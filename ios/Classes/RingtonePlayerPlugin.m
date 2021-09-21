#import "RingtonePlayerPlugin.h"
#if __has_include(<ringtone_player/ringtone_player-Swift.h>)
#import <ringtone_player/ringtone_player-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "ringtone_player-Swift.h"
#endif

@implementation RingtonePlayerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRingtonePlayerPlugin registerWithRegistrar:registrar];
}
@end
