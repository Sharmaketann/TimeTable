package com.maverick.timetable

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.maverick.timetable.databinding.ActivityTimetableBinding

class Timetable : AppCompatActivity() {

    private lateinit var binding: ActivityTimetableBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable)
        val sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE)
        val name = sharedPreferences.getString("username", "")
        val division = sharedPreferences.getString("division", "")
        val className = sharedPreferences.getString("classname", "")
        binding.name.text = "Welcome "+name+"\n"+className+"\n"+division

    }
}