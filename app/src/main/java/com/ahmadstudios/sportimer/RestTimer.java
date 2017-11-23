package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.EditText;

class RestTimer extends Timer {

    private boolean tickPlays;

    RestTimer (Activity mainActivity) {
        super(mainActivity, (EditText) mainActivity.findViewById(R.id.restTimerEditText), "RestTimer");
    }

    interface TimerListener {
        void onRestTimerFinish();
    }

    @Override
    void start() {
        final TimerListener timerListener;
        try {
            timerListener = (TimerListener) activity;
        } catch (ClassCastException castException) {
            throw new ClassCastException(activity.toString() + " должен реализовывать интерфейс TimerListener");
        }

        tickPlays = false;

        new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;

                if (seconds == 10 && millisUntilFinished < 10200 && !tickPlays) {
                    MediaPlayer.create(activity, R.raw.tick).start();
                    tickPlays = true;
                }

                timerEditText.setText(stringNumber(minutes) + ":" + stringNumber(seconds) + ":" + stringNumber(milliseconds));
            }

            @Override
            public void onFinish() {
                timerEditText.setText(R.string.zeros_of_timer);
                timerListener.onRestTimerFinish();
            }
        }.start();
    }
}