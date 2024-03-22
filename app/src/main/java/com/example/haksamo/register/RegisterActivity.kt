package com.example.haksamo.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haksamo.R
import com.example.haksamo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
//            binding.nameEdit
//            binding.universityEdit
//            binding.passwordEdit
            val intent = Intent(this, RegisterActivity2::class.java)
            startActivity(intent)
        }
    }
}