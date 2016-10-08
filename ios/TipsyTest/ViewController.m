//
//  ViewController.m
//  TipsyTest
//
//  Created by Xu Wu on 8/15/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "ViewController.h"
#import <AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVFoundation.h>
@interface ViewController ()
{
NSString *soundPath;
NSURL *soundUrl;
AVAudioPlayer *player;
}
@end

@implementation ViewController
@synthesize imageView;


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
    
    float screenW =self.view.frame.size.width;
    float screenH =self.view.frame.size.height;
    
    UIButton * beginButton= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIButton * faqButton= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIButton * contactButton= [UIButton buttonWithType:UIButtonTypeRoundedRect];
    UIImage * beginImage = [UIImage imageNamed:@"beginbutton.png"];
    UIImage * faqImage = [UIImage imageNamed:@"faqbutton.png"];
    UIImage * contactImage = [UIImage imageNamed:@"contactusbutton.png"];
    
    [beginButton setBackgroundImage:beginImage forState:UIControlStateNormal];
    [faqButton setBackgroundImage:faqImage forState:UIControlStateNormal];
    [contactButton setBackgroundImage:contactImage forState:UIControlStateNormal];
    
    [beginButton addTarget:self action:@selector(myAction:) forControlEvents: UIControlEventTouchUpInside];
    [faqButton addTarget:self action:@selector(myAction2:) forControlEvents: UIControlEventTouchUpInside];
    [contactButton addTarget:self action:@selector(myAction3:) forControlEvents: UIControlEventTouchUpInside];
    
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        beginButton.frame = CGRectMake((screenW-(screenW*.6))/2,screenH *.65,screenW * .6,screenH * .1);
        faqButton.frame = CGRectMake(screenW*.08,screenH *.86,screenW * .2,screenH*.035);
        contactButton.frame = CGRectMake(screenW*.64,screenH *.86,screenW * .27,screenH*.035);
        [imageView addSubview:beginButton];
        [imageView addSubview:faqButton];
        [imageView addSubview:contactButton];
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        beginButton.frame = CGRectMake((screenW-(screenW*.6))/2,screenH *.65,screenW * .6,screenH * .1);
        faqButton.frame = CGRectMake(screenW*.08,screenH *.86,screenW * .2,screenH*.035);
        contactButton.frame = CGRectMake(screenW*.64,screenH *.86,screenW * .27,screenH*.035);
        [imageView addSubview:beginButton];
        [imageView addSubview:faqButton];
        [imageView addSubview:contactButton];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        beginButton.frame = CGRectMake((screenW-(screenW*.6))/2,screenH *.65,screenW * .6,screenH * .12);
        faqButton.frame = CGRectMake(screenW*.08,screenH *.86,screenW * .2,screenH*.04);
        contactButton.frame = CGRectMake(screenW*.64,screenH *.86,screenW * .27,screenH*.04);
        [imageView addSubview:beginButton];
        [imageView addSubview:faqButton];
        [imageView addSubview:contactButton];
    }
    
    else{
        beginButton.frame = CGRectMake((screenW-(screenW*.6))/2,screenH *.65,screenW * .6,screenH * .12);
        faqButton.frame = CGRectMake(screenW*.08,screenH *.86,screenW * .2,screenH*.04);
        contactButton.frame = CGRectMake(screenW*.64,screenH *.86,screenW * .27,screenH*.04);
        [imageView addSubview:beginButton];
        [imageView addSubview:faqButton];
        [imageView addSubview:contactButton];
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
    [self performSegueWithIdentifier:@"gameOneSegue" sender:(id)sender];
    
    
}

-(void)myAction2:(id)sender{
    
   
    
    [player play];
    [self performSegueWithIdentifier:@"faqSegue" sender:(id)sender];
    
    
}

-(void)myAction3:(id)sender{
    
    [player play];
    [self performSegueWithIdentifier:@"contactSegue" sender:(id)sender];
    
    
}






@end
