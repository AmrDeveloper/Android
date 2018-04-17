package com.musicapp.amrdeveloper.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView musicName;
    private TextView singerName;

    private ImageView singerImage;

    private ImageButton skipPreviousButton;
    private ImageButton playSongButton;
    private ImageButton skipNextButton;

    private SeekBar songSeekBar;

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mediaPlayer;

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager audioManager;

    private double startTime = 0;
    private double finalTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;


    private final Handler myHandler = new Handler();
    private Runnable UpdateSongTime;

    //Check if it first time to play this song
    public static int oneTimeOnly = 0;
    private boolean isNewSong = true;

    private String singer;
    private String song;
    private int audioID;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback
                mediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private final MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Reset Player
            finalTime = 0;
            startTime = 0;
            songSeekBar.setProgress(0);
            playSongButton.setImageResource(R.mipmap.play_song_icon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        musicName = findViewById(R.id.musicName);
        singerName = findViewById(R.id.singerName);
        singerImage = findViewById(R.id.singerImage);
        skipPreviousButton = findViewById(R.id.skipPreviousButton);
        playSongButton = findViewById(R.id.playSongButton);
        skipNextButton = findViewById(R.id.skipNextButton);
        songSeekBar = findViewById(R.id.songSeekBar);

        if (isNewSong) {
            startTime = 0;
            finalTime = 0;
            oneTimeOnly = 0;
            songSeekBar.setProgress(0);
        }

        Intent intent = getIntent();
        singer = intent.getStringExtra("singerName");
        song = intent.getStringExtra("songName");
        audioID = intent.getIntExtra("songId", 0);

        singerName.setText(singer);
        musicName.setText(song);

        int result = audioManager.requestAudioFocus(
                mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        );
        //return AUDIOFOCUS_REQUEST_FAILED  or AUDIOFOCUS_REQUEST_GRANTED as static final integer
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Start Playback
            //we have audio focus now
            //Create Media Player
            mediaPlayer = MediaPlayer.create(getApplicationContext(), audioID);
            //notify when audio is completion
            mediaPlayer.setOnCompletionListener(mCompletionListener);
        }

        getCurrentSingerPhoto();

        playSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    playSongButton.setImageResource(R.mipmap.play_song_icon);
                    mediaPlayer.pause();
                } else {
                    playSongButton.setImageResource(R.mipmap.pause_song_icon);
                    mediaPlayer.start();
                    // Get the length of song
                    finalTime = mediaPlayer.getDuration();
                    // Get Which second in the song
                    startTime = mediaPlayer.getCurrentPosition();

                    //If it first time set seek bar max
                    if (oneTimeOnly == 0) {
                        //Set the max of seek bar
                        songSeekBar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    if (isNewSong) {
                        isNewSong = false;
                    }

                    myHandler.postDelayed(UpdateSongTime, 100);

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        skipPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeToSkip = (int) startTime - backwardTime;
                if (timeToSkip > 0) {
                    startTime = timeToSkip;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Can't Skip Previous 5 Seconds", Toast.LENGTH_SHORT).show();

                }
            }
        });

        skipNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeToSkip = (int) startTime + forwardTime;
                if (timeToSkip <= finalTime) {
                    startTime = timeToSkip;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Can't Skip Next 5 Seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        UpdateSongTime = new Runnable() {
            public void run() {
                if (mediaPlayer != null && startTime < finalTime) {
                    startTime = mediaPlayer.getCurrentPosition();
                    songSeekBar.setProgress((int) startTime);
                    myHandler.postDelayed(this, 100);
                }
            }
        };

        songSeekBar.setOnSeekBarChangeListener(this);
    }

    private void getCurrentSingerPhoto() {
        switch (singer) {
            case "Adele":
                this.singerImage.setImageResource(R.drawable.adele);
                break;
            case "Sia":
                this.singerImage.setImageResource(R.drawable.sia);
                break;
            case "Jennifer Lopez":
                this.singerImage.setImageResource(R.drawable.jennifer_lopez);
                break;
            case "Britney Spears":
                this.singerImage.setImageResource(R.drawable.britney_spears);
                break;
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            } else if (mediaPlayer == null) {
                seekBar.setProgress(0);
            }
        } catch (Exception e) {
            seekBar.setEnabled(false);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //reset Seek bar size if user not press play button yet
        if (oneTimeOnly == 0) {
            songSeekBar.setMax(mediaPlayer.getDuration());
            oneTimeOnly = 1;
        }
        //Get Current Progress
        int progress = seekBar.getProgress();
        //Seek Song to current Progress
        mediaPlayer.seekTo(progress);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}