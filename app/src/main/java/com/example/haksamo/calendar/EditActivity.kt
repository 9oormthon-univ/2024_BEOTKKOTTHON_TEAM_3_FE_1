package com.example.haksamo.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.haksamo.databinding.ActivityEditBinding
import com.example.haksamo.room.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
    }

    private fun setButton() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id",0)
        val selectedDate = intent.getStringExtra("date")
        val schedule = intent.getStringExtra("title")
        val location = intent.getStringExtra("location")

        binding.editSchedule.setText(schedule)
        binding.editLocation.setText(location)

        // 저장 버튼
        binding.underBtn.setOnClickListener {
            val updatedSchedule = binding.editSchedule.text.toString()
            val updatedLocation = binding.editLocation.text.toString()

            val db = Room.databaseBuilder(this, MyDatabase::class.java, "my-database").build()
            val scheduleDao = db.scheduleDao()

            CoroutineScope(Dispatchers.IO).launch {
                val originalSchedule = scheduleDao.getScheduleById(id)
                if (originalSchedule != null) {
                    originalSchedule.schedule = updatedSchedule
                    originalSchedule.location = updatedLocation
                    scheduleDao.updateSchedule(originalSchedule)
                }
            }
            Toast.makeText(this,"일정이 변경되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        //삭제 버튼
        binding.deleteBtn.setOnClickListener {
            val db = Room.databaseBuilder(this, MyDatabase::class.java, "my-database").build()
            val scheduleDao = db.scheduleDao()

            CoroutineScope(Dispatchers.IO).launch {
                val originalSchedule= scheduleDao.getScheduleById(id)
                scheduleDao.deleteSchedule(originalSchedule!!)
            }
            Toast.makeText(this,"일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}