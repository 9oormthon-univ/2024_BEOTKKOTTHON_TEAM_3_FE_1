package com.example.haksamo.register

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.haksamo.R
import com.example.haksamo.databinding.ActivityRegister2Binding
import com.example.haksamo.login.LoginHomeActivity

class RegisterActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityRegister2Binding
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        // 전송 버튼 -> 이메일로 인증번호 전송
        //  gMailSender = GMailSender()
        //   gMailSender.sendEmali(email)
        binding.sendBtn.setOnClickListener {
            setTimer()
            binding.certificationEdit.visibility = View.VISIBLE
            binding.timer.visibility = View.VISIBLE
            binding.okBtn.visibility = View.VISIBLE
        }

        val msg = "인증이 완료되었습니다"
        binding.okBtn.setOnClickListener {
            binding.certificationEdit.setText(msg)
            binding.certificationEdit.setTextColor(Color.parseColor("#00700B"))
            binding.certificationEdit.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00700B"))
            timer.cancel()
            binding.timer.visibility = View.INVISIBLE
        }

//        binding.emailEdit
//        binding.passwordEdit
//        binding.passwordCheckEdit

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, LoginHomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setTimer() {
         timer = object : CountDownTimer(5 * 60 * 1000, 1000) { // 5분 타이머, 1초마다 갱신
            override fun onTick(millisUntilFinished: Long) {
                // 타이머가 갱신될 때마다 호출되는 콜백
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("%02d:%02d", minutes, seconds)

                binding.timer.setTextColor(Color.parseColor("#E40606"))
                binding.timer.text = timeString // EditText에 시간 표시
            }

            override fun onFinish() {
                // 타이머가 종료되었을 때 호출되는 콜백
                binding.timer.text ="00:00" // EditText에 시간 종료 메시지 표시
            }
        }

// 타이머 시작
        timer.start()
    }
}