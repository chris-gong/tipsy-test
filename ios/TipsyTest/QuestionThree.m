//
//  QuestionThree.m
//  TipsyTest
//
//  Created by Xu Wu on 8/18/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "QuestionThree.h"
#import "reactionTimes.h"
@interface QuestionThree ()
{
    float screenW;
    float screenH;
    CFTimeInterval start;
    CFTimeInterval elapsedTime;
    int timeTillExit;
    int timeTillForceExit;
    NSTimer *exitTimer;
    NSTimer *forceExitTimer;
    int answer;

}
@end

@implementation QuestionThree
@synthesize gameOneTime, gameTwoTime,gameThreeTime, gameFourTime,gameFiveTime,imageView, instructions, inputNumber, reactionTime;
- (void)viewDidLoad {
    [super viewDidLoad];
    //buttons are clickable in imageView
    [imageView setUserInteractionEnabled:YES];
    
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
    
    int randomNumber1 = 5+arc4random()%5;
    int randomNumber2 = 5+arc4random()%5;
    answer = randomNumber1 + randomNumber2;
    
    inputNumber= [[UITextField alloc] init];
    inputNumber.textColor = [UIColor blackColor];
    inputNumber.backgroundColor = [UIColor orangeColor];
    inputNumber.placeholder = @"Answer here";
    instructions = [[UILabel alloc]init];
    instructions.textAlignment = NSTextAlignmentCenter;
    instructions.numberOfLines = 0;
    reactionTime = [[UILabel alloc]init];
    reactionTime.textAlignment = NSTextAlignmentCenter;
    reactionTime.numberOfLines = 0;

    //VERY IMPORTANT BELOW
    inputNumber.delegate = self;
    inputNumber.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    inputNumber.autocorrectionType = UITextAutocorrectionTypeNo;
    inputNumber.autocapitalizationType = UITextAutocapitalizationTypeNone;
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        
        reactionTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 31];
        instructions.frame = CGRectMake(0, screenH *.125, screenW, screenH*.25);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 100];
        instructions.text = [NSString stringWithFormat:@"%i+%i=",randomNumber1,randomNumber2];
       
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.4, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 30];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        
        [imageView addSubview:inputNumber];
        
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        
        reactionTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        instructions.frame = CGRectMake(0, screenH *.15, screenW, screenH*.2);
        
        instructions.text = [NSString stringWithFormat:@"%i+%i=",randomNumber1,randomNumber2];
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.4, screenW*.6, screenH*.05);
        
        
        
        if(diagonal == 4.7 || height == 1334){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 28];
            inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 27];
            instructions.font = [UIFont fontWithName:@"Century Gothic" size : 97];
        }
        else if(diagonal == 4.0 || height == 1136){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 25];
            inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 24];
            instructions.font = [UIFont fontWithName:@"Century Gothic" size : 95];
        }
        
        
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        
        [imageView addSubview:inputNumber];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        
        reactionTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.175, screenW, screenH*.2);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 90];
        instructions.text = [NSString stringWithFormat:@"%i+%i=",randomNumber1,randomNumber2];
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        
        [imageView addSubview:inputNumber];
    }
    
    else{
        
        reactionTime.frame = CGRectMake(0,screenH*.6,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.175, screenW, screenH*.2);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 90];
        instructions.text = [NSString stringWithFormat:@"%i+%i=",randomNumber1,randomNumber2];
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.45, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        
        [imageView addSubview:inputNumber];
    }
    
    start = CACurrentMediaTime();
    timeTillForceExit = 60;
    forceExitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod:) userInfo: nil repeats: YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//supports portrait and upside down orientations
-(BOOL)shouldAutorotate{
    
    return YES;
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    NSString *input = inputNumber.text;
    if([input isEqualToString:[NSString stringWithFormat:@"%i",answer]]){
        [forceExitTimer invalidate];
        forceExitTimer = nil;
        elapsedTime = CACurrentMediaTime() - start;
        gameFiveTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        float totalTime =gameThreeTime + gameFourTime + gameFiveTime;
        totalTime  =floorf(totalTime*1000.0+.5)/1000.0;
        inputNumber.enabled = NO;
        reactionTime.frame = CGRectMake(reactionTime.frame.origin.x,reactionTime.frame.origin.y,screenW,screenH*.3);
        reactionTime.text = [NSString stringWithFormat:@"Correct, your reaction time for the last three questions was %.3f seconds",totalTime];
        timeTillExit = 3;
        exitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod2:) userInfo: nil repeats: YES];
        
    }
    else{
        reactionTime.text = @"Incorrect, try again";
    }
    return YES;
}


-(NSUInteger)supportedInterfaceOrientations{
    return(UIInterfaceOrientationMaskAll);
}
-(void)timerMethod:(id)sender{
    timeTillForceExit--;
    if(timeTillForceExit==0){
        [forceExitTimer invalidate];
        forceExitTimer = nil;
        elapsedTime = CACurrentMediaTime() - start;
        gameFiveTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        float totalTime =gameThreeTime + gameFourTime + gameFiveTime;
        totalTime  =floorf(totalTime*1000.0+.5)/1000.0;
        inputNumber.enabled = NO;
        reactionTime.frame = CGRectMake(reactionTime.frame.origin.x,reactionTime.frame.origin.y,screenW,screenH*.3);
        reactionTime.text = [NSString stringWithFormat:@"Times Up, your reaction time for the last three questions was %.3f seconds",totalTime];
        [inputNumber endEditing:YES];
        timeTillExit = 3;
        exitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod2:) userInfo: nil repeats: YES];
        
    }
}
-(void)timerMethod2:(id)sender{
    timeTillExit--;
    
    if(timeTillExit==0){
        
        [exitTimer invalidate];
        exitTimer = nil;
        [self performSegueWithIdentifier:@"reactionTimesSegue" sender:(id)sender];
        
    }
}
//transferring data between view controllers
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    ReactionTimes *rtvc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"reactionTimesSegue"]){
        
        rtvc.gameOneTime = gameOneTime;
        rtvc.gameTwoTime = gameTwoTime;
        rtvc.gameThreeTime = gameThreeTime;
        rtvc.gameFourTime = gameFourTime;
        rtvc.gameFiveTime = gameFiveTime;
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
