package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class FamiltyFragment extends Fragment{

    private MediaPlayer mMediaPlayer;
    //setup a listener on the media player so that we can stop and release the media player
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //When Audio Is Done
            //Call Helper Method
            releaseMediaPlayer();
        }
    };

    //define AudoManager
    private AudioManager mAudioManager;
    //Init Audio Manager Listener
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //init audio manager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Array Of Number Words
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        //Word Adapter
        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_family);
        //initialize ListView
        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        //set Adapter
        listView.setAdapter(adapter);
        //set onItemClick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Word word = words.get(index);
                //Releases MediaPlayer
                releaseMediaPlayer();
                //request audio focus
                int result = mAudioManager.requestAudioFocus(
                        //listener
                        mOnAudioFocusChangeListener,
                        //StreamType
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );
                //return AUDIOFOCUS_REQUEST_FAILED  or AUDIOFOCUS_REQUEST_GRANTED as static final integer
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Start Playback
                    //we have audio focus now
                    //Create Media Player
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    //start when user click
                    mMediaPlayer.start();
                    //notify when audio is completion
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return  rootView;
    }

    //OnStop to stop audio when Activity state is stop
    @Override
    public void onStop() {
        super.onStop();
        //Stop Audio
        releaseMediaPlayer();
    }

    //Release Media Player
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            //release audio focus
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }


}
