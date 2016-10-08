//
//  GameTwo.m
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "GameTwo.h"
#import "GameThree.h"
#import <AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVFoundation.h>
@interface GameTwo ()
{
    int timesPressed;
    BOOL isStartPressed;
    float screenW;
    float screenH;
    int timeTillExit;
    NSTimer *exitTimer;
    CFTimeInterval start;
    CFTimeInterval elapsedTime;
    NSString *soundPath;
    NSURL *soundUrl;
    AVAudioPlayer *player;
}
@end

@implementation GameTwo
@synthesize imageView, gameOneTime, gameTwoTime, circleButton, reactionTime, startButton;
- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    //buttons are clickable in imageView
    [imageView setUserInteractionEnabled:YES];
    soundPath = [[NSBundle mainBundle] pathForResource:@"buttonsound" ofType:@"mp3"];
    soundUrl = [NSURL fileURLWithPath:soundPath];
    
    player = [[AVAudioPlayer alloc] initWithContentsOfURL:soundUrl error:nil];
    float scale = [[UIScreen mainScreen]scale];
    float ppi = scale * ((UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad)?132:163);
    float width = ([[UIScreen mainScreen]bounds].size.width*scale);
    float height =([[UIScreen mainScreen]bounds].size.height*scale);
    float horizontal = width/ppi;
    float vertical = height/ppi;
    float diagonal = sqrt(pow(horizontal,2)+pow(vertical,2));
    width = roundf(width*10)/10;
    height = roundf(height*10)/10;
    diagonal = roundf(diagonal*10)/10;
    
    screenW =self.view.frame.size.width;
    screenH =self.view.frame.size.height;
    isStartPressed = NO;
    timesPressed = 0;
    
    startButton= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * startImage = [UIImage imageNamed:@"startbutton.png"];
    [startButton setBackgroundImage:startImage forState:UIControlStateNormal];
    [startButton addTarget:self action:@selector(myAction:) forControlEvents: UIControlEventTouchUpInside];
    
    circleButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * circleImage = [UIImage imageNamed:@"orangebutton.png"];
    [circleButton setBackgroundImage:circleImage forState:UIControlStateNormal];
    [circleButton addTarget:self action:@selector(myAction2:) forControlEvents: UIControlEventTouchUpInside];
    reactionTime = [[UILabel alloc]init];
    reactionTime.textAlignment = NSTextAlignmentCenter;
    //sizeToFit
    reactionTime.numberOfLines = 0;
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        startButton.frame = CGRectMake((screenW-(screenW *.6))/2,screenH *.3,screenW * .6,screenH * .1);
        circleButton.frame = CGRectMake((screenW-(screenW *.375))/2,screenH*.4,screenW *.275,screenW *.275);
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 31];
        circleButton.hidden = YES;
        [imageView addSubview:startButton];
        [imageView addSubview:circleButton];
        [imageView addSubview:reactionTime];
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        startButton.frame = CGRectMake((screenW-(screenW *.6))/2,screenH *.3,screenW * .6,screenH * .1);
        circleButton.frame = CGRectMake((screenW-(screenW *.375))/2,screenH*.4,screenW *.275,screenW *.275);
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        if(diagonal == 4.7 || height == 1334){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 28];
        }
        else if(diagonal == 4.0 || height == 1136){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 25];
        }
        circleButton.hidden = YES;
        [imageView addSubview:startButton];
        [imageView addSubview:circleButton];
        [imageView addSubview:reactionTime];
        
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        startButton.frame = CGRectMake((screenW-(screenW *.55))/2,screenH *.3,screenW * .55,screenH * .12);
        circleButton.frame = CGRectMake((screenW-(screenW *.35))/2,screenH*.4,screenW *.23,screenW *.23);
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        circleButton.hidden = YES;
        [imageView addSubview:startButton];
        [imageView addSubview:circleButton];
        [imageView addSubview:reactionTime];
    }
    
    else{
        startButton.frame = CGRectMake((screenW-(screenW *.55))/2,screenH *.3,screenW * .55,screenH * .12);
        circleButton.frame = CGRectMake((screenW-(screenW *.35))/2,screenH*.4,screenW *.23,screenW *.23);
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        circleButton.hidden = YES;
        [imageView addSubview:startButton];
        [imageView addSubview:circleButton];
        [imageView addSubview:reactionTime];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//supports portrait and upside down orientations
-(BOOL)shouldAutorotate{
    
    return YES;
}

-(NSUInteger)supportedInterfaceOrientations{
    return(UIInterfaceOrientationMaskAll);
}
-(void)myAction:(id)sender{
    
    [player play];
    if(!isStartPressed){
    isStartPressed = YES;
    startButton.hidden = YES;
    int randomX= (int)((screenW*.1)+fmod(arc4random(),(screenW * .6)));
    int randomY= (int)((screenH*.3)+fmod(arc4random(),(screenH * .45)));
    circleButton.frame = CGRectMake(randomX, randomY, circleButton.frame.size.width, circleButton.frame.size.height);
    circleButton.hidden = NO;
    start = CACurrentMediaTime();
    }
}
-(void)myAction2:(id)sender{
    
    [player play];
    if(isStartPressed && timesPressed < 5){
        timesPressed++;
        int randomX= (int)((screenW*.1)+fmod(arc4random(),(screenW * .6)));
        int randomY= (int)((screenH*.3)+fmod(arc4random(),(screenH * .45)));
        circleButton.frame = CGRectMake(randomX, randomY, circleButton.frame.size.width, circleButton.frame.size.height);
    }
    if(timesPressed==5){
        circleButton.hidden = YES;
        elapsedTime = CACurrentMediaTime() - start;
        gameTwoTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        
        reactionTime.text = [NSString stringWithFormat:@"Your reaction time was %@ seconds",[NSString stringWithFormat:@"%.3f",gameTwoTime]];
        timeTillExit = 3;
        exitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod:) userInfo: nil repeats: YES];
    }
    
}

-(void)timerMethod:(id)sender{
    timeTillExit--;
    if(timeTillExit==0){
        [exitTimer invalidate];
        exitTimer = nil;
        [self performSegueWithIdentifier:@"gameThreeSegue" sender:(id)sender];
    }
}
//transferring data between view controllers
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    GameThree *gamethreevc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"gameThreeSegue"]){
        
        gamethreevc.gameOneTime = gameOneTime;
        gamethreevc.gameTwoTime = gameTwoTime;
    }
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
