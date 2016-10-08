package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class FirstGameActivity extends Activity {
	
	public Chronometer timer;
	public static double gameOneTime;
	public static boolean isCircleVisible;
	public static boolean isCircleOrange;
	public static boolean isCircleOrangeClicked;
	public static Button circleButton;
	public static Button startButton;
	public static TextView reactionTime;
	public static Typeface font;
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
		
		setContentView(R.layout.activity_first_game);
		//makes it so that device volume buttons
		//affect media (app) volume rather than
		//the ringer volume
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		timer = new Chronometer(this);
		isCircleVisible = false;
		isCircleOrange = false;
		isCircleOrangeClicked = false;
		
		startButton = (Button) findViewById(R.id.startGame1);
		circleButton = (Button) findViewById(R.id.circleButton);
		reactionTime = (TextView) findViewById(R.id.reactionTime1);
		
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		//Retrieves width and height of screen
				Display display = getWindowManager().getDefaultDisplay();
				
				//gets size of screen in dp
				DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		        setResVals(display,displayMetrics);
		        
		        
		        
		        //resizing and relocating buttons to prefer every device
		        
		        
		        startButton.setY((int)(realHeight*.25));
		        ViewGroup.LayoutParams startparams = startButton.getLayoutParams(); 
		        startparams.width = (int)(realWidth * .5);
		        startparams.height = (int)(realHeight * .085);    
		        startButton.setLayoutParams(startparams);
		         
		        circleButton.setY((int)(realHeight*.375));
		        ViewGroup.LayoutParams circleparams = circleButton.getLayoutParams(); 
		        circleparams.width = (int)(realWidth * .375);
		        circleparams.height = (int)(realWidth * .375);    
		        circleButton.setLayoutParams(circleparams);
		         
		        reactionTime.setY((int)(realHeight*.69));
		        //adjusting textsize for every device 
		       
		        	
		        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
		        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
		        double screenInches =  Math.sqrt(screenWidth+screenHeight);
		        
		        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
		        //phoneSize of current is 7
		        float conversionFactor = (float) Math.round((26.0/7.0)*1000)/1000;
		        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
		        reactionTime.setTextSize(textSize);
		        buttonClick = new AlphaAnimation(1F,0.8F);
		        
		startButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				//if circle button is not visible
				if(isCircleVisible == false){
					//startbutton goes away
					startButton.setVisibility(View.INVISIBLE);
					
					//makes button visible and changes button image
					circleButton.setVisibility(View.VISIBLE);
					circleButton.setBackgroundResource(R.drawable.graybutton);
					isCircleVisible = true;
					//generates random number between 3 and 6 (not including 6)
					int startTime = (int) (Math.random() * 3) +3;
					
					//creates a timer and code inside method is executed
					//once a certain amount of milliseconds passes
					new CountDownTimer(startTime*1000,1000){
						public void onTick(long millisUntilFinished){
							
						}
					public void onFinish(){
						//replaces button background
						circleButton.setBackgroundResource(R.drawable.orangebutton);
						isCircleOrange = true; 
						
						//stop watch that begins at current time
						//starts once button turns orange
						
						timer.setBase(SystemClock.elapsedRealtime());
						timer.start();
					}
					}
					.start();
					
					
				}
			}
		});
		
		circleButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){ 
				v.startAnimation(buttonClick);
				//on first click of the orange button, time is stopped
				//and reaction time is recorded
				if(isCircleOrange == true && isCircleOrangeClicked == false){
					clickSound.start();
					timer.stop();
					//current time minus timer's beginning time
					//must be divided by 1000.0 because double divided by int creates int
					gameOneTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
					//rounding value to 3 decimals
					gameOneTime = (double)Math.round(gameOneTime * 1000+.5)/1000;
					isCircleOrangeClicked = true;
					
					//telling the user how he or she performed
					font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
					reactionTime.setTypeface(font);
					reactionTime.setText("Your reaction time was " + Double.toString(gameOneTime)
							+ " seconds");
					
					//goes to next activity in 3 seconds
					new CountDownTimer(2000,1000){
						public void onTick(long millisUntilFinished){
							
						}
					public void onFinish(){
						Intent intent = new Intent(FirstGameActivity.this,  SecGameActivity.class);
						//passing data
						intent.putExtra("gameOneTime", gameOneTime);
						startActivity(intent);
					}
					}
					.start();
					
					
					
				}
				
			}
			
		});
		
	}
}
