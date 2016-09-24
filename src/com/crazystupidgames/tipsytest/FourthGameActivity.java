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
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FourthGameActivity extends Activity {
	
	public Chronometer timer;
	public CountDownTimer stopWatch;
	public static EditText inputNumber;
	public static TextView correctOrNotOrTimesUp;
	public static ImageView image;
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double gameThreeTime;
	public static double gameFourTime;
	public static int[] imageArray1;
	public static int[] imageArray2;
	public static int[] imageArray3;
	public static int[][] imageArrays;
	public static int choosenImage;
	public static int answer;
	public static Typeface font;
	public static TextView instructions;
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
		
		setContentView(R.layout.activity_fourth_game);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			gameOneTime = extras.getDouble("gameOneTime");
			gameTwoTime = extras.getDouble("gameTwoTime");
			gameThreeTime = extras.getDouble("gameThreeTime");
		}
		font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		instructions = (TextView) findViewById(R.id.instructions);
		inputNumber = (EditText) findViewById(R.id.inputNumber);
		image = (ImageView) findViewById(R.id.shapeImage);
		correctOrNotOrTimesUp = (TextView) findViewById(R.id.correctOrNotOrTimesUp);
		instructions.setTypeface(font); 
		correctOrNotOrTimesUp.setTypeface(font);
		inputNumber.setTypeface(font);
		
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		setResVals(display,displayMetrics);
		        
		        
		//resizing and relocating buttons to prefer every device
		instructions.setY((int)(realHeight*.15));
		image.setY((int) (realHeight * .25));
		inputNumber.setY((int)(realHeight * .5));
		correctOrNotOrTimesUp.setY((int)(realHeight * .65));
		
		ViewGroup.LayoutParams imageparams = image.getLayoutParams(); 
		imageparams.width = (int)(realWidth * .3);
		imageparams.height = (int)(realWidth * .3);    
		image.setLayoutParams(imageparams);
        //adjusting textsize for every device
        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
        double screenInches =  Math.sqrt(screenWidth+screenHeight);
        
        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
        //phoneSize of current is 7
        float conversionFactor = (float) Math.round((25.0/7.0)*1000)/1000;
        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
       
        float conversionFactor2 = (float) Math.round((50.0/7.0)*1000)/1000;
        float textSize2 = (float) Math.round((conversionFactor2 * screenInches)*10)/10;
        float conversionFactor3 = (float) Math.round((35.0/7.0)*1000)/1000;
        float textSize3 = (float) Math.round((conversionFactor3 * screenInches)*10)/10;
        instructions.setTextSize(textSize);
        inputNumber.setTextSize(textSize3);
        correctOrNotOrTimesUp.setTextSize(textSize2);
        
		//removes focus on edittext
		inputNumber.clearFocus();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//putting images of certain number of shapes in each array
		//depending on number of shapes
		imageArray1 = new int[]{R.drawable.circle3, R.drawable.square3, R.drawable.triangle3,3};
		imageArray2 = new int[]{R.drawable.circle4, R.drawable.square4, R.drawable.triangle4,4};
		imageArray3 = new int[]{R.drawable.circle5, R.drawable.square5, R.drawable.triangle5,5};
		imageArrays = new int[][]{imageArray1, imageArray2, imageArray3};
		
		//choosing a random array and
		//choosing a random picture in the array
		int randomArrayIndex = (int) (Math.random() * 3);
		int randomImageIndex = (int) (Math.random() * 3);
			
		//imageArrays [randomArray][randomArrayElement]
		choosenImage = imageArrays[randomArrayIndex][randomImageIndex];
		//set random image to ImageView
		image.setImageResource(choosenImage);
		
		answer = choosenImage = imageArrays[randomArrayIndex][3];
		timer = new Chronometer(this);
		timer.setBase(SystemClock.elapsedRealtime());
		timer.start();
		//making 60 second countdown timer
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
					gameFourTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
					gameFourTime = (double)Math.round(gameFourTime * 1000+.5)/1000;
					//goes to next activity
					new CountDownTimer(2000,1000){
						public void onTick(long millisUntilFinished){
							
						}
					public void onFinish(){
						Intent intent = new Intent(FourthGameActivity.this,  FifthGameActivity.class);
						//passing data
						intent.putExtra("gameOneTime", gameOneTime);
						intent.putExtra("gameTwoTime", gameTwoTime);
						intent.putExtra("gameThreeTime", gameThreeTime);
						intent.putExtra("gameFourTime", gameFourTime);
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
								gameFourTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
								gameFourTime = (double)Math.round(gameFourTime * 1000+.5)/1000;
								//user cannot put in another answer if they answered correctly
								inputNumber.setFocusable(false);
								//goes to next activity
								new CountDownTimer(2000,1000){
									public void onTick(long millisUntilFinished){
										
									}
								public void onFinish(){
									Intent intent = new Intent(FourthGameActivity.this,  FifthGameActivity.class);
									//passing data
									intent.putExtra("gameOneTime", gameOneTime);
									intent.putExtra("gameTwoTime", gameTwoTime);
									intent.putExtra("gameThreeTime", gameThreeTime);
									intent.putExtra("gameFourTime", gameFourTime);
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
