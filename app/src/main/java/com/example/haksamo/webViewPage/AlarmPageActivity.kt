package com.example.haksamo.webViewPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.haksamo.R
import com.example.haksamo.databinding.ActivityAlarmPageBinding
import com.example.haksamo.databinding.ActivityMyPageBinding

class AlarmPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlarmPageBinding
    private lateinit var webView: WebView
    private lateinit var webSettings: WebSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmPageBinding.inflate(layoutInflater)
        initWebView()
        setContentView(binding.root)
    }

    // WebView setting
    private fun initWebView() {
        webView = binding.webView
        webSettings = webView.settings
        webSettings.setJavaScriptEnabled(true)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://haksamo.site/notice")
    }

    override fun onBackPressed() {

        // 웹뷰에서 뒤로가기 동작 수행
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // 웹뷰에서 더 이상 뒤로 갈 페이지가 없을 때, Fragment의 뒤로가기 동작 수행
            super.onBackPressed()
        }
    }
}