package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;

import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class ReactionTimesActivity extends Activity {
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double gameThreeTime;
	public static double gameFourTime;
	public static double gameFiveTime;
	public static TextView timeOne;
	public static TextView timeTwo;
	public static TextView timeThree;
	public static TextView timeFour;
	public static TextView timeFive;
	public static TextView timeTotal;
	public static Button areYouDrunk;
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
		
		setContentView(R.layout.activity_reaction_times);
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
			gameFiveTime = extras.getDouble("gameFiveTime");
		}
		timeOne = (TextView) findViewById(R.id.timeOne);
		timeTwo = (TextView) findViewById(R.id.timeTwo);
		timeThree = (TextView) findViewById(R.id.timeThree);
		timeFour = (TextView) findViewById(R.id.timeFour);
		timeFive = (TextView) findViewById(R.id.timeFive);
		timeTotal = (TextView) findViewById(R.id.timeTotal);
		areYouDrunk = (Button) findViewById(R.id.areYouDrunk);
		font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		
		timeOne.setTypeface(font);
		timeTwo.setTypeface(font);
		timeThree.setTypeface(font);
		timeFour.setTypeface(font);
		timeFive.setTypeface(font);
		timeTotal.setTypeface(font);
		
		
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		setResVals(display,displayMetrics);
		        
		        
		//resizing and relocating buttons to prefer every device
		areYouDrunk.setY((int)(realHeight*.6));
        ViewGroup.LayoutParams areyoudrunkparams = areYouDrunk.getLayoutParams(); 
        areyoudrunkparams.width = (int)(realWidth * .55);
        areyoudrunkparams.height = (int)(realHeight * .075);    
        areYouDrunk.setLayoutParams(areyoudrunkparams);
        
		timeOne.setY((int) (realHeight * .1));
		timeTwo.setY((int)(realHeight * .175));
		timeThree.setY((int)(realHeight * .25));
		timeFour.setY((int)(realHeight * .325));
		timeFive.setY((int)(realHeight * .4));
		timeTotal.setY((int)(realHeight *.5));
		
        //adjusting textsize for every device
        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
        double screenInches =  Math.sqrt(screenWidth+screenHeight);
        
        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
        //phoneSize of current is 7
        float conversionFactor = (float) Math.round((20.0/7.0)*1000)/1000;
        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
        float conversionFactor2 = (float) Math.round((25.0/7.0)*1000)/1000;
        float textSize2 = (float) Math.round((conversionFactor2 * screenInches)*10)/10;
        
        timeOne.setTextSize(textSize); 
        timeTwo.setTextSize(textSize);
        timeThree.setTextSize(textSize);
        timeFour.setTextSize(textSize);
        timeFive.setTextSize(textSize);
        timeTotal.setTextSize(textSize2);
        
		//rounding value to 3 decimals
		gameOneTime = (double)Math.round(gameOneTime * 1000+.5)/1000;
		gameTwoTime = (double)Math.round(gameTwoTime * 1000+.5)/1000;
		gameThreeTime = (double)Math.round(gameThreeTime * 1000+.5)/1000;
		gameFourTime = (double)Math.round(gameFourTime * 1000+.5)/1000;
		gameFiveTime = (double)Math.round(gameFiveTime * 1000+.5)/1000;
		
		timeOne.setText("Your reaction time for the first game was " + String.valueOf(gameOneTime) + " seconds");
		timeTwo.setText("Your reaction time for the second game was " + String.valueOf(gameTwoTime) + " seconds");
		timeThree.setText("Your reaction time for the first question was " + String.valueOf(gameThreeTime) + " seconds");
		timeFour.setText("Your reaction time for the second question was " + String.valueOf(gameFourTime) + " seconds");
		timeFive.setText("Your reaction time for the third question was " + String.valueOf(gameFiveTime) + " seconds");
		timeTotal.setText("Total reaction time: " + String.valueOf((double)Math.round((gameOneTime + gameTwoTime + gameThreeTime + gameFourTime
				+ gameFiveTime)*1000)/1000) + " seconds");
		buttonClick = new AlphaAnimation(1F,0.8F);
		
		areYouDrunk.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				Intent intent = new Intent(ReactionTimesActivity.this,  FinalPageActivity.class);
				//passing data
				intent.putExtra("gameOneTime", gameOneTime);
				intent.putExtra("gameTwoTime", gameTwoTime);
				intent.putExtra("gameThreeTime", gameThreeTime);
				intent.putExtra("gameFourTime", gameFourTime);
				intent.putExtra("gameFiveTime", gameFiveTime);
				startActivity(intent);
			}
		});
	}
}
