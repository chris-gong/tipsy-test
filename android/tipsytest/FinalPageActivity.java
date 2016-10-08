package com.crazystupidgames.tipsytest;

import java.lang.reflect.Method;

import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FinalPageActivity extends Activity {
	public static double gameOneTime;
	public static double gameTwoTime;
	public static double gameThreeTime;
	public static double gameFourTime;
	public static double gameFiveTime;
	public static Typeface font;
	public static float realWidth;
	public static float realHeight;
	public static Button enterButton;
	public static Button plusButton;
	public static Button minusButton;
	public static Button uberButton;
	public static Button taxiButton;
	public static Button playAgainButton;
	public static TextView instructions;
	public static TextView disclaimer;
	public static EditText times;
	public static int timesPlayed;
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
		setContentView(R.layout.activity_final_page);
		
		
		
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
		enterButton = (Button) findViewById(R.id.enter);
		plusButton = (Button) findViewById(R.id.up);
		minusButton = (Button) findViewById(R.id.down);
		instructions = (TextView) findViewById(R.id.instructions);
		uberButton = (Button) findViewById(R.id.uber);
		taxiButton = (Button) findViewById(R.id.taxi);
		playAgainButton = (Button) findViewById(R.id.playAgain);
		disclaimer = (TextView) findViewById(R.id.disclaimer);
		times = (EditText) findViewById(R.id.timesPlayed);
		
		final MediaPlayer clickSound = MediaPlayer.create(this, R.drawable.buttonsound);
		font = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		instructions.setTypeface(font);
		times.setTypeface(font);
		disclaimer.setTypeface(font);
		
		//makes edittext uneditable
		times.setKeyListener(null);
		
		//Retrieves width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		//gets size of screen in dp
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
						
		setResVals(display,displayMetrics);
		//resizing and relocating buttons to prefer every device
        
        times.setY((int)(realHeight*.246));
        ViewGroup.LayoutParams timesparams = times.getLayoutParams(); 
        timesparams.width = (int)(realWidth * .5);  
        times.setLayoutParams(timesparams);
        
        enterButton.setY((int)(realHeight*.35));
        ViewGroup.LayoutParams enterparams = enterButton.getLayoutParams(); 
        enterparams.width = (int)(realWidth * .5);
        enterparams.height = (int)(realHeight * .085);    
        enterButton.setLayoutParams(enterparams);
         
        plusButton.setX((int)(realWidth*.8));
        plusButton.setY((int)(realHeight*.23));
        ViewGroup.LayoutParams plusparams = plusButton.getLayoutParams(); 
        plusparams.width = (int)(realWidth * .1);
        plusparams.height = (int)(realHeight * .035);    
        plusButton.setLayoutParams(plusparams);
        
        minusButton.setX((int)(realWidth*.8));
        minusButton.setY((int)(realHeight*.265));
        ViewGroup.LayoutParams minusparams = minusButton.getLayoutParams(); 
        minusparams.width = (int)(realWidth * .1);
        minusparams.height = (int)(realHeight * .035);    
        minusButton.setLayoutParams(minusparams);
        
        
        uberButton.setY((int)(realHeight*.25));
        ViewGroup.LayoutParams uberparams = uberButton.getLayoutParams(); 
        uberparams.width = (int)(realWidth * .5);
        uberparams.height = (int)(realHeight * .085);    
        uberButton.setLayoutParams(uberparams);
        
        taxiButton.setY((int)(realHeight*.4));
        ViewGroup.LayoutParams taxiparams = taxiButton.getLayoutParams(); 
        taxiparams.width = (int)(realWidth * .5);
        taxiparams.height = (int)(realHeight * .085);    
        taxiButton.setLayoutParams(taxiparams);
        
        playAgainButton.setY((int)(realHeight*.55));
        ViewGroup.LayoutParams playagainparams = playAgainButton.getLayoutParams(); 
        playagainparams.width = (int)(realWidth * .5);
        playagainparams.height = (int)(realHeight * .085);    
        playAgainButton.setLayoutParams(playagainparams);
        
        instructions.setY((int)(realHeight*.125));
        disclaimer.setY((int)(realHeight*.7));
        //adjusting textsize for every device 
       
        	
        double screenWidth =  Math.pow(realWidth/displayMetrics.xdpi,2);
        double screenHeight =  Math.pow(realHeight/displayMetrics.ydpi,2);
        double screenInches =  Math.sqrt(screenWidth+screenHeight);
        
        //x = (currentFontSize/phoneSize of current in inches) * phoneSize of new
        //phoneSize of current is 7
        float conversionFactor = (float) Math.round((22.0/7.0)*1000)/1000;
        float textSize = (float) Math.round((conversionFactor * screenInches)*10)/10;
        float conversionFactor2 = (float) Math.round((30.0/7.0)*1000)/1000;
        float textSize2 = (float) Math.round((conversionFactor2 * screenInches)*10)/10;
        float conversionFactor3 = (float) Math.round((18.0/7.0)*1000)/1000;
        float textSize3 = (float) Math.round((conversionFactor3 * screenInches)*10)/10;
        instructions.setTextSize(textSize);
        times.setTextSize(textSize2);
        disclaimer.setTextSize(textSize3);
        
        instructions.setText("How many times have you played this game before?");
        times.setText("0 times");
        disclaimer.setText("DISCLAIMER: This test is not 100% accurate, remember to always have someone sober behind the wheel");
        
        uberButton.setVisibility(View.INVISIBLE);
		taxiButton.setVisibility(View.INVISIBLE);
		playAgainButton.setVisibility(View.INVISIBLE);
		disclaimer.setVisibility(View.INVISIBLE);
		buttonClick = new AlphaAnimation(1F,0.8F);
		
        plusButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				if(timesPlayed <3){
					timesPlayed++;
				}
				if(timesPlayed==1){
					times.setText("1 time");
					minusButton.setEnabled(true);
				}
				if(timesPlayed==2){
					times.setText("2 times");
				}
				if(timesPlayed==3){
					times.setText("More than twice");
					plusButton.setEnabled(false);
				}
			}
        });
        minusButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.startAnimation(buttonClick);
				if(timesPlayed >0){
					timesPlayed--;
				}
				if(timesPlayed==0){
					times.setText("0 times");
					minusButton.setEnabled(false);
				}
				if(timesPlayed==1){
					times.setText("1 time");
				}
				if(timesPlayed==2){
					times.setText("2 times");
					plusButton.setEnabled(true);
				}
			}
        });
        enterButton.setOnClickListener(new View.OnClickListener(){
			@Override
			
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				enterButton.setVisibility(View.INVISIBLE);
				plusButton.setVisibility(View.INVISIBLE);
				minusButton.setVisibility(View.INVISIBLE);
				times.setVisibility(View.INVISIBLE);
				uberButton.setVisibility(View.VISIBLE);
				taxiButton.setVisibility(View.VISIBLE);
				playAgainButton.setVisibility(View.VISIBLE);
				disclaimer.setVisibility(View.VISIBLE);
				double threshold = .104;
				double conversion;
				double totalTime = gameOneTime + gameTwoTime + gameThreeTime + gameFourTime + gameFiveTime;
				if(timesPlayed < 3){
				conversion = 1/(2*Math.sqrt(totalTime));	
				
				}
				else{
					conversion = .93*(1/(2*Math.sqrt(totalTime)));
					
				}
				if(conversion > threshold){
					instructions.setText("Our tests show that you are safe to drive, but if you feel tipsy,"
							+ " check out these options");
				}
				else{
					instructions.setText("Our tests show that you are unsafe to drive, we recommend these options");
				}
				
			}
			
        });
        playAgainButton.setOnClickListener(new View.OnClickListener(){
			@Override
			
			public void onClick(View v){  
				v.startAnimation(buttonClick);
				clickSound.start();
				Intent intent = new Intent(FinalPageActivity.this,  HomepageActivity.class);
				startActivity(intent);
			}
        });
        uberButton.setOnClickListener(new View.OnClickListener(){
			@Override
			
			public void onClick(View v){
				v.startAnimation(buttonClick);
				clickSound.start();
				//opening up uber app on Google Play store
				final String appPackageName = "com.ubercab&hl=en";
				try{
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				}
				//in case user doesn't have google play
				catch(android.content.ActivityNotFoundException anfe){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/"
							+ "details?id="+appPackageName)));
				}
			}
        });
        taxiButton.setOnClickListener(new View.OnClickListener(){
    		@Override
    		
    		public void onClick(View v){  
    			v.startAnimation(buttonClick);
    			clickSound.start();
    			//opening up taxi app on Google Play store
    			final String appPackageName = "com.taxis99&hl=en";
				try{
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				}
				//in case user doesn't have google play
				catch(android.content.ActivityNotFoundException anfe){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/"
							+ "details?id="+appPackageName)));
				}
    		}
        });
	}
	

	
}
