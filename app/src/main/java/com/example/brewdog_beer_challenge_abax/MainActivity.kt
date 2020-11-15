package com.example.brewdog_beer_challenge_abax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.datacenter.*
import com.example.brewdog_beer_challenge_abax.ui.BeerSimpleListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var beerViewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)


        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BeerSimpleListAdapter(this, object : BeerSimpleListAdapter.ItemClickListener {
            override fun itemClick(beerObject: MediatorClass) {
                Log.v("Item clicked!!", "Selected beer is " + beerObject.beer.name)

                findViewById<LinearLayout>(R.id.mainContainer).visibility = View.GONE
                findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.VISIBLE

                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack("test")
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                val beerDetailsScreen: DetailsScreenFragment = DetailsScreenFragment.newInstance(beerObject.beer.idBeer)
                transaction.replace(R.id.fragmentContainer, beerDetailsScreen, "tag").commit()

            }
        })
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)


        beerViewModel.allBeers.observe(this, Observer { data ->
            data?.let {
                adapter.setData(data)
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (findViewById<LinearLayout>(R.id.mainContainer).visibility == View.GONE){
            findViewById<LinearLayout>(R.id.mainContainer).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
        }
    }
}