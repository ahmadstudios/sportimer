package com.ahmadstudios.sportimer;

import android.os.CountDownTimer;
import android.widget.EditText;

class Timer {

    private long time;
    private boolean tickPlays; //Флаг, который определяет, начался ли проигрываться звук тиканья

    interface TimerListener {
        void onTimerFinish(EditText editText);
        void onTimerWork(EditText editText);
    }

    void setTime(int minutes, int seconds) {
        time = minutes * 60000 + seconds * 1000;
    }

    void startTimer(final TimerListener timerListener, final EditText editText) {
        tickPlays = false;

        new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;
                String stringMinutes = Long.toString(minutes);
                String stringSeconds = Long.toString(seconds);
                String stringMilliseconds = Long.toString(milliseconds);

                if (seconds == 10 && millisUntilFinished < 10200 && !tickPlays) {
                    timerListener.onTimerWork(editText);
                    tickPlays = true;
                }

                if (minutes < 10) stringMinutes = "0" + stringMinutes;
                if (seconds < 10) stringSeconds = "0" + stringSeconds;
                if (milliseconds < 10) stringMilliseconds = "0" + milliseconds;

                editText.setText(stringMinutes + ":" + stringSeconds + ":" + stringMilliseconds);
            }

            @Override
            public void onFinish() {
                editText.setText(R.string.zeros_of_timer);
                timerListener.onTimerFinish(editText);
            }
        }.start();
    }
}