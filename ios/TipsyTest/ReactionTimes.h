//
//  ReactionTimes.h
//  TipsyTest
//
//  Created by Xu Wu on 8/19/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>

@interface ReactionTimes : UIViewController <ADBannerViewDelegate>
@property (nonatomic, assign) float gameOneTime;
@property (nonatomic, assign) float gameTwoTime;
@property (nonatomic, assign) float gameThreeTime;
@property (nonatomic, assign) float gameFourTime;
@property (nonatomic, assign) float gameFiveTime;
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime1;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime2;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime3;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime4;
@property (strong,nonatomic) IBOutlet UILabel *reactionTime5;
@property (strong,nonatomic) IBOutlet UILabel *totalTime;
@property (strong,nonatomic) IBOutlet UIButton *nextPage;
@property (strong, nonatomic) IBOutlet ADBannerView *adBanner;
@end
