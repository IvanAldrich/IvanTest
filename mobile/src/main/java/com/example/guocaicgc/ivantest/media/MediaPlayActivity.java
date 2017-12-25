package com.example.guocaicgc.ivantest.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.guocaicgc.ivantest.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by guocai.cgc on 2017/8/21.
 */
public class MediaPlayActivity extends AppCompatActivity {
    private static final String MUSIC_PATH = "/sdcard/IvanMusic/justyou.mp3";

    private AudioManager mAudioManager;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);
    }

    public void startPlayMedia(View view) {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(MUSIC_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                Log.e("ivan", "MediaPlayActivity onAudioFocusChange. focusChange = " + focusChange);
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mPlayer.setVolume(0.3f, 0.3f);
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mPlayer.setVolume(1, 1);
                }
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayer.setVolume(1, 1);
        mPlayer.start();

    }

    public void raiseVoice(View view) {
        mPlayer.setVolume(1, 1);
    }

    public void lowerVoice(View view) {
        mPlayer.setVolume(0.3f, 0.3f);
    }
}
