package com.example.ron_vs_mcgonagall;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {

    int whenPaused;

    private static MyMediaPlayer Instance;
    MediaPlayer mediaPlayer;

    static MyMediaPlayer getMediaPlayerInstance() {
        if (Instance == null) {
            return Instance = new MyMediaPlayer();
        }
        return Instance;
    }

    public void playAudioFile(Context context, int sampleAudio) {
        mediaPlayer = MediaPlayer.create(context, sampleAudio);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();

            }
        });

    }

    public void stopAudioFile() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setLoop(boolean b) {
        mediaPlayer.setLooping(b);
    }

    public void pausePlayer() {
        mediaPlayer.pause();
        whenPaused = mediaPlayer.getCurrentPosition();
    }

    public void resumePlayer(int audio, Context context) {
        mediaPlayer = MediaPlayer.create(context, audio);
        mediaPlayer.seekTo(whenPaused);
        mediaPlayer.start();
    }
}
