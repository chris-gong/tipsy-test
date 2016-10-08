//
//  GameThree.m
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "GameThree.h"
#import "QuestionOne.h"
#import <AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVFoundation.h>
@interface GameThree ()
{
    float screenW;
    float screenH;
    NSString *soundPath;
    NSURL *soundUrl;
    AVAudioPlayer *player;
}
@end

@implementation GameThree
@synthesize gameOneTime, gameTwoTime,startButton,imageView;
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
    
    startButton= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * startImage = [UIImage imageNamed:@"startbutton.png"];
    [startButton setBackgroundImage:startImage forState:UIControlStateNormal];
    [startButton addTarget:self action:@selector(myAction:) forControlEvents: UIControlEventTouchUpInside];
    
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        startButton.frame = CGRectMake((screenW-(screenW *.6))/2,screenH *.4,screenW * .6,screenH * .1);
        [imageView addSubview:startButton];
        
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        startButton.frame = CGRectMake((screenW-(screenW *.6))/2,screenH *.4,screenW * .6,screenH * .1);
        [imageView addSubview:startButton];
        
        
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        startButton.frame = CGRectMake((screenW-(screenW *.55))/2,screenH *.4,screenW * .55,screenH * .12);
        [imageView addSubview:startButton];
        
    }
    
    else{
        startButton.frame = CGRectMake((screenW-(screenW *.55))/2,screenH *.4,screenW * .55,screenH * .12);
        [imageView addSubview:startButton];
       
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
    [self performSegueWithIdentifier:@"questionOneSegue" sender:(id)sender];
}
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    QuestionOne *qonevc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"questionOneSegue"]){
        
        qonevc.gameOneTime = gameOneTime;
        qonevc.gameTwoTime = gameTwoTime;
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
