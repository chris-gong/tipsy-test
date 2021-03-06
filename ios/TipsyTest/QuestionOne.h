//
//  QuestionOne.h
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QuestionOne : UIViewController <UITextFieldDelegate>
@property (nonatomic, assign) float gameOneTime;
@property (nonatomic, assign) float gameTwoTime;
@property (nonatomic, assign) float gameThreeTime;
@property (strong,nonatomic) IBOutlet UILabel *instructions;
@property (strong,nonatomic) IBOutlet UILabel *questionLetter;
@property (strong,nonatomic) IBOutlet UITextField *inputLetter;
@property (strong, nonatomic) IBOutlet UIImageView *imageView;

@property (strong,nonatomic) IBOutlet UILabel *reactionTime;
@end
