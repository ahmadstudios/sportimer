package com.ahmadstudios.sportimer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.EditText;

class Timer {

    private long firstTimerTime;
    private long secondTimerTime;
    private int numberApproaches;
    private boolean isSecondTimer = false;
    private boolean tickPlays; //Флаг, который определяет, начался ли проигрываться звук тиканья

    void setFirstTimerTime(int minutes, int seconds) {
        firstTimerTime = minutes * 60000 + seconds * 1000;
    }

    void setSecondTimerTime(int minutes, int seconds) {
        secondTimerTime = minutes * 60000 + seconds * 1000;
    }

    void setNumberApproaches(String number) {
        numberApproaches = Integer.parseInt(number);
    }

    void startTimer(final Context context, final EditText firstTimerEditText, final EditText secondTimerEditText)
    {
        long currentTime;
        final EditText currentTimerEditText;
        tickPlays = false;

        if(isSecondTimer) {
            currentTime = secondTimerTime;
            currentTimerEditText = secondTimerEditText;
            isSecondTimer = false;
        } else {
            currentTime = firstTimerTime;
            currentTimerEditText = firstTimerEditText;
            isSecondTimer = true;

            MediaPlayer.create(context, R.raw.gong).start();
        }

       new CountDownTimer(currentTime, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;
                String stringMinutes = Long.toString(minutes);
                String stringSeconds = Long.toString(seconds);
                String stringMilliseconds = Long.toString(milliseconds);

                if (!isSecondTimer && seconds == 10 && millisUntilFinished < 10100 && !tickPlays)
                {
                    MediaPlayer.create(context, R.raw.tick).start();
                    tickPlays = true;
                }

                if (minutes < 10) stringMinutes = "0" + stringMinutes;
                if (seconds < 10) stringSeconds = "0" + stringSeconds;
                if (milliseconds < 10) stringMilliseconds = "0" + milliseconds;

                currentTimerEditText.setText(stringMinutes + ":" + stringSeconds + ":" + stringMilliseconds);
            }

            @Override
            public void onFinish() {
                currentTimerEditText.setText(R.string.zeros_of_timer);
                if (isSecondTimer) numberApproaches--;
                if (numberApproaches > 0)
                {
                    startTimer(context, firstTimerEditText, secondTimerEditText);
                } else isSecondTimer = false;
            }
        }.start();
    }
}