package com.example.haksamo.calendar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.haksamo.databinding.FragmentTodoBinding
import com.example.haksamo.recyclerview.BasicAdapter
import com.example.haksamo.recyclerview.BasicItem
import com.example.haksamo.recyclerview.MyAdapter
import com.example.haksamo.recyclerview.MyItem
import com.example.haksamo.room.MyDatabase
import com.example.haksamo.room.ScheduleDao
import com.example.haksamo.room.SchoolDatabase
import com.example.haksamo.room.SchoolEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TodoFragment(private val selectedYear: Int, private val selectedMonth: Int, private val selectedDay: Int) : Fragment() {
    private lateinit var biding: FragmentTodoBinding
    private lateinit var scheduleDao: ScheduleDao
    private lateinit var myDatabase: MyDatabase
    private lateinit var selectedDate: String
    private lateinit var schedule: String
    private lateinit var location: String
    private var itemList: MutableList<BasicItem>? = null
    private var itemList2: MutableList<MyItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        biding = FragmentTodoBinding.inflate(layoutInflater)

        setDataBase()
        //setSchoolSchedule()
        setRecyclerView()

//        val schoolDatabase = Room.databaseBuilder(requireContext(), SchoolDatabase::class.java, "school-database").build()
//        val schoolDao = schoolDatabase.SchoolDao()
//        CoroutineScope(Dispatchers.IO).launch {
//            schoolDao.deleteAllSchoolSchedules()
//        }
        return biding.root
    }

    fun setSchoolSchedule() {
        val schoolDatabase =
            Room.databaseBuilder(requireContext(), SchoolDatabase::class.java, "school-database")
                .build()
        val schoolDao = schoolDatabase.SchoolDao()

        val dateList = listOf(
            "2024-01-29",
            "2024-04-02",
            "2024-04-22",
            "2024-04-22",
            "2024-04-24",
            "2024-04-25",
            "2024-05-02",
            "2024-05-08",
            "2024-05-20",
            "2024-05-22",
            "2024-06-01",
            "2024-06-03",
            "2024-06-10",
            "2024-06-17",
            "2024-06-17",
            "2024-06-24",
            "2024-08-05",
            "2024-08-07",
            "2024-08-13",
            "2024-08-14",
            "2024-08-16",
            "2024-08-20",
            "2024-08-21",
            "2024-09-02",
            "2024-09-26",
            "2024-10-01",
            "2024-10-21",
            "2024-10-21",
            "2024-10-23",
            "2024-10-24",
            "2024-10-31",
            "2024-11-04",
            "2024-11-20",
        )
        val schoolEntities = listOf(
            SchoolEntity(dateList[0], "수업일수 4분의 1", "광운대학교"),
            SchoolEntity(dateList[1], "1학기 수업일수 30일", "광운대학교"),
            SchoolEntity(dateList[2], "1학기 중간고사 - 8주차", "광운대학교"),
            SchoolEntity(dateList[3], "1학기 강의 중간 평가", "광운대학교"),
            SchoolEntity(dateList[4], "수업일수 2분의 1", "광운대학교"),
            SchoolEntity(dateList[5], "1학기 [심화·복수·부·연계]전공 신청", "광운대학교"),
            SchoolEntity(dateList[6], "1학기 수업일수 60일", "광운대학교"),
            SchoolEntity(dateList[7], "졸업종합시험", "광운대학교"),
            SchoolEntity(dateList[8], "개교기념일", "광운대학교"),
            SchoolEntity(dateList[9], "월계 축전", "광운대학교"),
            SchoolEntity(dateList[10], "1학기 수업일수 90일", "광운대학교"),
            SchoolEntity(dateList[11], "1학기 강의평가/ 장학금 신청기간", "광운대학교"),
            SchoolEntity(dateList[12], "1학기 강의평가/ 장학금 신청기간", "광운대학교"),
            SchoolEntity(dateList[13], "1학기 기말고사·보강 주간 (15~16주)", "광운대학교"),
            SchoolEntity(dateList[14], "1학기 종강 교수회의 (16주차 시작요일)", "광운대학교"),
            SchoolEntity(dateList[15], "하계 계절수업", "광운대학교"),
            SchoolEntity(dateList[16], "2학기 복학신청", "광운대학교"),
            SchoolEntity(dateList[17], "2학기 [심화·복수·부·연계]전공 신청", "광운대학교"),
            SchoolEntity(dateList[18], "2학기 수강신청", "광운대학교"),
            SchoolEntity(dateList[19], "2학기 휴학신청", "광운대학교"),
            SchoolEntity(dateList[20], "2학기 등록", "광운대학교"),
            SchoolEntity(dateList[21], "2학기 개강교수회의", "광운대학교"),
            SchoolEntity(dateList[22], "2023학년도 후기 학위 수여식(대학원별 진행)", "광운대학교"),
            SchoolEntity(dateList[23], "2024학년도 2학기 개강(학기개시일)", "광운대학교"),
            SchoolEntity(dateList[24], "수업일수 4분의 1", "광운대학교"),
            SchoolEntity(dateList[25], "2학기 수업일수 30일", "광운대학교"),
            SchoolEntity(dateList[26], "2학기 중간고사 - 8주차", "광운대학교"),
            SchoolEntity(dateList[27], "2학기 강의 중간평가", "광운대학교"),
            SchoolEntity(dateList[28], "수업일수 2분의 1", "광운대학교"),
            SchoolEntity(dateList[29], "2학기 [심화·복수·부·연계]전공 신청", "광운대학교"),
            SchoolEntity(dateList[30], "2학기 수업일수 60일", "광운대학교"),
            SchoolEntity(dateList[31], "졸업종합시험", "광운대학교"),
            SchoolEntity(dateList[32], "동계 계절수업 수강신청", "광운대학교"),
        )

//        CoroutineScope(Dispatchers.IO).launch {
//            schoolEntities.forEach { schoolEntity ->
//                schoolDao.insertSchoolSchedule(schoolEntity)
//            }
//        }

        itemList = mutableListOf()

        CoroutineScope(Dispatchers.IO).launch {
            val schoolSchedule = schoolDao.getSchoolScheduleByDate(dateList[0])
            val firstScheduleEntity = schoolSchedule.firstOrNull()
            val schedule = firstScheduleEntity!!.schedule
            val basicItem = BasicItem(schedule)
            itemList!!.add(basicItem)

            withContext(Dispatchers.Main) {
                updateRecyclerView2()
            }
        }

        //        CoroutineScope(Dispatchers.IO).launch {
//            dateList.forEach { date ->
//                val schoolSchedule = schoolDao.getSchoolScheduleByDate(date)
//                val firstScheduleEntity = schoolSchedule.firstOrNull()
//                Log.d("로그", "${firstScheduleEntity}")
//
//                if (firstScheduleEntity != null) {
//                    val schedule = firstScheduleEntity.schedule
//                    val basicItem = BasicItem(schedule)
//
//                    if (firstScheduleEntity.date == date) {
//                        itemList!!.add(basicItem)
//                    }
//                }
//                withContext(Dispatchers.Main) {
//                    updateRecyclerView2()
//                }
//            }
//        }
    }

    private fun setDataBase() {

        itemList2 = mutableListOf()
        selectedDate = "$selectedYear-${String.format("%02d", selectedMonth)}-${String.format("%02d", selectedDay)}"
        myDatabase = Room.databaseBuilder(requireContext(), MyDatabase::class.java, "my-database").build()
        scheduleDao = myDatabase.scheduleDao()

        CoroutineScope(Dispatchers.IO).launch {
            val schedules = scheduleDao.getScheduleByDate(selectedDate)

            schedules.forEach { scheduleEntity ->
                schedule = scheduleEntity.schedule
                location = scheduleEntity.location
                //val id = scheduleEntity.id
                //val basicItem = BasicItem(scheduleEntity.schedule)
                val myItem = MyItem(scheduleEntity.schedule)
                itemList2!!.add(myItem)

                withContext(Dispatchers.Main) {
                    val schedules = withContext(Dispatchers.IO) { scheduleDao.getScheduleByDate(selectedDate) }

                    val myAdapter = itemList2?.let { MyAdapter(it) }
                    biding.myRecyclerview.adapter = myAdapter
                    myAdapter?.itemClickListener = object : MyAdapter.OnItemClickListener{

                        override fun onItemClick(position: Int) {
                            val item = itemList2?.get(position)
                            val intent = Intent(requireContext(), EditActivity::class.java).apply {
                                val clickedScheduleEntity = schedules[position]
                                putExtra("id", clickedScheduleEntity.id) // 해당 아이템의 id를 넘겨줌
                                putExtra("date", selectedDate)
                                putExtra("title", item?.title)
                                putExtra("location", location)
                            }
                            startActivity(intent)
                        }
                    }
                    myAdapter!!.notifyDataSetChanged()
                }
            }
        }

    }

    private fun updateRecyclerView2() {
        val basicAdapter = itemList?.let { BasicAdapter(it) }
        biding.schoolRecyclerview.adapter = basicAdapter
        basicAdapter!!.notifyDataSetChanged()
    }


    private fun setRecyclerView() {
        biding.schoolRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        biding.myRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}