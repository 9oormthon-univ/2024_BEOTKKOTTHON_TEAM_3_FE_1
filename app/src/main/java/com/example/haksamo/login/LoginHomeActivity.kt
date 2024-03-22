package com.example.haksamo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haksamo.R
import com.example.haksamo.databinding.ActivityLoginHomeBinding
import com.example.haksamo.register.RegisterActivity

class LoginHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginHomeBinding.inflate(layoutInflater)

        binding.nextBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUpBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)


    }
}