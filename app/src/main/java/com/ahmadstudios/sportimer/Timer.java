package com.ahmadstudios.sportimer;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.EditText;

public class Timer {

    private long firstTimerTime;
    private long secondTimerTime;
    private long currentTime;
    private int numberApproaches;
    private EditText firstTimerEditText;
    private EditText secondTimerEditText;
    private EditText currentTimerEditText;
    private boolean isSecondTimer = false;
    private Audio sound = new Audio();


    public void setFirstTimerTime(int minutes, int seconds) {
        firstTimerTime = minutes * 60000 + seconds * 1000;
    }

    public void setSecondTimerTime(int minutes, int seconds) {
        secondTimerTime = minutes * 60000 + seconds * 1000;
    }

    public void setFirstTimerEditText(EditText editText) {
        firstTimerEditText = editText;
    }

    public void setSecondTimerEditText(EditText editText) {
        secondTimerEditText = editText;
    }

    public void setNumberApproaches(String number) {
        numberApproaches = Integer.parseInt(number);
    }

    public void startTimer(final Context context)
    {
        if(isSecondTimer) {
            currentTime = secondTimerTime;
            currentTimerEditText = secondTimerEditText;
            isSecondTimer = false;
        } else {
            currentTime = firstTimerTime;
            currentTimerEditText = firstTimerEditText;
            isSecondTimer = true;

            sound.playSound(context, R.raw.gong);
        }

        CountDownTimer timer = new CountDownTimer(currentTime, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;
                String stringMinutes = Long.toString(minutes);
                String stringSeconds = Long.toString(seconds);
                String stringMilliseconds = Long.toString(milliseconds);

                if (seconds == 10 && milliseconds == 50) sound.playSound(context, R.raw.tick);

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
                    startTimer(context);
                } else isSecondTimer = false;
            }
        }.start();
    }
}