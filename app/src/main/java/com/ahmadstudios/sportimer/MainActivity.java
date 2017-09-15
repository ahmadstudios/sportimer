package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity
        implements SetTimerDialogFragment.SetTimerDialogListener, Timer.TimerListener, RestTimer.TimerListener {

    private ApproachTimer approachTimer;
    private RestTimer restTimer;
    private int numberApproaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        approachTimer = new ApproachTimer(this);
        restTimer = new RestTimer(this);
        approachTimer.chooseTime();
        restTimer.chooseTime();
    }

    @Override
    public void onDialogPositiveClick (int minutes, int seconds, String tag) {
        if (tag.equals("ApproachTimer")) approachTimer.setTime(minutes, seconds);
        if (tag.equals("RestTimer")) restTimer.setTime(minutes, seconds);
    }

    @Override
    public void onTimerFinish() {
        if (numberApproaches > 1) restTimer.start();
    }

    @Override
    public void onRestTimerFinish () {
        if (numberApproaches > 1) {
            numberApproaches--;
            approachTimer.start();
        }
    }

    public void onClick(View view) {
        numberApproaches = Integer.parseInt(((EditText)findViewById(R.id.numberApproachesEditText)).getText().toString());

        approachTimer.start();
    }
}