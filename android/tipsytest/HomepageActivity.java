package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class HomepageActivity extends Activity {
	
	public static int width;
	public static int height;
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
		
		setContentView(R.layout.activity_homepage);
		
		//makes it so that device volume buttons
		//affect media (app) volume rather than
		//the ringer volume
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		//instantiates button
		Button nextPageButton = (Button) findViewById(R.id.nextPage);
		Button faqPage = (Button) findViewById(R.id.faqPage);
		Button contactPage = (Button) findViewById(R.id.contactPage);
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		
        setResVals(display,displayMetrics);
        
        //resizing and relocating buttons to prefer every device
        if(realWidth == 1200 && realHeight == 1920){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.79));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.79));
        	nextPageButton.setY((int)(realHeight*.615));
        }
        else if(realWidth == 1080 && realHeight == 1920){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.76));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.76));
        	nextPageButton.setY((int)(realHeight*.585));
        }
        else if(realWidth == 800 && realHeight == 1200){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.76));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.76));
        	nextPageButton.setY((int)(realHeight*.585));
        }
        else if(realWidth == 480 && realHeight == 800){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.82));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.82));
        	nextPageButton.setY((int)(realHeight*.63));
        	Log.d("HomepageActivity","ypo");
        }
        else if(realWidth == 800 && realHeight == 1280){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.79));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.79));
        	nextPageButton.setY((int)(realHeight*.615));
        }
        else if(realWidth == 720 && realHeight == 1280){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.76));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.76));
        	nextPageButton.setY((int)(realHeight*.585));
        }
        else if(realWidth == 768 && realHeight == 1280){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.76));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.76));
        	nextPageButton.setY((int)(realHeight*.585));
        }
        else if(realWidth == 600 && realHeight == 1024){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.79));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.79));
        	nextPageButton.setY((int)(realHeight*.615));
        }
        else if(realWidth == 480 && realHeight == 854){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.83));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.83));
        	nextPageButton.setY((int)(realHeight*.65));
        }
        else if(realWidth == 320 && realHeight == 480){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.82));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.82));
        	nextPageButton.setY((int)(realHeight*.635));
        }
        else if(realWidth == 240 && realHeight == 320){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.82));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.82));
        	nextPageButton.setY((int)(realHeight*.635));
        }
        else if(realWidth == 240 && realHeight == 432){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.82));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.82));
        	nextPageButton.setY((int)(realHeight*.635));
        }
        else if(realWidth == 240 && realHeight == 400){
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.82));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.82));
        	nextPageButton.setY((int)(realHeight*.635));
        }
        else{
        	contactPage.setX((int)(realWidth*.64));
            contactPage.setY((int)(realHeight*.76));
        	faqPage.setX((int)(realWidth*.1));
            faqPage.setY((int)(realHeight*.76));
        	nextPageButton.setY((int)(realHeight*.585));
        }
        
        ViewGroup.LayoutParams nextpageparams = nextPageButton.getLayoutParams(); 
        nextpageparams.width = (int)(realWidth * .6);
        nextpageparams.height = (int)(realHeight * .09);    
        nextPageButton.setLayoutParams(nextpageparams);
        
        ViewGroup.LayoutParams faqparams = faqPage.getLayoutParams(); 
        faqparams.width = (int)(realWidth * .2);
        faqparams.height = (int)(realHeight * .035);    
        faqPage.setLayoutParams(faqparams);
        
        ViewGroup.LayoutParams contactparams = contactPage.getLayoutParams(); 
        contactparams.width = (int)(realWidth* .25);
        contactparams.height = (int)(realHeight * .035);    
        contactPage.setLayoutParams(contactparams);
 
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		buttonClick = new AlphaAnimation(1F,0.8F);
		nextPageButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				//button click effect
				v.startAnimation(buttonClick);
				
				//button sound starts
				clickSound.start();
				//when button is clicked, the activity changes from homepage to first game
				Intent intent = new Intent(HomepageActivity.this, FirstGameActivity.class);
				startActivity(intent);
			}
		});
		faqPage.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				//button click effect
				v.startAnimation(buttonClick);
				//button sound starts
				clickSound.start();
				//when button is clicked, the activity changes from homepage to first game
				Intent intent = new Intent(HomepageActivity.this, FAQsActivity.class);
				startActivity(intent);
			}
		});
		contactPage.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				//button click effect
				v.startAnimation(buttonClick);
				//button sound starts
				clickSound.start();
				//when button is clicked, the activity changes from homepage to first game
				Intent intent = new Intent(HomepageActivity.this, ContactUsActivity.class);
				startActivity(intent);
			}
		});
	}
}
