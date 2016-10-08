//
//  ReactionTimes.m
//  TipsyTest
//
//  Created by Xu Wu on 8/19/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "ReactionTimes.h"
#import "FinalPage.h"
#import <AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVFoundation.h>
@interface ReactionTimes ()
{
    float screenW;
    float screenH;
    NSString *soundPath;
    NSURL *soundUrl;
    AVAudioPlayer *player;
}
@end

@implementation ReactionTimes
@synthesize gameOneTime, gameTwoTime,gameThreeTime, gameFourTime,gameFiveTime,imageView,reactionTime1,reactionTime2,reactionTime3,reactionTime4,reactionTime5,nextPage, totalTime, adBanner;
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
    
    reactionTime1 = [[UILabel alloc]init];
    reactionTime1.textAlignment = NSTextAlignmentCenter;
    reactionTime1.numberOfLines = 0;
    reactionTime1.text = [NSString stringWithFormat:@"Your reaction for the first game was %.3f seconds",gameOneTime];
    reactionTime2 = [[UILabel alloc]init];
    reactionTime2.textAlignment = NSTextAlignmentCenter;
    reactionTime2.numberOfLines = 0;
    reactionTime2.text = [NSString stringWithFormat:@"Your reaction for the second game was %.3f seconds",gameTwoTime];
    reactionTime3 = [[UILabel alloc]init];
    reactionTime3.textAlignment = NSTextAlignmentCenter;
    reactionTime3.numberOfLines = 0;
    reactionTime3.text = [NSString stringWithFormat:@"Your reaction for the first question  was %.3f seconds",gameThreeTime];
    reactionTime4 = [[UILabel alloc]init];
    reactionTime4.textAlignment = NSTextAlignmentCenter;
    reactionTime4.numberOfLines = 0;
    reactionTime4.text = [NSString stringWithFormat:@"Your reaction for the second question was %.3f seconds",gameFourTime];
    reactionTime5 = [[UILabel alloc]init];
    reactionTime5.textAlignment = NSTextAlignmentCenter;
    reactionTime5.numberOfLines = 0;
    reactionTime5.text = [NSString stringWithFormat:@"Your reaction for the third question was %.3f seconds",gameFiveTime];
    totalTime = [[UILabel alloc]init];
    totalTime.textAlignment = NSTextAlignmentCenter;
    totalTime.numberOfLines = 0;
    totalTime.text = [NSString stringWithFormat:@"Total reaction time: %.3f seconds",gameOneTime + gameTwoTime + gameThreeTime+ gameFourTime+gameFiveTime];
    nextPage= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * image = [UIImage imageNamed:@"areyourdrunk.png"];
    [nextPage setBackgroundImage:image forState:UIControlStateNormal];
    [nextPage addTarget:self action:@selector(myAction:) forControlEvents: UIControlEventTouchUpInside];
    adBanner = [[ADBannerView alloc]init];
    adBanner.delegate = self;
    adBanner.alpha = 0;
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        
        reactionTime1.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        reactionTime1.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        reactionTime2.frame = CGRectMake(0,screenH*.2,screenW,screenH*.15);
        reactionTime2.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        reactionTime3.frame = CGRectMake(0,screenH*.3,screenW,screenH*.15);
        reactionTime3.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        reactionTime4.frame = CGRectMake(0,screenH*.4,screenW,screenH*.15);
        reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        reactionTime5.frame = CGRectMake(0,screenH*.5,screenW,screenH*.15);
        reactionTime5.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        totalTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        totalTime.font = [UIFont fontWithName:@"Century Gothic" size : 23];
        nextPage.frame = CGRectMake(screenW*.2,screenH*.72,screenW*.6,screenH*.08);
        adBanner.frame = CGRectMake(0,screenH * .88,screenW,screenH*.15);
        [imageView addSubview:adBanner];
        [imageView addSubview:reactionTime1];
        [imageView addSubview:reactionTime2];
        [imageView addSubview:reactionTime3];
        [imageView addSubview:reactionTime4];
        [imageView addSubview:reactionTime5];
        [imageView addSubview:totalTime];
        [imageView addSubview:nextPage];
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        
        reactionTime1.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        
        reactionTime2.frame = CGRectMake(0,screenH*.2,screenW,screenH*.15);
        
        reactionTime3.frame = CGRectMake(0,screenH*.3,screenW,screenH*.15);
        
        reactionTime4.frame = CGRectMake(0,screenH*.4,screenW,screenH*.15);
        
        reactionTime5.frame = CGRectMake(0,screenH*.5,screenW,screenH*.15);
        
        totalTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        nextPage.frame = CGRectMake(screenW*.2,screenH*.72,screenW*.6,screenH*.08);
        
        
        
        
        
        
        if(diagonal == 4.7 || height == 1334){
            reactionTime1.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            reactionTime2.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            reactionTime3.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            reactionTime5.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            totalTime.font = [UIFont fontWithName:@"Century Gothic" size : 20];
            adBanner.frame = CGRectMake(0,screenH * .88,screenW,screenH*.15);
        }
        else if(diagonal == 4.0 || height == 1136){
            reactionTime1.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            reactionTime2.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            reactionTime3.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            reactionTime5.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            totalTime.font = [UIFont fontWithName:@"Century Gothic" size : 18];
            adBanner.frame = CGRectMake(0,screenH * .87,screenW,screenH*.15);
        }
        
        
        [imageView addSubview:adBanner];
        [imageView addSubview:reactionTime1];
        [imageView addSubview:reactionTime2];
        [imageView addSubview:reactionTime3];
        [imageView addSubview:reactionTime4];
        [imageView addSubview:reactionTime5];
        [imageView addSubview:totalTime];
        [imageView addSubview:nextPage];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        
        reactionTime1.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        reactionTime1.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        reactionTime2.frame = CGRectMake(0,screenH*.2,screenW,screenH*.15);
        reactionTime2.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        reactionTime3.frame = CGRectMake(0,screenH*.3,screenW,screenH*.15);
        reactionTime3.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        reactionTime4.frame = CGRectMake(0,screenH*.4,screenW,screenH*.15);
        reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        reactionTime5.frame = CGRectMake(0,screenH*.5,screenW,screenH*.15);
        reactionTime5.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        totalTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        totalTime.font = [UIFont fontWithName:@"Century Gothic" size : 21];
        nextPage.frame = CGRectMake(screenW*.2,screenH*.73,screenW*.6,screenH*.08);
        adBanner.frame = CGRectMake(0,screenH * .84,screenW,screenH*.15);
        [imageView addSubview:adBanner];
        [imageView addSubview:reactionTime1];
        [imageView addSubview:reactionTime2];
        [imageView addSubview:reactionTime3];
        [imageView addSubview:reactionTime4];
        [imageView addSubview:reactionTime5];
        [imageView addSubview:totalTime];
    [imageView addSubview:nextPage];
    }
    
    else{
        
        reactionTime1.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        reactionTime1.font = [UIFont fontWithName:@"Century Gothic" size : 18];
        reactionTime2.frame = CGRectMake(0,screenH*.2,screenW,screenH*.15);
        reactionTime2.font = [UIFont fontWithName:@"Century Gothic" size : 18];
        reactionTime3.frame = CGRectMake(0,screenH*.3,screenW,screenH*.15);
        reactionTime3.font = [UIFont fontWithName:@"Century Gothic" size : 18];
        reactionTime4.frame = CGRectMake(0,screenH*.4,screenW,screenH*.15);
        reactionTime4.font = [UIFont fontWithName:@"Century Gothic" size : 18];
        reactionTime5.frame = CGRectMake(0,screenH*.5,screenW,screenH*.15);
        reactionTime5.font = [UIFont fontWithName:@"Century Gothic" size : 18];
        totalTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        totalTime.font = [UIFont fontWithName:@"Century Gothic" size : 21];
        nextPage.frame = CGRectMake(screenW*.2,screenH*.73,screenW*.6,screenH*.08);
        adBanner.frame = CGRectMake(0,screenH * .84,screenW,screenH*.15);
        [imageView addSubview:adBanner];
        [imageView addSubview:reactionTime1];
        [imageView addSubview:reactionTime2];
        [imageView addSubview:reactionTime3];
        [imageView addSubview:reactionTime4];
        [imageView addSubview:reactionTime5];
        [imageView addSubview:totalTime];
    [imageView addSubview:nextPage];
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
//transferring data between view controllers
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    FinalPage *fpvc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"finalPageSegue"]){
        
        fpvc.gameOneTime = gameOneTime;
        fpvc.gameTwoTime = gameTwoTime;
        fpvc.gameThreeTime = gameThreeTime;
        fpvc.gameFourTime = gameFourTime;
        fpvc.gameFiveTime = gameFiveTime;
    }
}
-(void)myAction:(id)sender{
    
    [player play];
    [self performSegueWithIdentifier:@"finalPageSegue" sender:(id)sender];
}
#pragma mark iAd Delegate Methods
-(void)bannerViewDidLoadAd:(ADBannerView *)banner{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1];
    [banner setAlpha:1];
    [UIView commitAnimations];
    
}
-(void)bannerView:(ADBannerView *)banner didFailToReceiveAdWithError:(NSError *)error{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1];
    [banner setAlpha:0];
    [UIView commitAnimations];
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
