package com.mgerbie.ringtone_player

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.lang.Exception

/** RingtonePlayerPlugin */
class RingtonePlayerPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var context: Context
  private lateinit var channel : MethodChannel
  private lateinit var ringtoneManager: RingtoneManager
  private var ringtone: Ringtone? = null


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "ringtone_player")
    channel.setMethodCallHandler(this)
    context=flutterPluginBinding.applicationContext;
    ringtoneManager = RingtoneManager(flutterPluginBinding.applicationContext)
    ringtoneManager.stopPreviousRingtone = true
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    try {
      var ringtoneUri: Uri? = null
      if (call.method == "play" && !call.hasArgument("android")) {
        result.notImplemented()
      } else if (call.method == "play") {
        val kind = call.argument<Int>("android")!!
        when (kind) {
          1 -> ringtoneUri = Settings.System.DEFAULT_ALARM_ALERT_URI
          2 -> ringtoneUri = Settings.System.DEFAULT_NOTIFICATION_URI
          3 -> ringtoneUri = Settings.System.DEFAULT_RINGTONE_URI
          else -> result.notImplemented()
        }
      } else if (call.method == "stop") {
        if (ringtone != null) {
          ringtone!!.stop()
        }
        result.success(null)
      }
      if (ringtoneUri != null) {
        if (ringtone != null) {
          ringtone!!.stop()
        }
        ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
        if (call.hasArgument("volume")) {
          val volume = call.argument<Double>("volume")!!
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone!!.volume = volume.toFloat()
          }
        }
        if (call.hasArgument("looping")) {
          val looping = call.argument<Boolean>("looping")!!
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            ringtone!!.isLooping = looping
          }
        }
        if (call.hasArgument("asAlarm")) {
          val asAlarm = call.argument<Boolean>("asAlarm")!!
          /* There's also a .setAudioAttributes method
                       that is more flexible, but .setStreamType
                       is supported in all Android versions
                       whereas .setAudioAttributes needs SDK > 21.
                       More on that at
                       https://developer.android.com/reference/android/media/Ringtone
                    */if (asAlarm) {

            ringtone!!.audioAttributes = AudioAttributes.Builder()
                    .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                    .setLegacyStreamType(AudioManager.STREAM_ALARM)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
          }
        }
        ringtone!!.play()
        result.success(null)
      }else {
        result.notImplemented()
      }
    } catch (e: Exception) {
      e.printStackTrace()
      result.error("Exception", e.message, null)
    }
    }


  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)

  }
}
