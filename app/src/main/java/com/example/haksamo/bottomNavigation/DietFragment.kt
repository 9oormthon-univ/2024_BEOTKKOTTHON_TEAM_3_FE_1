package com.example.haksamo.bottomNavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haksamo.R
import com.example.haksamo.databinding.FragmentDietBinding
import com.example.haksamo.webViewPage.AlarmPageActivity
import com.example.haksamo.webViewPage.MyPageActivity


class DietFragment : Fragment() {
    private lateinit var binding: FragmentDietBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDietBinding.inflate(layoutInflater)

        setButton()

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

}