package com.seda.a7minuteworkoutapp.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var popularAdapter: HistoryAdapter
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
        val viewModelFactory =HistroyFactory(repository)
        mvvm   = ViewModelProvider(this,viewModelFactory).get(HistoryMvvm::class.java)
getAllComplete()
    }
private  fun getAllComplete(){
    lifecycleScope.launch{
    mvvm.allhistory.collect{all->
        if(all.isNotEmpty()){
            binding?.tvHistory?.visibility = View.VISIBLE
            binding?.rvHistory?.visibility = View.VISIBLE
            binding?.tvNoDataAvailable?.visibility = View.GONE
            binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity,LinearLayoutManager.VERTICAL,false)

            // History adapter is initialized and the list is passed in the param.
            val dates = ArrayList<String>()
            for (date in all){
                dates.add(date.date)
                Log.e("cevap:","${date.date}")
            }
            popularAdapter= HistoryAdapter(dates)

            // Access the RecyclerView Adapter and load the data into it
            binding?.rvHistory?.adapter = popularAdapter



        }else {
            binding?.tvHistory?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.GONE
            binding?.tvNoDataAvailable?.visibility = View.VISIBLE
        }
        // END

    }


    }
}

    override fun onDestroy() {
        super.onDestroy()
//Todo 12 reset the binding to null to avoid memory leak
        binding = null
    }
}