package com.example.haksamo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.haksamo.MainActivity
import com.example.haksamo.R
import com.example.haksamo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener{
            //binding.emailEdit
            //binding.passwordEdit

            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "로그인 성공.",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


    }
}