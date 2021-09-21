import Flutter
import UIKit
import AudioToolbox

public class SwiftRingtonePlayerPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "ringtone_player", binaryMessenger: registrar.messenger())
    let instance = SwiftRingtonePlayerPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if "play" == call.method {
        let args = call.arguments as? Dictionary<String, Any>
        let soundId = args?["ios"] as? NSNumber
        AudioServicesPlaySystemSound(SystemSoundID(soundId?.intValue ?? 0))
        result(nil)
    } else if "stop" == call.method {
        result(nil)
    } else {
        result(FlutterMethodNotImplemented)
    }
    
  }
}
