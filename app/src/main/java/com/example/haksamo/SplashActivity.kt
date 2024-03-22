package com.example.haksamo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.haksamo.databinding.ActivityMainBinding
import com.example.haksamo.databinding.ActivitySplashBinding
import com.example.haksamo.login.LoginHomeActivity

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        // 2초 뒤에 메인 액티비티로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginHomeActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}