package com.seda.a7minuteworkoutapp.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.seda.a7minuteworkoutapp.FinishActivity
import com.seda.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import com.seda.a7minuteworkoutapp.mvvm.HistoryMvvm
import com.seda.a7minuteworkoutapp.mvvm.HistroyFactory
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding?=null
    private lateinit var mvvm: HistoryMvvm
    private lateinit var db:HistoryDatabase
    private lateinit var  mealDao: HistoryDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
//Todo 9 bind the layout to this activity
        setContentView(binding?.root)



        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "HISTORY" // Setting a title in the action bar.
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        db = HistoryDatabase.getInstance(this)
        mealDao = db.historyDao()
        val repository = HistoryRepository(mealDao)
        val viewModelFactory = HistroyFactory(repository)
        mvvm   = ViewModelProvider(this,viewModelFactory).get(HistoryMvvm::class.java)

    }
private suspend fun getAllComplete(){
    lifecycleScope.launch{
        mvvm.allhistory.collect{
            
        }
    }
}
    override fun onDestroy() {
        super.onDestroy()
//Todo 12 reset the binding to null to avoid memory leak
        binding = null
    }
}