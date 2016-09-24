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

public class FAQsActivity extends Activity {
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_faqs);
		//makes it so that device volume buttons
				//affect media (app) volume rather than
				//the ringer volume
				this.setVolumeControlStream(AudioManager.STREAM_MUSIC);		
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		Button backButton = (Button) findViewById(R.id.backButton);
		
		//Retrieves width and height of screen
				Display display = getWindowManager().getDefaultDisplay();
				
				//gets size of screen in dp
				DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
				
		        setResVals(display,displayMetrics);
		        
		        //resizing and relocating buttons to prefer every device
		        backButton.setX((int)(realWidth*.0001)); 
		        backButton.setY((int)(realHeight*.00001));
		        ViewGroup.LayoutParams backButtonparams = backButton.getLayoutParams(); 
		        backButtonparams.width = (int)(realWidth * .25);
		        backButtonparams.height = (int)(realHeight * .1);    
		        backButton.setLayoutParams(backButtonparams);
		        buttonClick = new AlphaAnimation(1F,0.8F);
		        
		backButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				//when button is clicked, the activity changes from homepage to first game
				Intent intent = new Intent(FAQsActivity.this, HomepageActivity.class);
				startActivity(intent);
			}
		});
	}
}
