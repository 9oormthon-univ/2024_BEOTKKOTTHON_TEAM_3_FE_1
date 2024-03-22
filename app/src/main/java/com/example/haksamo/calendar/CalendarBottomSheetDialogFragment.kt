package com.example.haksamo.calendar

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.haksamo.R
import com.example.haksamo.databinding.FragmentScheduleBinding
import com.example.haksamo.room.MyDatabase
import com.example.haksamo.room.ScheduleDao
import com.example.haksamo.room.ScheduleEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarBottomSheetDialogFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentScheduleBinding
    private lateinit var scheduleDao: ScheduleDao
    private lateinit var myDatabase: MyDatabase

    // 선택한 날짜 정보를 저장할 리스트
    var selectedDateList: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(layoutInflater)

        setBottomSheet()
        setButton()

        return binding.root
    }

    fun setButton() {

        // 취소 버튼
        binding.backBtn.setOnClickListener{
            Log.d("로그", "backBtn")
            dismiss()
        }

        // 입력 버튼
        binding.underBtn.setOnClickListener {
            val selectedYear = arguments?.getInt("selectedYear", 0) ?: 0
            val selectedMonth = arguments?.getInt("selectedMonth", 0) ?: 0
            val selectedDay = arguments?.getInt("selectedDay", 0) ?: 0
            val selectedDate = "$selectedYear-${String.format("%02d", selectedMonth)}-${String.format("%02d", selectedDay)}"

            val schedule = binding.editSchedule.text.toString()
            val location = binding.editLocation.text.toString()

            if(schedule.isEmpty() || location.isEmpty()) {
                Toast.makeText(requireContext(), "일정과 장소를 추가하세요", Toast.LENGTH_SHORT).show()
            } else {

                myDatabase =
                    Room.databaseBuilder(requireContext(), MyDatabase::class.java, "my-database")
                        .build()
                scheduleDao = myDatabase.scheduleDao()

                CoroutineScope(Dispatchers.IO).launch {
                    val scheduleEntity = ScheduleEntity(selectedDate, schedule, location)
                    scheduleDao.insertSchedule(scheduleEntity)
                }

                Toast.makeText(requireContext(), "일정이 추가되었습니다", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    fun setBottomSheet() {
        val coordinatorLayout = binding.root
        val bottomSheetLayout = coordinatorLayout.findViewById<ConstraintLayout>(R.id.bottom_sheet)
        val bottomBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        val windowHeight = Resources.getSystem().displayMetrics.heightPixels
        bottomBehavior.peekHeight = windowHeight / 4
        Log.d("로그", "${bottomBehavior.peekHeight}")

        bottomBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    Log.d("로그", "addBottomSheetCallback")
                    when (newState) {

                        // 사용자가 BottomSheet를 위나 아래로 드래그 중인 상태
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            Log.d("로그", "드래그")
                        }

                        // 드래그 동작 후 BottomSheet가 특정 높이로 고정될 때의 상태
                        BottomSheetBehavior.STATE_SETTLING -> { }

                        //  완전히 펼쳐진 상태
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            Log.d("로그", "onStateChanged: 완전펼침")
                        }

                        // 절반으로 펼쳐진 상태
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            Log.d("로그", "onStateChanged: 펼침")
                        }

                        // peek 높이 만큼 보이는 상태
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            Log.d("로그", "onStateChanged: 접음")
                        }

                        // 숨김 상태
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            Log.d("로그", "onStateChanged: 숨김")
                        }
                    }


                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // 슬라이드 될때 offset / hide -1.0 ~ collapsed 0.0 ~ expended 1.0
                    Log.d("로그", "onStateChanged: onSlide 중")
                }
        })
    }

}