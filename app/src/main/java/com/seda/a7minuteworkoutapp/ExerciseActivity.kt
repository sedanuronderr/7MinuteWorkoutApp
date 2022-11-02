package com.seda.a7minuteworkoutapp

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.seda.a7minuteworkoutapp.adapter.ExerciseStatusAdapter
import com.seda.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import com.seda.a7minuteworkoutapp.model.ExerciseModel

import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null // Variable for Rest Timer and later on we will initialize it.
    private var restProgress = 0
    // START
    private var exerciseTimer: CountDownTimer? = null // Variable for Exercise Timer and later on we will initialize it.
    private var exerciseProgress =0
    private var exerciseTimerDuration:Long = 30

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    private var exerciseAdapter: ExerciseStatusAdapter? = null


    // Metin konuşma
    private var tts :TextToSpeech?=null
    private var player: MediaPlayer? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
            exerciseList = Constants.defaultExercise()
            setupRestView()
            tts = TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
setupExerciseStatusRecyclerView()

    }
    private fun setupExerciseStatusRecyclerView() {

        // Defining a layout manager for the recycle view
        // Here we have used a LinearLayout Manager with horizontal scroll.
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // As the adapter expects the exercises list and context so initialize it passing it.
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)

        // Adapter class is attached to recycler view
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }





    private fun setupRestView() {
player = MediaPlayer()

        try {
            val soundURI =
                Uri.parse("android.resource://com.seda.a7minuteworkoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, R.raw.press_start)

            player?.isLooping = false // Sets the player to be looping or non-looping.
            player?.prepare()
            player?.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }



        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility =View.VISIBLE
        binding?.tvExerciseName?.visibility =View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE

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
                exerciseList?.get(currentExercisePosition)?.isSelected= true
                exerciseAdapter?.notifyDataSetChanged()
              setupExerciseView()
            }
        }.start()
    }


    private fun setupExerciseView() {

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility =View.INVISIBLE
        binding?.tvExerciseName?.visibility =View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
speakOut(exerciseList!![currentExercisePosition].name)
            binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].image)
            binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].name

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
                exerciseList!![currentExercisePosition].isSelected= false // exercise is completed so selection is set to false
                exerciseList!![currentExercisePosition].isCompleted =true
                exerciseAdapter?.notifyDataSetChanged()


              if(currentExercisePosition < exerciseList?.size!! - 1){
                  setupRestView()
              }
              else{
                  Toast.makeText(
                      this@ExerciseActivity,
                      "Congratulations! You have completed the 7 minutes workout.",
                      Toast.LENGTH_SHORT
                  ).show()
              }
            }
        }.start()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        // TODO (Step 7 - Shutting down the Text to Speech feature when activity is destroyed.)
        // START
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        // END
        super.onDestroy()
        binding = null


    }
}