package com.ahmadstudios.sportimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText numberApproachesEditText;
    private EditText approachTimerEditText;
    private EditText restTimerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberApproachesEditText = findViewById(R.id.numberApproachesEditText);
        approachTimerEditText = findViewById(R.id.approachTimerEditText);
        restTimerEditText = findViewById(R.id.restTimerEitText);
    }

    public void onClick(View view) {
        Audio gong = new Audio();
        gong.playGong(this);
    }
}
