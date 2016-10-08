//
//  QuestionOne.m
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "QuestionOne.h"
#import "QuestionTwo.h"

@interface QuestionOne (){
    
char alphabet[10];
char randomLetter;
    char answerLetterUppercase;
    char answerLetterLowercase;
    float screenW;
    float screenH;
    CFTimeInterval start;
    CFTimeInterval elapsedTime;
    int timeTillExit;
    int timeTillForceExit;
    NSTimer *exitTimer;
    NSTimer *forceExitTimer;
}
@end

@implementation QuestionOne
@synthesize questionLetter, inputLetter, instructions, reactionTime, gameTwoTime, gameOneTime, gameThreeTime,imageView;
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
    
    instructions = [[UILabel alloc]init];
    instructions.textAlignment = NSTextAlignmentCenter;
    instructions.numberOfLines = 0;
    reactionTime = [[UILabel alloc]init];
    reactionTime.textAlignment = NSTextAlignmentCenter;
    reactionTime.numberOfLines = 0;
    questionLetter = [[UILabel alloc]init];
    questionLetter.textAlignment = NSTextAlignmentCenter;
    questionLetter.numberOfLines = 0;
    
    inputLetter = [[UITextField alloc] init];
    inputLetter.textColor = [UIColor blackColor];
    inputLetter.backgroundColor = [UIColor orangeColor];
    inputLetter.placeholder = @"Answer here";
    //VERY IMPORTANT BELOW
    inputLetter.delegate = self;
    inputLetter.autocorrectionType = UITextAutocorrectionTypeNo;
    inputLetter.autocapitalizationType = UITextAutocapitalizationTypeNone;
    alphabet[0] = 'G';
    alphabet[1] = 'P';
    alphabet[2] = 'H';
    alphabet[3] = 'Q';
    alphabet[4] = 'R';
    alphabet[5] = 'J';
    alphabet[6] = 'K';
    alphabet[7] = 'L';
    alphabet[8] = 'F';
    alphabet[9] = 'O';
    int randomIndex = arc4random()%10;
    randomLetter = alphabet[randomIndex];
    answerLetterUppercase = randomLetter - 1;
    
    NSString *answerLetterString = [NSString stringWithFormat:@"%c",answerLetterUppercase];
    answerLetterLowercase = [[answerLetterString lowercaseString] characterAtIndex:0];
    start = CACurrentMediaTime();
    
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 31];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"What letter comes before";
        questionLetter.frame = CGRectMake(0, screenH *.25, screenW, screenH*.2);
        questionLetter.font = [UIFont fontWithName:@"Century Gothic" size : 130];
        questionLetter.text = [NSString stringWithFormat:@"%c",randomLetter];
        inputLetter.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        inputLetter.font = [UIFont fontWithName:@"Century Gothic" size: 30];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:questionLetter];
        [imageView addSubview:inputLetter];
        
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"What letter comes before";
        questionLetter.frame = CGRectMake(0, screenH *.25, screenW, screenH*.2);
        questionLetter.font = [UIFont fontWithName:@"Century Gothic" size : 130];
        questionLetter.text = [NSString stringWithFormat:@"%c",randomLetter];
        inputLetter.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        
        
        
        if(diagonal == 4.7 || height == 1334){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 28];
            inputLetter.font = [UIFont fontWithName:@"Century Gothic" size: 27];
        }
        else if(diagonal == 4.0 || height == 1136){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 25];
            inputLetter.font = [UIFont fontWithName:@"Century Gothic" size: 24];
        }
        
        
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:questionLetter];
        [imageView addSubview:inputLetter];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"What letter comes before";
        questionLetter.frame = CGRectMake(0, screenH *.235, screenW, screenH*.2);
        questionLetter.font = [UIFont fontWithName:@"Century Gothic" size : 100];
        questionLetter.text = [NSString stringWithFormat:@"%c",randomLetter];
        inputLetter.frame = CGRectMake(screenW*.2, screenH *.5, screenW*.6, screenH*.05);
        inputLetter.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:questionLetter];
        [imageView addSubview:inputLetter];
    }
    
    else{
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"What letter comes before";
        questionLetter.frame = CGRectMake(0, screenH *.225, screenW, screenH*.15);
        questionLetter.font = [UIFont fontWithName:@"Century Gothic" size : 100];
        questionLetter.text = [NSString stringWithFormat:@"%c",randomLetter];
        inputLetter.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        inputLetter.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:questionLetter];
        [imageView addSubview:inputLetter];
    }
    
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

-(NSUInteger)supportedInterfaceOrientations{
    return(UIInterfaceOrientationMaskAll);
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    NSString *input = inputLetter.text;
    if([input isEqualToString:[NSString stringWithFormat:@"%c",answerLetterUppercase]] ||
       [input isEqualToString:[NSString stringWithFormat:@"%c",answerLetterLowercase]]){
        [forceExitTimer invalidate];
        forceExitTimer = nil;
        elapsedTime = CACurrentMediaTime() - start;
        gameThreeTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        inputLetter.enabled = NO;
        reactionTime.text = @"Correct";
        timeTillExit = 3;
        exitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod2:) userInfo: nil repeats: YES];
    }
    else{
        reactionTime.text = @"Incorrect, try again";
    }
    return YES;
}
-(void)timerMethod:(id)sender{
    timeTillForceExit--;
    if(timeTillForceExit==0){
        [forceExitTimer invalidate];
        forceExitTimer = nil;
        elapsedTime = CACurrentMediaTime() - start;
        gameThreeTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        inputLetter.enabled = NO;
        reactionTime.text = @"Times Up";
        [inputLetter endEditing:YES];
        timeTillExit = 3;
        exitTimer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(timerMethod2:) userInfo: nil repeats: YES];
        
    }
}
-(void)timerMethod2:(id)sender{
    timeTillExit--;
    
    if(timeTillExit==0){
        
        [exitTimer invalidate];
        exitTimer = nil;
        [self performSegueWithIdentifier:@"questionTwoSegue" sender:(id)sender];
        
}
}
//transferring data between view controllers
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    QuestionTwo *qtwovc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"questionTwoSegue"]){
        
        qtwovc.gameOneTime = gameOneTime;
        qtwovc.gameTwoTime = gameTwoTime;
        qtwovc.gameThreeTime = gameThreeTime;
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
