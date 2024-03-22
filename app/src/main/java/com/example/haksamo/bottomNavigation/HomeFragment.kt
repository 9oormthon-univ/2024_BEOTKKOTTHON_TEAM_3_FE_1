package com.example.haksamo.bottomNavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.haksamo.databinding.FragmentHomeBinding
import com.example.haksamo.webViewPage.AlarmPageActivity
import com.example.haksamo.webViewPage.MyPageActivity


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var webView: WebView
    private lateinit var webSettings: WebSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setButton()
        initWebView()
        focusEditText()

        return binding.root
    }

    private fun setButton() {
        binding.alarm.setOnClickListener {
            val intent = Intent(requireContext(), AlarmPageActivity::class.java)
            startActivity(intent)
        }
        binding.mypage.setOnClickListener {
            val intent = Intent(requireContext(), MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    // WebView setting
    private fun initWebView() {
        webView = binding.webView
        webSettings = webView.settings
        webSettings.setJavaScriptEnabled(true)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://haksamo.site/")
    }

    // EditText focus 함수
    private fun focusEditText() {
        binding.editSearch.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // EditText 포커스 있을 때
                binding.alarm.visibility = View.INVISIBLE
                binding.mypage.visibility = View.INVISIBLE
                binding.universityName.visibility = View.INVISIBLE
                binding.webView.visibility = View.INVISIBLE
            } else {
                // EditText 포커스 없을 때
                binding.alarm.visibility = View.VISIBLE
                binding.mypage.visibility = View.VISIBLE
                binding.universityName.visibility = View.VISIBLE
                binding.webView.visibility = View.VISIBLE
            }
        }

        // 레이아웃의 다른 부분을 터치했을 때 포커스 해제
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {

                if (binding.editSearch.hasFocus()) {
                    binding.editSearch.clearFocus() // EditText의 포커스 해제
                }

                // 키보드 숨기기
                val manager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    requireActivity().window.decorView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                true
            }
            else {
                false
            }

        }
    }

    fun onBackPressed() {
        // 웹뷰에서 뒤로가기 동작 수행
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // 웹뷰에서 더 이상 뒤로 갈 페이지가 없을 때, Fragment의 뒤로가기 동작 수행
            requireActivity().finish()
        }
    }
}