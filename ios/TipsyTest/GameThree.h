//
//  GameThree.h
//  TipsyTest
//
//  Created by Xu Wu on 8/17/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GameThree : UIViewController
@property (nonatomic, assign) float gameOneTime;
@property (nonatomic, assign) float gameTwoTime;
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (strong,nonatomic) IBOutlet UIButton *startButton;

@end
