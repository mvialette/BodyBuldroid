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
        
        // count down initialize at 45sec by 1 sec step
//        final CountDownTimer countDownTimer = new CountDownTimer(currentSecond * 1000,1000) {
//			
//			@Override
//			public void onTick(long millisUntilFinished) {
//				
//				pauseTime = millisUntilFinished;
//				
//				TextView countDownTextView = (TextView)findViewById(R.id.countDownTextView);
//				currentSecond = (int) (millisUntilFinished / 1000);
//				countDownTextView.setText("00:"+ currentSecond );
//			}
//			
//			@Override
//			public void onFinish() {
//				TextView countDownTextView = (TextView)findViewById(R.id.countDownTextView);
//				countDownTextView.setText("The end");
//			}
//		};
        
        final Duration duration = new Duration(45 * 1000, 1000, (TextView)findViewById(R.id.countDownTextView), getApplicationContext(), 40*1000, 35*1000);
        
        
        //toggleButtonStartStop.setText(R.string.button_name_enable);
        
//        toggleButtonStartStop.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	
//            	//countDownTextView.setText()
//            	
////                Chronometer chronometer = (Chronometer)findViewById(R.id.chronometer1);
////                //chronometer.rev
//                if(isRunning){
//                	
////                	lastPause = SystemClock.elapsedRealtime();
////                	chronometer.stop();
//                	isRunning = false;
////                	toggleButtonStartStop.setText(R.string.button_name_enable);
//                	//int backup = currentSecond;
//                	duration.onPause();
//                }else{
//                	isRunning = true;
//                	duration.start();
////                	if(lastPause > 0){
////                		chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
////                	}else{
////                		chronometer.setBase(SystemClock.elapsedRealtime());
////                	}
////                	chronometer.start();
////                	isRunning = true;
////                	toggleButtonStartStop.setText(R.string.button_name_disable);
////                	iterationNumber++;
////                	
////                	TextView iterationNumberValue = (TextView) findViewById(R.id.textViewIterationNumber);
////                	iterationNumberValue.setText("" + iterationNumber);
//                }
//                
//            }
//        });
        final Button buttonStartTimer = (Button) findViewById(R.id.btn_start);
        buttonStartTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				System.out.println("aaa");
				duration.start();
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber++;
				textViewIterationNumber.setText("" + iterationNumber);
			}
		});
        
//        final Button buttonPauseTimer = (Button) findViewById(R.id.btn_pause);
//        buttonPauseTimer.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				System.out.println("bbb");
//				duration.onPause();
//			}
//		});
        
//        final Button buttonResumeTimer = (Button) findViewById(R.id.btn_resume);
//        buttonResumeTimer.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				System.out.println("ccc");
//				Duration newDuration = duration.onResume();
//				duration.cancel();
//				newDuration.start();
//			}
//		});
//        
//        final Button buttonResetTimer = (Button) findViewById(R.id.btn_reset);
//        buttonResetTimer.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	System.out.println("ddd");
//            	duration.cancel();
//            	
////        		currentSecond = 45;
////        		//toggleButtonStartStop.
////            	TextView countDownTextView = (TextView)findViewById(R.id.countDownTextView);
////				countDownTextView.setText("00:" + currentSecond);
//            	
////            	iterationNumber = 0;
////            	TextView iterationNumberValue = (TextView) findViewById(R.id.textViewIterationNumber);
////            	iterationNumberValue.setText("" + iterationNumber);
////            	
////                lastPause = 0L;
////            	isRunning = false;
////            	//chronometer.start();
////            	//isRunning = true;
////            	//toggleButtonStartStop
//            	//toggleButtonStartStop.performClick();
//            	
//            	
//            	//toggleButtonStartStop.setText(R.string.button_name_disable);
//            }
//        });

    }
}