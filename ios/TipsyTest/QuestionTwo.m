//
//  QuestionTwo.m
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import "QuestionTwo.h"
#import "QuestionThree.h"

@interface QuestionTwo ()
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

@implementation QuestionTwo
@synthesize chosenImage, inputNumber, instructions, reactionTime, gameTwoTime, gameOneTime, gameThreeTime, gameFourTime,imageView;
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
    
    NSMutableArray *imageArray1 = [[NSMutableArray alloc]initWithObjects:@"circle3.png",@"square3.png",@"triangle3.png", [NSString stringWithFormat:@"%i",3],nil];
    NSMutableArray *imageArray2 = [[NSMutableArray alloc]initWithObjects:@"circle4.png",@"square4.png",@"triangle4.png",[NSString stringWithFormat:@"%i",4], nil];
    NSMutableArray *imageArray3 = [[NSMutableArray alloc]initWithObjects:@"circle5.png",@"square5.png",@"triangle5.png",[NSString stringWithFormat:@"%i",5], nil];
    NSMutableArray *imageArrays = [[NSMutableArray alloc
                                    ]initWithObjects:imageArray1,imageArray2,imageArray3, nil];
    
    int randomArrayIndex = arc4random()%3;
    int randomImageIndex = arc4random()%3;
    answer = (int)[[[imageArrays objectAtIndex:randomArrayIndex]objectAtIndex:3]floatValue];
    instructions = [[UILabel alloc]init];
    instructions.textAlignment = NSTextAlignmentCenter;
    instructions.numberOfLines = 0;
    reactionTime = [[UILabel alloc]init];
    reactionTime.textAlignment = NSTextAlignmentCenter;
    reactionTime.numberOfLines = 0;
    chosenImage = [[UIImageView alloc]init];
    UIImage *image = [UIImage imageNamed: [[imageArrays objectAtIndex:randomArrayIndex]objectAtIndex:randomImageIndex]];
    
    [chosenImage setImage: image];
    
    inputNumber= [[UITextField alloc] init];
    inputNumber.textColor = [UIColor blackColor];
    inputNumber.backgroundColor = [UIColor orangeColor];
    inputNumber.placeholder = @"Answer here";
    //VERY IMPORTANT BELOW
    inputNumber.delegate = self;
    inputNumber.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    inputNumber.autocorrectionType = UITextAutocorrectionTypeNo;
    inputNumber.autocapitalizationType = UITextAutocapitalizationTypeNone;
    //if iphone 6 plus, display zoom
    if(height == 2208 || height == 2001 || height ==1920){
        
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 31];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"How many shapes are there?";
        chosenImage.frame = CGRectMake((screenW-(screenW*.35))/2, screenH *.25, screenW *.35, screenW*.375);
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 30];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:chosenImage];
        [imageView addSubview:inputNumber];
        
        
    }
    //if iphone 6, display zoom,//if iphone 5,5s, and newest ipod touch
    else if(diagonal == 4.7 || height == 1334 || height == 1136 || diagonal == 4){
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"How many shapes are there?";
        
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        
        
        
        if(diagonal == 4.7 || height == 1334){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 28];
            inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 27];
            chosenImage.frame = CGRectMake((screenW-(screenW*.325))/2, screenH *.25, screenW*.325, screenW*.35);
        }
        else if(diagonal == 4.0 || height == 1136){
            reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 25];
            inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 24];
            chosenImage.frame = CGRectMake((screenW-(screenW*.335))/2, screenH *.25, screenW*.335, screenW*.35);
        }
        
        
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:chosenImage];
        [imageView addSubview:inputNumber];
    }
    
    //if iphone 4,4s, 3 and below, ipod touches
    else if(diagonal == 3.5 || width == 640 || width == 320){
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"How many shapes are there?";
        chosenImage.frame = CGRectMake((screenW-(screenW*.315))/2, screenH *.235, screenW*.315, screenW*.315);
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.5, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:chosenImage];
        [imageView addSubview:inputNumber];
    }
    
    else{
        
        reactionTime.frame = CGRectMake(0,screenH*.7,screenW,screenH*.15);
        reactionTime.font = [UIFont fontWithName:@"Century Gothic" size : 24];
        instructions.frame = CGRectMake(0, screenH *.1, screenW, screenH*.1);
        instructions.font = [UIFont fontWithName:@"Century Gothic" size : 20];
        instructions.text = @"How many shapes are there?";
        chosenImage.frame = CGRectMake((screenW-(screenW*.315))/2, screenH *.235, screenW*.315, screenW*.315);
        
        inputNumber.frame = CGRectMake(screenW*.2, screenH *.55, screenW*.6, screenH*.05);
        inputNumber.font = [UIFont fontWithName:@"Century Gothic" size: 23];
        [imageView addSubview:reactionTime];
        [imageView addSubview:instructions];
        [imageView addSubview:chosenImage];
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

-(NSUInteger)supportedInterfaceOrientations{
    return(UIInterfaceOrientationMaskAll);
}
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    NSString *input = inputNumber.text;
    if([input isEqualToString:[NSString stringWithFormat:@"%i",answer]]){
        [forceExitTimer invalidate];
        forceExitTimer = nil;
        elapsedTime = CACurrentMediaTime() - start;
        gameFourTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        inputNumber.enabled = NO;
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
        gameFourTime = floorf(elapsedTime*1000.0+.5)/1000.0;
        inputNumber.enabled = NO;
        reactionTime.text = @"Times Up";
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
        [self performSegueWithIdentifier:@"questionThreeSegue" sender:(id)sender];
        
    }
}
//transferring data between view controllers
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    QuestionThree *qthreevc = [segue destinationViewController];
    
    if([segue.identifier isEqualToString:@"questionThreeSegue"]){
        
        qthreevc.gameOneTime = gameOneTime;
        qthreevc.gameTwoTime = gameTwoTime;
        qthreevc.gameThreeTime = gameThreeTime;
        qthreevc.gameFourTime = gameFourTime;
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
