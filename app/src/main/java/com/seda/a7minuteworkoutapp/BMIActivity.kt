package com.seda.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seda.a7minuteworkoutapp.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityBmiBinding.inflate(layoutInflater)
            setContentView(binding?.root)

            setSupportActionBar(binding?.toolbarBmiActivity)
            supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
            supportActionBar?.title = "CALCULATE BMI" // Setting a title in the action bar.
            binding?.toolbarBmiActivity?.setNavigationOnClickListener {
                onBackPressed()
            }
        }
}