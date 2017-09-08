package com.ahmadstudios.sportimer;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {
    private MediaPlayer mediaPlayer;

    public void playSound(Context context, int resid){
        mediaPlayer = MediaPlayer.create(context, resid);
        mediaPlayer.start();
    }
}