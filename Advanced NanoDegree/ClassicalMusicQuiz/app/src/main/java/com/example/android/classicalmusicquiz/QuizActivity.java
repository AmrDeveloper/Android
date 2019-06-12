/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.classicalmusicquiz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media.session.MediaButtonReceiver;
import androidx.media.app.NotificationCompat.MediaStyle;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CORRECT_ANSWER_DELAY_MILLIS = 1000;
    private static final String REMAINING_SONGS_KEY = "remaining_songs";
    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String NOTIFICATION_CHANNEL_ID = "com.example.android.classicalmusicquiz";
    private int[] mButtonIDs = {R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD};
    private ArrayList<Integer> mRemainingSampleIDs;
    private ArrayList<Integer> mQuestionSampleIDs;
    private int mAnswerSampleID;
    private int mCurrentScore;
    private int mHighScore;
    private Button[] mButtons;

    private ExoPlayer mExoPlayer;
    private PlayerView mExoPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mExoPlayerView = findViewById(R.id.playerView);

        boolean isNewGame = !getIntent().hasExtra(REMAINING_SONGS_KEY);

        // If it's a new game, set the current score to 0 and load all samples.
        if (isNewGame) {
            QuizUtils.setCurrentScore(this, 0);
            mRemainingSampleIDs = Sample.getAllSampleIDs(this);
            // Otherwise, get the remaining songs from the Intent.
        } else {
            mRemainingSampleIDs = getIntent().getIntegerArrayListExtra(REMAINING_SONGS_KEY);
        }

        // Get current and high scores.
        mCurrentScore = QuizUtils.getCurrentScore(this);
        mHighScore = QuizUtils.getHighScore(this);

        // Generate a question and get the correct answer.
        mQuestionSampleIDs = QuizUtils.generateQuestion(mRemainingSampleIDs);
        mAnswerSampleID = QuizUtils.getCorrectAnswerID(mQuestionSampleIDs);

        // Load the image of the composer for the answer into the PlayerView.
        mExoPlayerView.setDefaultArtwork(getResources().getDrawable(R.drawable.question_mark));

        // If there is only one answer left, end the game.
        if (mQuestionSampleIDs.size() < 2) {
            QuizUtils.endGame(this);
            finish();
        }

        // Initialize the buttons with the composers names.
        mButtons = initializeButtons(mQuestionSampleIDs);

        // Initialize the Media Session.
        initializeMediaSession();

        Sample answerSample = Sample.getSampleByID(this, mAnswerSampleID);
        if (answerSample == null) {
            Toast.makeText(this, getString(R.string.sample_list_load_error), Toast.LENGTH_SHORT).show();
            return;
        }

        initializePlayer(Uri.parse(answerSample.getUri()));
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(mExoPlayerListener);

            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);

            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(this, TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );
        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MediaSessionCallback());
        mMediaSession.setActive(true);
    }

    /**
     * Initializes the button to the correct views, and sets the text to the composers names,
     * and set's the OnClick listener to the buttons.
     *
     * @param answerSampleIDs The IDs of the possible answers to the question.
     * @return The Array of initialized buttons.
     */
    private Button[] initializeButtons(ArrayList<Integer> answerSampleIDs) {
        Button[] buttons = new Button[mButtonIDs.length];
        for (int i = 0; i < answerSampleIDs.size(); i++) {
            Button currentButton = findViewById(mButtonIDs[i]);
            Sample currentSample = Sample.getSampleByID(this, answerSampleIDs.get(i));
            buttons[i] = currentButton;
            currentButton.setOnClickListener(this);
            if (currentSample != null) {
                currentButton.setText(currentSample.getComposer());
            }
        }
        return buttons;
    }

    /**
     * Release Exo Player
     */
    private void releasePlayer() {
        mNotificationManager.cancelAll();

        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void showNotification(PlaybackStateCompat state) {
        int icon;
        String play_pause;
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.exo_controls_pause_description);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.exo_controls_play_description);
        }

        PendingIntent contentPendingIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, QuizActivity.class), 0);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, QuizActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .addAction(icon, play_pause,
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,  PlaybackStateCompat.ACTION_PLAY_PAUSE))
                .addAction(R.drawable.exo_controls_previous, getString(R.string.restart),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0,1));

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Classical Music Quiz",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        mNotificationManager.notify(0, builder.build());
    }

    /**
     * The OnClick method for all of the answer buttons. The method uses the index of the button
     * in button array to to get the ID of the sample from the array of question IDs. It also
     * toggles the UI to show the correct answer.
     *
     * @param v The button that was clicked.
     */
    @Override
    public void onClick(View v) {

        // Show the correct answer.
        showCorrectAnswer();

        // Get the button that was pressed.
        Button pressedButton = (Button) v;

        // Get the index of the pressed button
        int userAnswerIndex = -1;
        for (int i = 0; i < mButtons.length; i++) {
            if (pressedButton.getId() == mButtonIDs[i]) {
                userAnswerIndex = i;
            }
        }

        // Get the ID of the sample that the user selected.
        int userAnswerSampleID = mQuestionSampleIDs.get(userAnswerIndex);

        // If the user is correct, increase there score and update high score.
        if (QuizUtils.userCorrect(mAnswerSampleID, userAnswerSampleID)) {
            mCurrentScore++;
            QuizUtils.setCurrentScore(this, mCurrentScore);
            if (mCurrentScore > mHighScore) {
                mHighScore = mCurrentScore;
                QuizUtils.setHighScore(this, mHighScore);
            }
        }

        // Remove the answer sample from the list of all samples, so it doesn't get asked again.
        mRemainingSampleIDs.remove(Integer.valueOf(mAnswerSampleID));

        // Wait some time so the user can see the correct answer, then go to the next question.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextQuestionIntent = new Intent(QuizActivity.this, QuizActivity.class);
                nextQuestionIntent.putExtra(REMAINING_SONGS_KEY, mRemainingSampleIDs);
                finish();
                startActivity(nextQuestionIntent);
            }
        }, CORRECT_ANSWER_DELAY_MILLIS);

    }

    /**
     * Disables the buttons and changes the background colors to show the correct answer.
     */
    private void showCorrectAnswer() {
        for (int i = 0; i < mQuestionSampleIDs.size(); i++) {
            int buttonSampleID = mQuestionSampleIDs.get(i);

            mButtons[i].setEnabled(false);
            mExoPlayerView.setDefaultArtwork(new BitmapDrawable(getResources(), Sample.getComposerArtBySampleID(this, mAnswerSampleID)));

            if (buttonSampleID == mAnswerSampleID) {
                mButtons[i].getBackground().setColorFilter(ContextCompat.getColor
                                (this, android.R.color.holo_green_light),
                        PorterDuff.Mode.MULTIPLY);
                mButtons[i].setTextColor(Color.WHITE);
            } else {
                mButtons[i].getBackground().setColorFilter(ContextCompat.getColor
                                (this, android.R.color.holo_red_light),
                        PorterDuff.Mode.MULTIPLY);
                mButtons[i].setTextColor(Color.WHITE);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            super.onPlay();
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            super.onPause();
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            mExoPlayer.seekTo(0);
        }
    }

    private final Player.EventListener mExoPlayerListener = new Player.EventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                // We are Playing
                mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getContentPosition(), 1f);
            } else if (playbackState == ExoPlayer.STATE_READY) {
                // We are paused
                mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getContentPosition(), 1f);
            }
            mMediaSession.setPlaybackState(mStateBuilder.build());
            showNotification(mStateBuilder.build());
        }
    };

    public static class MediaReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }

    }
}
