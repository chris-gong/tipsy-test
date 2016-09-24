package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;



import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class FifthGameActivity extends Activity {

	public Chronometer timer;
	public CountDownTimer stopWatch;
	public static Typeface font;
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double gameThreeTime;
	public static double gameFourTime;
	public static double gameFiveTime;
	public static int answer;
	public static EditText inputNumber;
	public static TextView question;
	public static TextView correctOrNotOrTimesUp;
	public static TextView times;
	public static float realWidth;
	public static float realHeight;
	public static AlphaAnimation buttonClick;
	
	@TargetApi(17)
    @SuppressWarnings("deprecation")
	public static void setResVals(Display display, DisplayMetrics displayMetrics){
    	
    	 if(Build.VERSION.SDK_INT >= 17){
		        display.getRealMetrics(displayMetrics);
		        realWidth = displayMetrics.widthPixels;
		        realHeight = displayMetrics.heightPixels;
		        }
		        else if(Build.VERSION.SDK_INT >=14){
		        	
					try {
						Method mGetRawW = Display.class.getMethod("getRawWidth");
						Method mGetRawH = Display.class.getMethod("getRawHeight");
			        	realWidth = (Integer) mGetRawW.invoke(display);
			        	realHeight = (Integer) mGetRawH.invoke(display);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						realWidth = display.getWidth();
						realHeight = display.getHeight();
					}
		        }
    }
	@Override
	public void onBackPressed(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_fifth_game);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			gameOneTime = extras.getDouble("gameOneTime");
			gameTwoTime = extras.getDouble("gameTwoTime");
			gameThreeTime = extras.getDouble("gameThreeTime");
			gameFourTime = extras.getDouble("gameFourTime");
		}
		font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		inputNumber = (EditText) findViewById(R.id.inputNumber);
		question = (TextView) findViewById(R.id.question);
		correctOrNotOrTimesUp = (TextView) findViewById(R.id.correctOrNotOrTimesUp);
		times = (TextView) findViewById(R.id.times);
		question.setTypeface(font);
		correctOrNotOrTimesUp.setTypeface(font);
		inputNumber.setTypeface(font);
		times.setTypeface(font);
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		setResVals(display,displayMetrics);
		        
		        
		//resizing and relocating buttons to prefer every device
		
		question.setY((int) (realHeight * .15));
		inputNumber.setY((int)(realHeight * .4));
		correctOrNotOrTimesUp.setY((int)(realHeight * .55));
		times.setY((int)(realHeight * .65));
		
        //adjusting textsize for every device
        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
        double screenInches =  Math.sqrt(screenWidth+screenHeight);
        
        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
        //phoneSize of current is 7
        float conversionFactor = (float) Math.round((105.0/7.0)*1000)/1000;
        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
        float conversionFactor2 = (float) Math.round((35.0/7.0)*1000)/1000;
        float textSize2 = (float) Math.round((conversionFactor2 * screenInches)*10)/10;
        float conversionFactor3 = (float) Math.round((50.0/7.0)*1000)/1000;
        float textSize3 = (float) Math.round((conversionFactor3 * screenInches)*10)/10;
        float conversionFactor4 = (float) Math.round((40.0/7.0)*1000)/1000;
        float textSize4 = (float) Math.round((conversionFactor4 * screenInches)*10)/10;
        question.setTextSize(textSize); 
        inputNumber.setTextSize(textSize2);
        correctOrNotOrTimesUp.setTextSize(textSize3);
        times.setTextSize(textSize4);
		//removes focus on edittext
		inputNumber.clearFocus();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//two random numbers to be added later (from 5 to 9)
		int num1 = (int) ((Math.random()*5)+5);
		int num2 = (int) ((Math.random()*5)+5);
		answer = num1 + num2;
		question.setText(String.valueOf(num1) + "+" + String.valueOf(num2) + "=");
		timer = new Chronometer(this);
		timer.setBase(SystemClock.elapsedRealtime());
		timer.start();
		stopWatch = new CountDownTimer(60000,1000){
			public void onTick(long millisUntilFinished){
						
					}
				public void onFinish(){
					//edittext goes away once times up
					inputNumber.setVisibility(View.INVISIBLE);
					correctOrNotOrTimesUp.setText("Times Up!");
					//keyboard closes once time runs out
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(inputNumber.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
					timer.stop();
					//current time minus timer's beginning time
					//must be divided by 1000.0 because double divided by int creates int
					gameFiveTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
					gameFiveTime = (double)Math.round(gameFiveTime * 1000+.5)/1000;
					double totalTime = gameThreeTime + gameFourTime + gameFiveTime;
					//rounding value to 3 decimals
					totalTime = (double)Math.round(totalTime * 1000)/1000;
					
					times.setText("Your reaction time the last three questions was "
							+ String.valueOf(totalTime) + " seconds");
					//goes to next activity
					new CountDownTimer(4000,1000){
						public void onTick(long millisUntilFinished){
							
						}
					public void onFinish(){
						Intent intent = new Intent(FifthGameActivity.this,  ReactionTimesActivity.class);
						//passing data
						intent.putExtra("gameOneTime", gameOneTime);
						intent.putExtra("gameTwoTime", gameTwoTime);
						intent.putExtra("gameThreeTime", gameThreeTime);
						intent.putExtra("gameFourTime", gameFourTime);
						intent.putExtra("gameFiveTime", gameFiveTime);
						startActivity(intent);
					}
					}
					.start();
				}
				}
		.start();
		inputNumber.setOnKeyListener(new OnKeyListener(){
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)){
					//string value of user input
					//if user input is blank then it's counted as wrong
					//blank string can't be converted to integer
					if(inputNumber.getText().toString().equals("")){
						//keyboard closes if answer is wrong as well
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputNumber.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
						correctOrNotOrTimesUp.setText("Incorrect, try again");
						//removes focus on edittext
						inputNumber.clearFocus();
						return false;
					}
					int input = Integer.valueOf(inputNumber.getText().toString());
					
					
					//if user input is correct answer
					if(input == answer){
						//stopwatch stop if user gets answer correct
						stopWatch.cancel();
						//keyboard closes if answer is right
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputNumber.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
						correctOrNotOrTimesUp.setText("Correct!");
						timer.stop();
						//current time minus timer's beginning time
						//must be divided by 1000.0 because double divided by int creates int
						gameFiveTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
						gameFiveTime = (double)Math.round(gameFiveTime * 1000+.5)/1000;
						double totalTime = gameThreeTime + gameFourTime + gameFiveTime;
						//rounding value to 3 decimals
						totalTime = (double)Math.round(totalTime * 1000)/1000;
						
						times.setText("Your reaction time from the last three questions was "
								+ String.valueOf(totalTime) + " seconds");
						//user cannot put in another answer if they answered correctly
						inputNumber.setFocusable(false);
						//goes to next activity
						new CountDownTimer(4000,1000){
							public void onTick(long millisUntilFinished){
								
							}
						public void onFinish(){
							Intent intent = new Intent(FifthGameActivity.this,  ReactionTimesActivity.class);
							//passing data
							intent.putExtra("gameOneTime", gameOneTime);
							intent.putExtra("gameTwoTime", gameTwoTime);
							intent.putExtra("gameThreeTime", gameThreeTime);
							intent.putExtra("gameFourTime", gameFourTime);
							intent.putExtra("gameFiveTime", gameFiveTime);
							startActivity(intent);
						}
						}
						.start();
					}
					else{
						//keyboard closes if answer is wrong as well
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputNumber.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
						correctOrNotOrTimesUp.setText("Incorrect, try again");
						//removes focus on edittext
						inputNumber.clearFocus();
					}
					
				}
				
				return false;
			}

			
		});
	}
}
