package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity implements SetTimerDialogFragment.SetTimerDialogListener, Timer.TimerListener {

    private EditText approachTimerEditText;
    private EditText restTimerEditText;
    private Timer timer = new Timer();
    private int restMinutes;
    private int restSeconds;
    private int approachMinutes;
    private int approachSeconds;
    private int numberApproaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        approachTimerEditText = findViewById(R.id.approachTimerEditText);
        restTimerEditText = findViewById(R.id.restTimerEditText);

        approachTimerEditText.setOnTouchListener(touchListener("ApproachTimer"));
        restTimerEditText.setOnTouchListener(touchListener("RestTimer"));
    }

    private View.OnTouchListener touchListener (final String tag) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    DialogFragment dialog = new SetTimerDialogFragment();
                    dialog.show(getFragmentManager(), tag);
                }
                return false;
            }
        };
    }

    @Override
    public void onDialogPositiveClick (int minutes, int seconds, String tag)
    {
        if (tag.equals("ApproachTimer")) {
            approachMinutes = minutes;
            approachSeconds = seconds;
            approachTimerEditText.setText(timer.stringNumber(minutes) + ":" + timer.stringNumber(seconds) + ":00");
        } else {
            restMinutes = minutes;
            restSeconds = seconds;
            restTimerEditText.setText(timer.stringNumber(minutes) + ":" + timer.stringNumber(seconds) + ":00");
        }
    }

    @Override
    public void onTimerFinish (EditText editText) {
        if (numberApproaches > 1) {
            if (editText == approachTimerEditText) {
                timer.startTimer(this, restTimerEditText, restMinutes, restSeconds);
            } else {
                numberApproaches--;
                MediaPlayer.create(this, R.raw.gong).start();
                timer.startTimer(this, approachTimerEditText, approachMinutes, approachSeconds);
            }
        }
    }

    @Override
    public void onTimerWork (EditText editText) {
        if (editText == restTimerEditText) MediaPlayer.create(this, R.raw.tick).start();
    }

    public void onClick(View view) {
        numberApproaches = Integer.parseInt(((EditText)findViewById(R.id.numberApproachesEditText)).getText().toString());
        MediaPlayer.create(this, R.raw.gong).start();
        timer.startTimer(this, approachTimerEditText, approachMinutes, approachSeconds);
    }
}