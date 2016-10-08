//
//  FinalPage.h
//  TipsyTest
//
//  Created by Xu Wu on 8/19/15.
//  Copyright (c) 2015 Chris Gong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>

@interface FinalPage : UIViewController <UITextFieldDelegate,ADBannerViewDelegate>
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (nonatomic, assign) float gameOneTime;
@property (nonatomic, assign) float gameTwoTime;
@property (nonatomic, assign) float gameThreeTime;
@property (nonatomic, assign) float gameFourTime;
@property (nonatomic, assign) float gameFiveTime;
@property (strong, nonatomic) IBOutlet UIButton *enter;
@property (strong, nonatomic) IBOutlet UIButton *callUber;
@property (strong, nonatomic) IBOutlet UIButton *callTaxi;
@property (strong, nonatomic) IBOutlet UILabel *recommendation;
@property (strong, nonatomic) IBOutlet UIButton *replay;
@property (strong, nonatomic) IBOutlet UIButton *up;
@property (strong, nonatomic) IBOutlet UIButton *down;
@property (strong, nonatomic) IBOutlet UITextField *userAnswer;
@property (strong, nonatomic) IBOutlet ADBannerView *adBanner;
@property (strong, nonatomic) IBOutlet UILabel *disclaimer;
@end
