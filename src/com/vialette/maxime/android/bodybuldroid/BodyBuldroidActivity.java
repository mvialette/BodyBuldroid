package com.vialette.maxime.android.bodybuldroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BodyBuldroidActivity extends Activity {
	
	boolean isRunning = false;
	private long lastPause;
	
	private int iterationNumber = 0;
	private int currentSecond = 45;
	
	private long pauseTime;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Duration duration = new Duration(45 * 1000, 1000, (TextView)findViewById(R.id.countDownTextView), getApplicationContext(), 40*1000, 35*1000, (Button) findViewById(R.id.btn_start));
        
        final Button buttonStartTimer = (Button) findViewById(R.id.btn_start);
        buttonStartTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				buttonStartTimer.setEnabled(false);
				duration.start();
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber++;
				textViewIterationNumber.setText("" + iterationNumber);
			}
		});
        
        final Button buttonResumeTimer = (Button) findViewById(R.id.btn_resume);
        buttonResumeTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber = 0;
				textViewIterationNumber.setText("" + iterationNumber);
				
				TextView countdown = (TextView)findViewById(R.id.countDownTextView);
				countdown.setText("");
				
				buttonStartTimer.setEnabled(true);
				
				duration.cancel();
			}
		});

    }
}