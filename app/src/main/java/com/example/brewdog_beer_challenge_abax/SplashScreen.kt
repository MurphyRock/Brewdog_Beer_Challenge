package com.example.brewdog_beer_challenge_abax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.example.brewdog_beer_challenge_abax.datacenter.BeerViewModel

class SplashScreen : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val SPLASH_DELAY: Long = 1000
    private var delayHandler: Handler? = null
    private var progressBarStatus = 0
    private var dummyOperation:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initiating the ViewModel here so we can start retrieving the data from the API and save to the DB before MainActivity is executed
        var beerViewModel: BeerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

        progressBar = findViewById(R.id.progress_bar)
        delayHandler = Handler()
        //Navigate with delay
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }

    private fun launchMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
        delayHandler!!.removeCallbacks(runnable)

    }
    private val runnable: Runnable = Runnable {
        Thread(Runnable {
            while (progressBarStatus < 100) {
                // performing some dummy operation
                try {
                    dummyOperation += 25
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressBarStatus = dummyOperation
                progressBar.progress = progressBarStatus
            }
            launchMainActivity()
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }
    }

}