package com.ahmadstudios.sportimer;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {
    private MediaPlayer mediaPlayer;

    public void playGong(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.gong);
        mediaPlayer.start();
    }
}