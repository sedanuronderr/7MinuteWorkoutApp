package com.seda.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.seda.a7minuteworkoutapp.databinding.ActivityFinishBinding
import com.seda.a7minuteworkoutapp.history.HistoryDao
import com.seda.a7minuteworkoutapp.history.HistoryDatabase
import com.seda.a7minuteworkoutapp.history.HistoryEntity
import com.seda.a7minuteworkoutapp.history.HistoryRepository
import com.seda.a7minuteworkoutapp.mvvm.HistoryMvvm
import com.seda.a7minuteworkoutapp.mvvm.HistroyFactory
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    private lateinit var db:HistoryDatabase
    private lateinit var  mealDao: HistoryDao
private lateinit var mvvm: HistoryMvvm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
// Todo  bind the layout to this Activity
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
            binding?.btnFinish?.setOnClickListener {
                finish()
            }
        db = HistoryDatabase.getInstance(this)
        mealDao = db.historyDao()
        val repository = HistoryRepository(mealDao)
        val viewModelFactory =HistroyFactory(repository)
        mvvm   = ViewModelProvider(this,viewModelFactory).get(HistoryMvvm::class.java)

addDateToDatabase()
    }
    private fun addDateToDatabase(){
        val c = Calendar.getInstance() // Calendars Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date)
        // Formatted date is printed in the log.
        val history =HistoryEntity(date)
mvvm.insertTodo(history)

    }

}