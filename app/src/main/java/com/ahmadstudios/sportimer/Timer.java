package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

class Timer {
    Activity activity;
    long time;
    EditText timerEditText;
    private String tag;

    interface TimerListener {
        void onTimerFinish();
    }

    Timer(Activity mainActivity, EditText editText, String timerTag) {
        activity = mainActivity;
        timerEditText = editText;
        tag = timerTag;
    }

    void chooseTime() {
        timerEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    DialogFragment dialog = new SetTimerDialogFragment();
                    dialog.show(activity.getFragmentManager(), tag);
                }
                return false;
            }
        });
    }

    void setTime(int minutes, int seconds) {
        time = minutes * 60000 + seconds * 1000;
        timerEditText.setText(stringNumber(minutes) + ":" + stringNumber(seconds) + ":00");
    }

    void start() {
        final TimerListener timerListener;
        try {
            timerListener = (TimerListener) activity;
        } catch (ClassCastException castException) {
            throw new ClassCastException(activity.toString() + " должен реализовывать интерфейс TimerListener");
        }

        new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;

                timerEditText.setText(stringNumber(minutes) + ":" + stringNumber(seconds) + ":" + stringNumber(milliseconds));
            }

            @Override
            public void onFinish() {
                timerEditText.setText(R.string.zeros_of_timer);
                timerListener.onTimerFinish();
            }
        }.start();
    }

    String stringNumber(long number) {
        String stringNumber = Long.toString(number);
        if (number < 10) stringNumber = "0" + stringNumber;
        return stringNumber;
    }
}