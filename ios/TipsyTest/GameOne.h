//
//  GameOne.h
//  TipsyTest
//
//  Created by Xu Wu on 8/16/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GameOne : UIViewController
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (nonatomic, assign) float gameOneTime;
@property (strong,nonatomic) IBOutlet UIButton *startButton;
@property (strong,nonatomic) IBOutlet UIButton *circleButton;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime;
//below could have been declared in .m file
@property (nonatomic, assign) BOOL isCircleVisible;
@property (nonatomic, assign) BOOL isCircleOrange;
@property (nonatomic, assign) BOOL isCircleOrangeClicked;

@property (nonatomic, assign) NSTimer *timer;
@end
