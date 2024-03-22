package com.example.haksamo.bottomNavigation


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.haksamo.calendar.BoldDecorator
import com.example.haksamo.calendar.EventDecorator
import com.example.haksamo.R
import com.example.haksamo.calendar.CalendarBottomSheetDialogFragment
import com.example.haksamo.calendar.SaturdayDecorator
import com.example.haksamo.calendar.SelectedMonthDecorator
import com.example.haksamo.calendar.SundayDecorator
import com.example.haksamo.calendar.TodayDecorator
import com.example.haksamo.calendar.TodoFragment
import com.example.haksamo.databinding.FragmentCalendarBinding
import com.example.haksamo.room.MyDatabase
import com.example.haksamo.room.SchoolDatabase
import com.example.haksamo.webViewPage.AlarmPageActivity
import com.example.haksamo.webViewPage.MyPageActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var materialCalendarView: MaterialCalendarView
    private lateinit var add_schedule_btn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)

        setButton()
        setCalendarView()
        setClickDate()
        setFabBtn()

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

    private fun setClickDate() {

        materialCalendarView.setOnDateChangedListener { widget, date, selected ->
            val selectedYear = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.year ?: 0
            } else {
                CalendarDay.today().year
            }

            val selectedMonth = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.month ?: 0
            } else {
                CalendarDay.today().month
            }

            val selectedDay = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.day ?: 0
            } else {
                CalendarDay.today().day
            }

            val todoFragment = TodoFragment(selectedYear, selectedMonth, selectedDay)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, todoFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    private fun setFabBtn() {
        add_schedule_btn = binding.addSchedule
        add_schedule_btn.setOnClickListener{
            val selectedYear = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.year ?: 0
            } else {
                CalendarDay.today().year
            }

            val selectedMonth = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.month ?: 0
            } else {
                CalendarDay.today().month
            }

            val selectedDay = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.day ?: 0
            } else {
                CalendarDay.today().day
            }


            val bundle = Bundle().apply {
                putInt("selectedYear", selectedYear)
                putInt("selectedMonth", selectedMonth)
                putInt("selectedDay", selectedDay)
            }

            val scheduleFragment = CalendarBottomSheetDialogFragment().apply {
                arguments = bundle
            }

            scheduleFragment.selectedDateList = mutableListOf(selectedYear, selectedMonth, selectedDay)
            scheduleFragment.show(childFragmentManager,null)
        }
    }

    fun generateDateList(year: Int, month: Int): List<String> {
        val dateList = mutableListOf<String>()

        val firstDayOfMonth = Calendar.getInstance()
        firstDayOfMonth.set(year, month - 1, 1)

        val lastDayOfMonth = Calendar.getInstance()
        lastDayOfMonth.set(year, month - 1, firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))

        val dateFormat = "%d-%02d-%02d"

        for (day in firstDayOfMonth.get(Calendar.DAY_OF_MONTH)..lastDayOfMonth.get(Calendar.DAY_OF_MONTH)) {
            dateList.add(String.format(dateFormat, year, month, day))
        }

        return dateList
    }

    private  fun setCalendarView() {
        materialCalendarView = binding.calendarview
        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)

        val dateList = generateDateList(CalendarDay.today().year, CalendarDay.today().month)

        val myDatabase = Room.databaseBuilder(requireContext(), MyDatabase::class.java, "my-database").build()
        val scheduleDao = myDatabase.scheduleDao()
        val schoolDatabase = Room.databaseBuilder(requireContext(), SchoolDatabase::class.java, "school-database").build()
        val schoolDao = schoolDatabase.SchoolDao()

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        var boldDecorator = BoldDecorator(CalendarDay.today().month)
        val todayDecorator = TodayDecorator(requireContext())
        var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)
        val eventDecorator = EventDecorator(dateList, scheduleDao, schoolDao)

        materialCalendarView.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            boldDecorator,
            todayDecorator,
            selectedMonthDecorator,
            eventDecorator
        )
        materialCalendarView.setOnMonthChangedListener { _, date ->

            materialCalendarView.removeDecorators()
            materialCalendarView.invalidateDecorators()

            val newYear = date.year
            val newMonth = date.month
            val newDateList = generateDateList(newYear, newMonth)
            val newEventDecorator = EventDecorator(newDateList, scheduleDao, schoolDao)
            Log.d("로그", "${newDateList}")
            // Decorators 추가
            selectedMonthDecorator = SelectedMonthDecorator(date.month)
            boldDecorator = BoldDecorator(date.month)
            materialCalendarView.addDecorators(
                sundayDecorator,
                saturdayDecorator,
                boldDecorator,
                todayDecorator,
                selectedMonthDecorator,
                newEventDecorator
            )
        }
    }

}