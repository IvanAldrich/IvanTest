package com.example.guocaicgc.ivantest;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.util.concurrent.TimeUnit;

/**
 * Created by guocai.cgc on 2018/7/17.
 */

public class AudioTest {

    private AudioTrack mAudioTrack;

    public AudioTest() {
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat
                .CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioTrack.getMinBufferSize
                (16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT),
                AudioTrack.MODE_STREAM);
    }

    private void release() {
        mAudioTrack.play();
        byte[] pcm = new byte[] {2, 2, 3, 4,99, 23, 87};
        mAudioTrack.write(pcm, 0, pcm.length);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAudioTrack.stop();
        mAudioTrack.flush();
        mAudioTrack.release();
        mAudioTrack.flush();
    }

    public static void testRelease() {
        AudioTest test = new AudioTest();

        test.release();
    }
}
