package com.example.brewdog_beer_challenge_abax

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.datacenter.BeerViewModel
import com.example.brewdog_beer_challenge_abax.datacenter.MediatorClass
import com.example.brewdog_beer_challenge_abax.ui.BeerSimpleListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var beerViewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

        var backButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BeerSimpleListAdapter(this, object : BeerSimpleListAdapter.ItemClickListener {
            override fun itemClick(beerObject: MediatorClass) {
                Log.v("Item clicked!!", "Selected beer is " + beerObject.beer.name)

                findViewById<LinearLayout>(R.id.mainContainer).visibility = View.GONE
                findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.VISIBLE
                backButton.visibility = View.VISIBLE

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

//        val dividerItemDecoration = DividerItemDecoration(
//            recycler.getContext(),
//            (recycler.layoutManager as LinearLayoutManager).orientation
//        )
//        recycler.addItemDecoration(dividerItemDecoration)

        beerViewModel.allBeers.observe(this, Observer { data ->
            data?.let {
                adapter.setData(data)
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (findViewById<LinearLayout>(R.id.mainContainer).visibility == View.GONE){
            findViewById<LinearLayout>(R.id.mainContainer).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
            findViewById<FloatingActionButton>(R.id.back_button).visibility = View.GONE
        }
    }
}