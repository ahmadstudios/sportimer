package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity implements SetTimerDialogFragment.SetTimerDialogListener {

    private EditText approachTimerEditText;
    private EditText restTimerEditText;
    private Timer timer = new Timer();
    private boolean restTimerFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        approachTimerEditText = findViewById(R.id.approachTimerEditText);
        restTimerEditText = findViewById(R.id.restTimerEitText);

        approachTimerEditText.setOnTouchListener(touchListener("SetApproachTimer", false));
        restTimerEditText.setOnTouchListener(touchListener("SetRestTimer", true));
    }

    private View.OnTouchListener touchListener (final String tag, final boolean isRestTimer) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    SetTimerDialogFragment dialog = new SetTimerDialogFragment();
                    dialog.show(getFragmentManager(), tag);
                    restTimerFlag = isRestTimer;
                }
                return false;
            }
        };
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
        EditText numberApproachesEditText = findViewById(R.id.numberApproachesEditText);

        timer.setNumberApproaches(numberApproachesEditText.getText().toString());
        timer.startTimer(this, approachTimerEditText, restTimerEditText);
    }
}
