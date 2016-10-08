package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;

import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.Display;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class SecGameActivity extends Activity {
	public Chronometer timer;
	public static Button startButton;
	public static Button circleButton;
	public static TextView reactionTime;
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double minimumDist;
	public static boolean isStartPressed;
	public static int timesPressed;
	public static int width;
	public static int height;
	public static int reducedWidth;
	public static int reducedWidth2;
	public static int reducedHeight;
	public static int reducedHeight2;
	public static int startWidth;
	public static int endWidth;
	public static int startHeight;
	public static int endHeight;
	public static int randomX;
	public static int randomY;
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
	//if circle is certain disance away from last circle it's good
	//this method uses recursion (method never used)
	public static boolean locationIsFarEnough(int x1, int y1, int x2, int y2, double minDistance){
		double distance = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		if(distance < minDistance){
			x2 = (int) (Math.random() * (endWidth - startWidth)) + startWidth;
			y2 = (int) (Math.random() * (endHeight - startHeight)) + startHeight;
			locationIsFarEnough(x1,y1,x2,y2,minDistance);
		}
		
		else{
		return true;
		}
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//next two lines remove title bar at the top of the screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_sec_game);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		isStartPressed = false;
		timesPressed = 0;
		
		//retrieving data from past activity
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			gameOneTime = extras.getDouble("gameOneTime");
		}
		timer = new Chronometer(this);
		startButton = (Button) findViewById(R.id.startButton);
		circleButton = (Button) findViewById(R.id.circleButton);
		reactionTime = (TextView) findViewById(R.id.reactionTime);
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		Display display = getWindowManager().getDefaultDisplay();
		
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		
        setResVals(display,displayMetrics);
        
        
        //resizing and relocating buttons to prefer every device
        
        startButton.setY((int)(realHeight*.3));
        ViewGroup.LayoutParams startparams = startButton.getLayoutParams(); 
        startparams.width = (int)(realWidth * .5);
        startparams.height = (int)(realHeight * .085);    
        startButton.setLayoutParams(startparams);
        
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
        
        ViewGroup.LayoutParams circleparams = circleButton.getLayoutParams(); 
        circleparams.width = (int)(realWidth * .25);
        circleparams.height = (int)(realWidth * .25);    
        circleButton.setLayoutParams(circleparams);
        buttonClick = new AlphaAnimation(1F,0.8F);
        
		startButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				
				if(isStartPressed==false){
					//startButton is now invisible
					startButton.setVisibility(View.INVISIBLE);
					isStartPressed = true; 
					//Retrieves width and height of screen
					
					width = (int) realWidth;
					height = (int) realHeight;
					  
					//reducing width to 3/4 of its original
					//and height to 1/2 of its original
					reducedWidth = (int) (.1 * width);
					reducedWidth2 = (int) (.375 * width);
					startWidth = reducedWidth;
					endWidth = width - reducedWidth2;
					reducedHeight = (int) (.35 * height);
					reducedHeight2 = (int) (.35 * height);
					startHeight = reducedHeight;
					endHeight = height - reducedHeight2;
					//picking random location to put circleButton in
					randomX = (int) (Math.random() * (endWidth - startWidth)) + startWidth;
					randomY = (int) (Math.random() * (endHeight - startHeight)) + startHeight;
					
					//set button to random location
					circleButton.setX(randomX);
					circleButton.setY(randomY);
					circleButton.setVisibility(View.VISIBLE);
					
					//stop watch that begins at current time
					//starts once start button is pressed and circlebutton is placed
					timer.setBase(SystemClock.elapsedRealtime());
					timer.start();
					
				}
				
			}
			
		});
		
		circleButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				
				//picking and setting random location for circlebutton
				//until pressed 5 times
				if(isStartPressed == true && timesPressed < 5){
					
					timesPressed++;
					//picking random location to put circleButton in
					randomX = (int) (Math.random() * (endWidth - startWidth)) + startWidth;
					randomY = (int) (Math.random() * (endHeight - startHeight)) + startHeight;
					minimumDist = Math.sqrt(Math.pow(width*.3,2)+Math.pow(height*.3,2));
					
					
					//set button to random location
					circleButton.setX(randomX);
					circleButton.setY(randomY);
					
					
				}
				//after 5 button presses, game ends and
				//user gets his reaction time
				if(timesPressed == 5){
					clickSound.start();
					circleButton.setVisibility(View.INVISIBLE);
					timer.stop();
					//current time minus timer's beginning time
					//must be divided by 1000.0 because double divided by int creates int
					gameTwoTime = (SystemClock.elapsedRealtime() - timer.getBase())/1000.0;
					//rounding value to 3 decimals
					gameTwoTime = (double)Math.round(gameTwoTime * 1000+.5)/1000;
					
					//telling the user how he or she performed
					font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
					reactionTime.setTypeface(font);
					reactionTime.setText("Your reaction time was " + Double.toString(gameTwoTime)
							+ " seconds");
					
					//goes to next activity in 3 seconds
					new CountDownTimer(2000,1000){
						public void onTick(long millisUntilFinished){
							
						}
					public void onFinish(){
						Intent intent = new Intent(SecGameActivity.this,  ThirdGameActivity.class);
						//passing data
						intent.putExtra("gameOneTime", gameOneTime);
						intent.putExtra("gameTwoTime", gameTwoTime);
						startActivity(intent);
					}
					}
					.start();
				}
			}
			
		});
		
	}
}
