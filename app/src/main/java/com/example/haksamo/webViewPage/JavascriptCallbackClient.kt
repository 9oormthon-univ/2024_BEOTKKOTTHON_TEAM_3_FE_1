package com.example.haksamo.webViewPage

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class JavascriptCallbackClient(private val context: Context, private val webView: WebView) {

    private fun publishEvent(functionName: String, data: String): String {
        return """
            window.dispatchEvent(
               new CustomEvent("$functionName", {
                   detail: {
                       data: $data
                   }
               })
           );
        """.trimIndent()
    }

    @JavascriptInterface
    fun setNotificationValue (value: Boolean) {
        val booleanValue = value
        Log.d("로그", "value: $booleanValue")
        setNotificationPermission(booleanValue)
    }

    @JavascriptInterface
    fun sendNotificationValue() {
        webView.post {
            val value = NotificationManagerCompat.from(context).areNotificationsEnabled().toString()
            val script = publishEvent("setNotificationValue", value)
            webView.evaluateJavascript(script) { result ->
                Toast.makeText(context, "알람: $result", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNotificationPermission(value: Boolean) {
        presentNotificationSetting(context)
        val value = NotificationManagerCompat.from(context).areNotificationsEnabled().toString()
        Log.d("로그", "수정 value: $value")
    }

    // 현재 버전에 따라 설정화면으로 이동
    fun presentNotificationSetting(context: Context) {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationSettingOreo(context)
        } else {
            notificationSettingOreoLess(context)
        }
        try {
            context.startActivity(intent)
        }catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    // Android Oreo 미만의 버전
    fun notificationSettingOreoLess(context: Context): Intent {
        return Intent().also { intent ->
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo?.uid)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    // Android Oreo (API 레벨 26) 이상의 버전
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationSettingOreo(context: Context): Intent {
        return Intent().also { intent ->
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

}


