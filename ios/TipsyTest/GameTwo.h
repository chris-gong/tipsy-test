//
//  GameTwo.h
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GameTwo : UIViewController
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (nonatomic, assign) float gameOneTime;
@property (nonatomic, assign) float gameTwoTime;
@property (strong,nonatomic) IBOutlet UIButton *startButton;
@property (strong,nonatomic) IBOutlet UIButton *circleButton;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime;
@end
