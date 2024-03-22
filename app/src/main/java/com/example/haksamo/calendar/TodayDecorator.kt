package com.example.haksamo.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import com.example.haksamo.R
import com.example.haksamo.room.ScheduleDao
import com.example.haksamo.room.SchoolDao
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Calendar

class TodayDecorator(context: Context): DayViewDecorator {
    private var date = CalendarDay.today()
    private val drawable = context.resources.getDrawable(R.drawable.calendar_circle_black)
    private val textColor = Color.WHITE

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
        view?.addSpan(ForegroundColorSpan(textColor))
    }
}

class EventDecorator(private val dateList: List<String>, private val scheduleDao: ScheduleDao, private val schoolDao: SchoolDao)
    : DayViewDecorator {

    private val datesWithEvent = mutableSetOf<CalendarDay>()

    init {
        // 초기화 시점에 데이터베이스 쿼리를 수행하여 결과를 저장합니다.
        runBlocking {
            withContext(Dispatchers.IO) {
                dateList.forEach { date ->
                    val schedules = scheduleDao.getScheduleByDate(date)
                    val schoolSchedules = schoolDao.getSchoolScheduleByDate(date)
                    if (schedules.isNotEmpty() || schoolSchedules.isNotEmpty()) {
                        val year = date.substring(0, 4).toInt()
                        val month = date.substring(5, 7).toInt()
                        val day = date.substring(8, 10).toInt()
                        Log.d("로그","${year}, ${month}, ${day}")
                        datesWithEvent.add(CalendarDay.from(year, month, day))
                    }
                }
            }
        }
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        // 저장된 결과를 사용하여 데코레이션을 적용할 날짜를 결정합니다.
        return datesWithEvent.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10F, Color.parseColor("#d3d3d3")))
    }
}

// 이전 달, 다음 달 일부 보여줌
class SelectedMonthDecorator(val selectedMonth : Int) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.month != selectedMonth
    }
    override fun decorate(view: DayViewFacade) {
        view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#d3d3d3")){})
    }
}

class SaturdayDecorator:DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val localDate = day!!.date
        val calendar = Calendar.getInstance()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY

    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object:ForegroundColorSpan(Color.BLUE){})
    }
}

class SundayDecorator:DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val localDate = day!!.date
        val calendar = Calendar.getInstance()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SUNDAY

    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: ForegroundColorSpan(Color.RED){})
    }
}

class BoldDecorator(val selectedMonth : Int) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        //return day.month != selectedMonth || day.month == selectedMonth
        return day.month == selectedMonth
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
        //view?.addSpan(object: RelativeSizeSpan(1.4f){})
    }
}