//
//  FinalPage.m
//  TipsyTest
//
//  Created by Xu Wu on 8/19/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "FinalPage.h"
#import <AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVFoundation.h>
@interface FinalPage ()
{
    float screenW;
    float screenH;
    int timesPlayed;
    NSString *soundPath;
    NSURL *soundUrl;
    AVAudioPlayer *player;
}
@end

@implementation FinalPage
@synthesize gameOneTime, gameTwoTime,gameThreeTime, gameFourTime,gameFiveTime,imageView,recommendation,enter,callUber,callTaxi,replay, userAnswer, up, down, adBanner, disclaimer;
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
    recommendation = [[UILabel alloc]init];
    recommendation.textAlignment = NSTextAlignmentCenter;
    recommendation.numberOfLines = 0;
    recommendation.text = @"How many times have you played this game before?";
    disclaimer = [[UILabel alloc]init];
    disclaimer.textAlignment = NSTextAlignmentCenter;
    disclaimer.numberOfLines = 0;
    disclaimer.text = @"DISCLAIMER: This test is not 100% accurate, remember to always have someone sober behind the wheel";
    enter= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * image = [UIImage imageNamed:@"enterbutton.png"];
    [enter setBackgroundImage:image forState:UIControlStateNormal];
    [enter addTarget:self action:@selector(myAction:) forControlEvents: UIControlEventTouchUpInside];
    callUber = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * image2 = [UIImage imageNamed:@"uberbutton.png"];
    [callUber setBackgroundImage:image2 forState:UIControlStateNormal];
    [callUber addTarget:self action:@selector(myAction2:) forControlEvents: UIControlEventTouchUpInside];
    callTaxi= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * image3 = [UIImage imageNamed:@"taxibutton.png"];
    [callTaxi setBackgroundImage:image3 forState:UIControlStateNormal];
    [callTaxi addTarget:self action:@selector(myAction3:) forControlEvents: UIControlEventTouchUpInside];
    replay= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * image4 = [UIImage imageNamed:@"replaybutton.png"];
    [replay setBackgroundImage:image4 forState:UIControlStateNormal];
    [replay addTarget:self action:@selector(myAction4:) forControlEvents: UIControlEventTouchUpInside];
    up = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * upArrow = [UIImage imageNamed:@"plusbutton.png"];
    [up setBackgroundImage:upArrow forState:UIControlStateNormal];
    [up addTarget:self action:@selector(changeStepUp:) forControlEvents: UIControlEventTouchUpInside];
    down = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * downArrow = [UIImage imageNamed:@"minusbutton.png"];
    [down setBackgroundImage:downArrow forState:UIControlStateNormal];
    [down addTarget:self action:@selector(changeStepDown:) forControlEvents: UIControlEventTouchUpInside];
    userAnswer = [[UITextField alloc]init];
    userAnswer.textColor = [UIColor blackColor];
    userAnswer.backgroundColor = [UIColor orangeColor];
    timesPlayed = 0;
    userAnswer.text = [NSString stringWithFormat:@"%i times",timesPlayed];
    userAnswer.enabled = NO;
    
    
    //VERY IMPORTANT BELOW
    
    userAnswer.delegate = self;
    adBanner = [[ADBannerView alloc]init];
    adBanner.delegate = self;
    adBanner.alpha = 0;
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        
        recommendation.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        recommendation.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        disclaimer.frame = CGRectMake(0,screenH*.75,screenW,screenH*.15);
        disclaimer.font = [UIFont fontWithName:@"Century Gothic" size : 17];
        userAnswer.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.04);
        userAnswer.font = [UIFont fontWithName:@"Century Gothic" size: 25];
        up.frame = CGRectMake(screenW*.85, screenH *.22, screenW*.1, screenH*.05);
        down.frame = CGRectMake(screenW*.85, screenH *.27, screenW*.1, screenH*.05);
        enter.frame = CGRectMake(screenW*.2, screenH *.35, screenW*.6, screenH*.08);
        callUber.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.08);
        callTaxi.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.08);
        replay.frame = CGRectMake(screenW*.2, screenH *.65, screenW*.6, screenH*.08);
        callUber.hidden = YES;
        callTaxi.hidden = YES;
        replay.hidden = YES;
        disclaimer.hidden = YES;
        adBanner.frame = CGRectMake(0,screenH * .88,screenW,screenH*.12);
        [imageView addSubview:adBanner];
        [imageView addSubview:up];
        [imageView addSubview:down];
        [imageView addSubview:recommendation];
        [imageView addSubview:userAnswer];
        [imageView addSubview:enter];
        [imageView addSubview:callUber];
        [imageView addSubview:callTaxi];
        [imageView addSubview:replay];
        [imageView addSubview:disclaimer];
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        
        recommendation.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        userAnswer.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.04);
        disclaimer.frame = CGRectMake(0,screenH*.75,screenW,screenH*.15);
        
        up.frame = CGRectMake(screenW*.85, screenH *.22, screenW*.1, screenH*.05);
        down.frame = CGRectMake(screenW*.85, screenH *.27, screenW*.1, screenH*.05);
        enter.frame = CGRectMake(screenW*.2, screenH *.35, screenW*.6, screenH*.08);
        callUber.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.08);
        callTaxi.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.08);
        replay.frame = CGRectMake(screenW*.2, screenH *.65, screenW*.6, screenH*.08);
        callUber.hidden = YES;
        callTaxi.hidden = YES;
        replay.hidden = YES;
        disclaimer.hidden = YES;
        
        
        
        
        if(diagonal == 4.7 || height == 1334){
            recommendation.font = [UIFont fontWithName:@"Century Gothic" size : 17];
            userAnswer.font = [UIFont fontWithName:@"Century Gothic" size: 22];
            disclaimer.font = [UIFont fontWithName:@"Century Gothic" size : 15];
            adBanner.frame = CGRectMake(0,screenH * .88,screenW,screenH*.15);
        }
        else if(diagonal == 4.0 || height == 1136){
            recommendation.font = [UIFont fontWithName:@"Century Gothic" size: 15];
            userAnswer.font = [UIFont fontWithName:@"Century Gothic" size: 20];
            adBanner.frame = CGRectMake(0,screenH * .87,screenW,screenH*.15);
            disclaimer.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        }
        
        [imageView addSubview:adBanner];
        [imageView addSubview:up];
        [imageView addSubview:down];
        [imageView addSubview:recommendation];
        [imageView addSubview:userAnswer];
        [imageView addSubview:enter];
        [imageView addSubview:callUber];
        [imageView addSubview:callTaxi];
        [imageView addSubview:replay];
        [imageView addSubview:disclaimer];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        
        recommendation.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        recommendation.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        disclaimer.frame = CGRectMake(0,screenH*.75,screenW,screenH*.12);
        disclaimer.font = [UIFont fontWithName:@"Century Gothic" size : 11];
        userAnswer.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.04);
        userAnswer.font = [UIFont fontWithName:@"Century Gothic" size: 18];
        up.frame = CGRectMake(screenW*.85, screenH *.22, screenW*.1, screenH*.05);
        down.frame = CGRectMake(screenW*.85, screenH *.27, screenW*.1, screenH*.05);
        enter.frame = CGRectMake(screenW*.2, screenH *.35, screenW*.6, screenH*.08);
        callUber.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.08);
        callTaxi.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.08);
        replay.frame = CGRectMake(screenW*.2, screenH *.65, screenW*.6, screenH*.08);
        callUber.hidden = YES;
        callTaxi.hidden = YES;
        replay.hidden = YES;
        disclaimer.hidden = YES;
        adBanner.frame = CGRectMake(0,screenH * .84,screenW,screenH*.12);
        [imageView addSubview:adBanner];
        [imageView addSubview:up];
        [imageView addSubview:down];
        [imageView addSubview:recommendation];
        [imageView addSubview:userAnswer];
        [imageView addSubview:enter];
        [imageView addSubview:callUber];
        [imageView addSubview:callTaxi];
        [imageView addSubview:replay];
        [imageView addSubview:disclaimer];
    }
    
    else{
        
        recommendation.frame = CGRectMake(0,screenH*.1,screenW,screenH*.15);
        recommendation.font = [UIFont fontWithName:@"Century Gothic" size : 13];
        disclaimer.frame = CGRectMake(0,screenH*.75,screenW,screenH*.15);
        disclaimer.font = [UIFont fontWithName:@"Century Gothic" size : 11];
        userAnswer.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.04);
        userAnswer.font = [UIFont fontWithName:@"Century Gothic" size: 18];
        up.frame = CGRectMake(screenW*.85, screenH *.22, screenW*.1, screenH*.05);
        down.frame = CGRectMake(screenW*.85, screenH *.27, screenW*.1, screenH*.05);
        enter.frame = CGRectMake(screenW*.2, screenH *.35, screenW*.6, screenH*.08);
        callUber.frame = CGRectMake(screenW*.2, screenH *.25, screenW*.6, screenH*.08);
        callTaxi.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.08);
        replay.frame = CGRectMake(screenW*.2, screenH *.65, screenW*.6, screenH*.08);
        callUber.hidden = YES;
        callTaxi.hidden = YES;
        replay.hidden = YES;
        disclaimer.hidden = YES;
        adBanner.frame = CGRectMake(0,screenH * .84,screenW,screenH*.12);
        [imageView addSubview:adBanner];
        [imageView addSubview:up];
        [imageView addSubview:down];
        [imageView addSubview:recommendation];
        [imageView addSubview:userAnswer];
        [imageView addSubview:enter];
        [imageView addSubview:callUber];
        [imageView addSubview:callTaxi];
        [imageView addSubview:replay];
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
    enter.hidden = YES;
    up.hidden = YES;
    down.hidden = YES;
    userAnswer.hidden = YES;
    callUber.hidden = NO;
    callTaxi.hidden = NO;
    replay.hidden = NO;
    disclaimer.hidden = NO;
    float threshold = .104;
    float conversion;
    float totalTime = gameOneTime + gameTwoTime + gameThreeTime + gameFourTime + gameFiveTime;
    
    [player play];
    if(timesPlayed<3){
        conversion = 1/(2*sqrtf(totalTime));
        
        
    }
    else{
        conversion = .93*(1/(2*sqrtf(totalTime)));
        
    }
    if(conversion>threshold){
        recommendation.text = @"Our tests show that you are safe to drive, but if you feel tipsy, check out these options";
    }
    else{
        recommendation.text = @"Our tests show that you are unsafe to drive, we recommend these options";
    }
    
}
-(void)myAction2:(id)sender{
   
    [player play];
    NSString *uberLink = @"itms-apps://itunes.apple.com/us/app/uber/id368677368?mt=8";
    [[UIApplication sharedApplication]openURL:[NSURL URLWithString:uberLink]];
}

-(void)myAction3:(id)sender{
    
    [player play];
    NSString *taxiLink = @"itms-apps://itunes.apple.com/us/app/easy-taxi-your-taxi-in-3-minutes/id567264775?mt=8";
    [[UIApplication sharedApplication]openURL:[NSURL URLWithString:taxiLink]];
}

-(void)myAction4:(id)sender{
    
    [player play];
    [self performSegueWithIdentifier:@"backToHomeSegue" sender:(id)sender];
}
-(void)changeStepUp:(id)sender{
    if(timesPlayed < 3){
        timesPlayed++;
        
    }
    if(timesPlayed == 1){
        userAnswer.text = [NSString stringWithFormat:@"%i time",timesPlayed];
        down.enabled = YES;
    }
    if(timesPlayed == 2){
        userAnswer.text = [NSString stringWithFormat:@"%i times",timesPlayed];
    }
    if(timesPlayed == 3){
        userAnswer.text = [NSString stringWithFormat:@"More than twice"];
        up.enabled = NO;
    }
}
-(void)changeStepDown:(id)sender{
    if(timesPlayed>0){
        timesPlayed--;
        
    }
    if(timesPlayed == 0){
        userAnswer.text = [NSString stringWithFormat:@"%i times",timesPlayed];
        down.enabled = NO;
    }
    if(timesPlayed == 1){
        userAnswer.text = [NSString stringWithFormat:@"%i time",timesPlayed];
    }
    if(timesPlayed == 2){
        userAnswer.text = [NSString stringWithFormat:@"%i times",timesPlayed];
        up.enabled = YES;
    }
}
//user can click on textfield but not type in it
//only select from tableview answers
-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string{
    return NO;
}
//prevents copy and paste
-(BOOL)canPerformAction:(SEL)action withSender:(id)sender{
    [[NSOperationQueue mainQueue]addOperationWithBlock:^{
        [[UIMenuController sharedMenuController]setMenuVisible:NO
                                                       animated:NO];
         }];
        return [super canPerformAction:action withSender:sender];
}
-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
    
    return YES;
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
