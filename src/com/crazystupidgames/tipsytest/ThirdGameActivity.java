package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Chronometer;

public class ThirdGameActivity extends Activity {
	
	public static double gameOneTime;
	public static double gameTwoTime;
	public static Button startButton;
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
		//2nd line was deleted because it deleted notification bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_third_game);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//retrieving data from past activity
				Bundle extras = getIntent().getExtras();
				if(extras!=null){
					gameOneTime = extras.getDouble("gameOneTime");
					gameTwoTime = extras.getDouble("gameTwoTime");
				}
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		startButton = (Button) findViewById(R.id.startButton);
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		//gets size of screen in dp
				DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		        setResVals(display,displayMetrics);
		        
		        
		        //resizing and relocating buttons to prefer every device
		        
		        
		        ViewGroup.LayoutParams startparams = startButton.getLayoutParams(); 
		        startparams.width = (int)(realWidth * .5);
		        startparams.height = (int)(realHeight * .085);    
		        startButton.setLayoutParams(startparams);
		        buttonClick = new AlphaAnimation(1F,0.8F);		
		         
		       
		startButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
					//button sound starts
					clickSound.start();
					Intent intent = new Intent(ThirdGameActivity.this,  ThirdGameContActivity.class);
					//passing data
					intent.putExtra("gameOneTime", gameOneTime);
					intent.putExtra("gameTwoTime", gameTwoTime);
					startActivity(intent);
				
			}
			
		});
	}
}
