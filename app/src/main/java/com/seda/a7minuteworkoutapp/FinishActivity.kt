package com.seda.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.seda.a7minuteworkoutapp.databinding.ActivityFinishBinding
import com.seda.a7minuteworkoutapp.history.HistoryApp
import com.seda.a7minuteworkoutapp.history.HistoryDao
import com.seda.a7minuteworkoutapp.history.HistoryEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

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
        val dao = (application as HistoryApp).db.historyDao()
        addDateToDatabase(dao)

    }
    private fun addDateToDatabase(historyDao: HistoryDao){
        val c = Calendar.getInstance() // Calendars Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }

    }

}