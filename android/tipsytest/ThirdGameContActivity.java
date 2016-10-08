package com.crazystupidgames.tipsytest;


import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdGameContActivity extends Activity {
	
	public Chronometer timer;
	public CountDownTimer stopWatch;
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double gameThreeTime;
	public static char[] alphabet;
	public static char randomLetter;
	public static char answerLetterUppercase;
	public static char answerLetterLowercase;
	public static Typeface font;
	public static TextView instructions;
	public static TextView questionLetter;
	public static EditText inputLetter;
	public static TextView correctOrNotOrTimesUp;
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
		//next two lines remove title bar at the top of the screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_third_game_cont);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			gameOneTime = extras.getDouble("gameOneTime");
			gameTwoTime = extras.getDouble("gameTwoTime");
		}
		timer = new Chronometer(this);
		font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		instructions = (TextView) findViewById(R.id.instructions);
		questionLetter = (TextView) findViewById(R.id.questionLetter);
		correctOrNotOrTimesUp = (TextView) findViewById(R.id.correctOrNotOrTimesUp);
		inputLetter = (EditText) findViewById(R.id.answerLetter);
		//set fonts of both textviews
		instructions.setTypeface(font);
		questionLetter.setTypeface(font);
		inputLetter.setTypeface(font);
		correctOrNotOrTimesUp.setTypeface(font);
		//Retrieves width and height of screen
				Display display = getWindowManager().getDefaultDisplay();
				//gets size of screen in dp
				DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
						
				setResVals(display,displayMetrics);
				        
				        
				//resizing and relocating buttons to prefer every device
				instructions.setY((int)(realHeight*.15));
				questionLetter.setY((int) (realHeight * .2));
				inputLetter.setY((int)(realHeight * .5));
				correctOrNotOrTimesUp.setY((int)(realHeight * .65));
				
				
		        //adjusting textsize for every device
		        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
		        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
		        double screenInches =  Math.sqrt(screenWidth+screenHeight);
		        
		        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
		        //phoneSize of current is 7
		        float conversionFactor = (float) Math.round((25.0/7.0)*1000)/1000;
		        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
		        float conversionFactor2 = (float) Math.round((155.0/7.0)*1000)/1000;
		        float textSize2 = (float) Math.round((conversionFactor2 * screenInches)*10)/10;
		        float conversionFactor3 = (float) Math.round((50.0/7.0)*1000)/1000;
		        float textSize3 = (float) Math.round((conversionFactor3 * screenInches)*10)/10;
		        float conversionFactor4 = (float) Math.round((35.0/7.0)*1000)/1000;
		        float textSize4 = (float) Math.round((conversionFactor4 * screenInches)*10)/10;
		        instructions.setTextSize(textSize);
		        questionLetter.setTextSize(textSize2);
		        inputLetter.setTextSize(textSize4);
		        correctOrNotOrTimesUp.setTextSize(textSize3);
		        
		//declaring/initializing new character array
		alphabet = new char[]{'G','P','H','Q','R','J','K','L','F','O'};
		
		//setting random letter in array to textview
		int randomLetterIndex = (int) (Math.random() * 10);
		randomLetter = alphabet[randomLetterIndex];
		//converting char to string
		questionLetter.setText(String.valueOf(randomLetter));
		
		//answer to question is letter before randomLetter
		answerLetterUppercase = (char) (randomLetter - 1);
		//converts answer to lowercase
		answerLetterLowercase = Character.toLowerCase(answerLetterUppercase);
		
		//removes focus on edittext
		inputLetter.clearFocus();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//making 60 second countdown timer
		stopWatch = new CountDownTimer(60000,1000){
			public void onTick(long millisUntilFinished){
				
			}
		public void onFinish(){
			//edittext goes away once times up
			inputLetter.setVisibility(View.INVISIBLE);
			correctOrNotOrTimesUp.setText("Times Up!");
			//keyboard closes once time runs out
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(inputLetter.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			timer.stop();
			//current time minus timer's beginning time
			//must be divided by 1000.0 because double divided by int creates int
			gameThreeTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
			gameThreeTime = (double)Math.round(gameThreeTime * 1000+.5)/1000;
			//goes to next activity
			new CountDownTimer(2000,1000){
				public void onTick(long millisUntilFinished){
					
				}
			public void onFinish(){
				Intent intent = new Intent(ThirdGameContActivity.this,  FourthGameActivity.class);
				//passing data
				intent.putExtra("gameOneTime", gameOneTime);
				intent.putExtra("gameTwoTime", gameTwoTime);
				intent.putExtra("gameThreeTime", gameThreeTime);
				startActivity(intent);
			}
			}
			.start();
		}
		}
		.start();
		
		timer.setBase(SystemClock.elapsedRealtime());
		timer.start();
		inputLetter.setOnKeyListener(new OnKeyListener(){
			

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				//if the event is a key-down event on the "enter" button
				if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)){
					//string value of user input
					String input = inputLetter.getText().toString();
					
					//if user input is correct answer
					if(input.equals(String.valueOf(answerLetterUppercase))||input.equals(String.valueOf(answerLetterLowercase))){
						//stopwatch stop if user gets answer correct
						stopWatch.cancel();
						//keyboard closes if answer is right
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputLetter.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
						correctOrNotOrTimesUp.setText("Correct!");
						timer.stop();
						//current time minus timer's beginning time
						//must be divided by 1000.0 because double divided by int creates int
						gameThreeTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
						gameThreeTime = (double)Math.round(gameThreeTime * 1000+.5)/1000;
						//user cannot put in another answer if they answered correctly
						inputLetter.setFocusable(false);
						//goes to next activity
						new CountDownTimer(2000,1000){
							public void onTick(long millisUntilFinished){
								
							}
						public void onFinish(){
							Intent intent = new Intent(ThirdGameContActivity.this,  FourthGameActivity.class);
							//passing data
							intent.putExtra("gameOneTime", gameOneTime);
							intent.putExtra("gameTwoTime", gameTwoTime);
							intent.putExtra("gameThreeTime", gameThreeTime);
							startActivity(intent);
						}
						}
						.start();
					}
					else{
						//keyboard closes if answer is wrong as well
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputLetter.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
						correctOrNotOrTimesUp.setText("Incorrect, try again");
						//removes focus on edittext
						inputLetter.clearFocus();
					}
					
				}
				return false;
			}
		});

		
	}
}
