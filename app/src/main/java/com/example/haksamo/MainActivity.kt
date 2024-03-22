package com.example.haksamo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.haksamo.bottomNavigation.CalendarFragment
import com.example.haksamo.bottomNavigation.DietFragment
import com.example.haksamo.bottomNavigation.HomeFragment
import com.example.haksamo.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    private var homeFragment: HomeFragment? = null
    private var calendarFragment: CalendarFragment? = null
    private var dietFragment: DietFragment? = null
    private val TAG = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        FirebaseApp.initializeApp(this)
        getFCMToken()

        setBottomNavigationView()

        fragmentManager = supportFragmentManager

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_home
        }

        this.onBackPressedDispatcher.addCallback(this, callback) // 뒤로가기
    }

    // 알림 권한 허용
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    // FCM 토큰 생성
    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d(TAG, "FCM Token is ${token}")
        })

        return token
    }



    // 선택한 Fragment 제외한 다른 Fragment 숨기는 함수
    private fun hideOtherFragments(selectedFragment: Fragment?) {
        if (selectedFragment != homeFragment) {
            homeFragment?.let { fragmentManager.beginTransaction().hide(it).commit() }
        }
        if (selectedFragment != calendarFragment) {
            calendarFragment?.let { fragmentManager.beginTransaction().hide(it).commit() }
        }
        if (selectedFragment != dietFragment) {
            dietFragment?.let { fragmentManager.beginTransaction().hide(it).commit() }
        }
    }

    // BottomNavigationView 생성 함수
    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    if (homeFragment == null) { // Fragment null 이면 새로운 Fragment 생성
                        homeFragment = HomeFragment()
                        fragmentManager.beginTransaction()
                            .add(R.id.main_container, homeFragment!!, "home").commit()
                    }
                    else {   // 이미 생성된 경우 해당 Fragment 보여줌
                        fragmentManager.beginTransaction().show(homeFragment!!).commit()
                    }
                    hideOtherFragments(homeFragment) // 현재 Fragment 제외한 다른 Fragment 숨김
                    true
                }

                R.id.fragment_calendar -> {
                    if (calendarFragment == null) {
                        calendarFragment = CalendarFragment()
                        fragmentManager.beginTransaction()
                            .add(R.id.main_container, calendarFragment!!, "calendar").commit()
                    } else {
                        fragmentManager.beginTransaction().show(calendarFragment!!).commit()
                    }
                    hideOtherFragments(calendarFragment)
                    true
                }

                R.id.fragment_diet -> {
                    if (dietFragment == null) {
                        dietFragment = DietFragment()
                        fragmentManager.beginTransaction()
                            .add(R.id.main_container, dietFragment!!, "diet").commit()
                    } else {
                        fragmentManager.beginTransaction().show(dietFragment!!).commit()
                    }
                    hideOtherFragments(dietFragment)
                    true
                }

                else -> false
            }
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)

            if (currentFragment is HomeFragment) {
                currentFragment.onBackPressed()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}