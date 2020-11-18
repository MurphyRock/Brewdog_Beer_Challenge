package com.example.brewdog_beer_challenge_abax

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.datacenter.BeerViewModel
import com.example.brewdog_beer_challenge_abax.datacenter.MediatorClass
import com.example.brewdog_beer_challenge_abax.ui.BeerSimpleListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private lateinit var beerViewModel: BeerViewModel
    private var internet by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        internet = isNetwork()

        beerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

        // Back button on fragment
        var backButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Recycler, Adapter, clickListener code...
        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BeerSimpleListAdapter(this, internet,  object : BeerSimpleListAdapter.ItemClickListener {
            override fun itemClick(beerObject: MediatorClass) {

                findViewById<LinearLayout>(R.id.mainContainer).visibility = View.GONE
                findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.VISIBLE
                backButton.visibility = View.VISIBLE

                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack("test")
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                val beerDetailsScreen: DetailsScreenFragment = DetailsScreenFragment.newInstance(beerObject.beer.idBeer, internet)
                transaction.replace(R.id.fragmentContainer, beerDetailsScreen, "tag").commit()

            }
        })
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        // View model observer will update the adapter with any data change on real time
        // In this case, the data is not suppose to change while is being shown except for one reason,
        // the time that the data takes to come from the API
        beerViewModel.allBeers.observe(this, Observer { data ->
            data?.let {
                adapter.setData(data)
            }
        })
    }

    fun isNetwork(): Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        // In here we return true if network is not null and Network is connected
        return networkInfo != null && networkInfo.isConnected

    }

    override fun onResume() {
        super.onResume()
        internet = isNetwork()
    }

// Controlling the visibility of some views when coming back from the fragment
    override fun onBackPressed() {
        super.onBackPressed()
        internet = isNetwork()
        if (findViewById<LinearLayout>(R.id.mainContainer).visibility == View.GONE){
            findViewById<LinearLayout>(R.id.mainContainer).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
            findViewById<FloatingActionButton>(R.id.back_button).visibility = View.GONE
        }
    }
}