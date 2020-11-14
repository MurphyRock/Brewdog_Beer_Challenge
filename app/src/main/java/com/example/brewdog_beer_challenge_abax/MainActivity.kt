package com.example.brewdog_beer_challenge_abax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brewdog_beer_challenge_abax.datacenter.BeerClass
import com.example.brewdog_beer_challenge_abax.datacenter.BeerViewModel
import com.example.brewdog_beer_challenge_abax.datacenter.HopsClass
import com.example.brewdog_beer_challenge_abax.datacenter.MaltClass
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var beerViewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

        findViewById<Button>(R.id.testButton).setOnClickListener {
            Snackbar.make(it, "Added test example", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            beerViewModel.insert(BeerClass(
                0,
                "testURL",
                "name",
                2.5,
                "Description wow"),
                arrayListOf(
                    HopsClass(0, 10,"hops1", 2, "yes", "black"),
                    HopsClass(0, 11,"hops2", 3, "no", "blond")),
                arrayListOf(
                    MaltClass(0, 10,"hops1", 2),
                    MaltClass(0, 11,"hops2", 3))
            )
        }

        beerViewModel.allBeers.observe(this, Observer { data ->
            data?.let {
                findViewById<TextView>(R.id.testText).text = data.toString()
            }
        })
    }

//    override fun onStart() {
//        super.onStart()
//        GlobalScope.async(Dispatchers.IO) {
//            beerViewModel.insert(BeerClass(
//                0,
//                "testURL",
//                "name",
//                2.5,
//                "Description wow",
//                arrayListOf("a", "b", "c"),
//                arrayListOf("a", "b", "c"),
//                arrayListOf("a", "b", "c")
//                )
//            )
//
//        }
//    }
    override fun onStop() {
        super.onStop()
        GlobalScope.async(Dispatchers.IO) {
            beerViewModel.deleteAll()

        }
    }
}