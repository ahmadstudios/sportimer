package com.ahmadstudios.sportimer;

import android.os.CountDownTimer;
import android.widget.EditText;

public class Timer {

    private long time;
    private EditText editText;

    public void setTime(String timeInString) {
        long minutes = Long.parseLong(timeInString.substring(0, 2));
        long seconds = Long.parseLong(timeInString.substring(3, 5));
        long milliseconds = Long.parseLong(timeInString.substring(6, 8));

        time = minutes * 60000 + seconds * 1000 + milliseconds * 10;
    }

    public void setEditText(EditText approachTimerEditText) {
        editText = approachTimerEditText;
    }

    public void start() {
        CountDownTimer timer = new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished - minutes * 60000) / 1000;
                long milliseconds = ((millisUntilFinished - minutes * 60000) % 1000) / 10;
                String stringMinutes = Long.toString(minutes);
                String stringSeconds = Long.toString(seconds);
                String stringMilliseconds = Long.toString(milliseconds);

                if (minutes < 10) stringMinutes = "0" + stringMinutes;
                if (seconds < 10) stringSeconds = "0" + stringSeconds;
                if (milliseconds < 10) stringMilliseconds = "0" + milliseconds;

                editText.setText(stringMinutes + ":" + stringSeconds + ":" + stringMilliseconds);
            }

            @Override
            public void onFinish() {
                editText.setText("Бабах!");
            }
        }.start();
    }
}