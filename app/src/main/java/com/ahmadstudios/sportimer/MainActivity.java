package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity implements SetTimerDialogFragment.SetTimerDialogListener {

    private EditText numberApproachesEditText;
    private EditText approachTimerEditText;
    private EditText restTimerEditText;
    private Timer timer = new Timer();
    private boolean restTimerFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberApproachesEditText = findViewById(R.id.numberApproachesEditText);
        approachTimerEditText = findViewById(R.id.approachTimerEditText);
        restTimerEditText = findViewById(R.id.restTimerEitText);

        approachTimerEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    SetTimerDialogFragment dialog = new SetTimerDialogFragment();
                    dialog.show(getFragmentManager(), "SetApproachTimer");
                    restTimerFlag = false;
                }
                return false;
            }
        });

        restTimerEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    DialogFragment dialog = new SetTimerDialogFragment();
                    dialog.show(getFragmentManager(), "SetRestTimer");
                    restTimerFlag = true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDialogPositiveClick (int minutes, int seconds)
    {
        String stringMinutes = Integer.toString(minutes);
        String stringSeconds = Integer.toString(seconds);
        if (minutes < 10) stringMinutes = "0" + stringMinutes;
        if (seconds < 10) stringSeconds = "0" + stringSeconds;
        if (!restTimerFlag) {
            timer.setFirstTimerTime(minutes, seconds);
            approachTimerEditText.setText(stringMinutes + ":" + stringSeconds + ":00");
        } else {
            timer.setSecondTimerTime(minutes, seconds);
            restTimerEditText.setText(stringMinutes + ":" + stringSeconds + ":00");
        }
    }

    public void onClick(View view) {
        timer.setFirstTimerEditText(approachTimerEditText);
        timer.setSecondTimerEditText(restTimerEditText);
        timer.setNumberApproaches(numberApproachesEditText.getText().toString());
        timer.startTimer(this);

//        approachTimerEditText.setFocusableInTouchMode(false);
//        restTimerEditText.setFocusableInTouchMode(false);
    }
}
