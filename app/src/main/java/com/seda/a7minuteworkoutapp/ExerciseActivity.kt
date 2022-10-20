package com.seda.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.seda.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import com.seda.a7minuteworkoutapp.model.ExerciseModel

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null // Variable for Rest Timer and later on we will initialize it.
    private var restProgress = 0
    // START
    private var exerciseTimer: CountDownTimer? = null // Variable for Exercise Timer and later on we will initialize it.
    private var exerciseProgress =0
    private var exerciseTimerDuration:Long = 30

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
            exerciseList = Constants.defaultExercise()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }
    private fun setupRestView() {


        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        // This function is used to set the progress details.
        setRestProgressBar()
    }
    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress // Sets the current progress to the specified value.


        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                binding?.progressBar?.progress = 10 - restProgress // Indicates progress bar progress
                binding?.tvTimer?.text =
                    (10 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.
                currentExercisePosition++
              setupExerciseView()
            }
        }.start()
    }


    private fun setupExerciseView() {

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerDuration.toInt() - exerciseProgress
                binding?.tvTimerExercise?.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "This is 30 seconds completed so now we will add all the exercises.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }
}