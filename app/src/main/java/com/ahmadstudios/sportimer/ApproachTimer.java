package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.EditText;

class ApproachTimer extends Timer {

    ApproachTimer (Activity mainActivity) {
        super(mainActivity, (EditText) mainActivity.findViewById(R.id.approachTimerEditText), "ApproachTimer");
    }

    @Override
    void start() {
        MediaPlayer.create(activity, R.raw.gong).start();
        super.start();
    }
}