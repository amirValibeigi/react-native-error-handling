
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNErrorHandlingSpec.h"

@interface ErrorHandling : NSObject <NativeErrorHandlingSpec>
#else
#import <React/RCTBridgeModule.h>

@interface ErrorHandling : NSObject <RCTBridgeModule>
#endif

@end
