package com.example.brewdog_beer_challenge_abax

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.datacenter.*
import com.example.brewdog_beer_challenge_abax.ui.BeerSimpleListAdapter
import com.example.brewdog_beer_challenge_abax.ui.HopsSimpleListAdapter
import com.example.brewdog_beer_challenge_abax.ui.MaltsSimpleListAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


private const val ARG_PARAM1 = "param1"

class DetailsScreenFragment : Fragment() {
    private var param1: Long? = null
    private lateinit var beerViewModel: BeerViewModel
    private lateinit var beer: BeerClass
    private lateinit var hops: List<HopsClass>
    private lateinit var malts: List<MaltClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
        }
        beerViewModel = ViewModelProvider(this).get(BeerViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_details_screen, container, false)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                beer =  beerViewModel.getById(param1!!)!!
                hops =  beerViewModel.getHopsById(param1!!)!!
                malts =  beerViewModel.getMaltsById(param1!!)!!
                withContext(Dispatchers.Main){
                    Picasso.get().load(beer.imageURL).into(view.findViewById<ImageView>(R.id.imageView))
                    view.findViewById<TextView>(R.id.name_text).text = beer.name
                    view.findViewById<TextView>(R.id.abv_text).text = "ABV: ${beer.abv} %"
                    view.findViewById<TextView>(R.id.description_text).text = beer.description

                    // Methods
                    var mashTemps: String = ""
                    for (mash in beer.methods.mashTemp){
                        if (mash.indexOf("-") != -1){
                            mashTemps += (mash.substring(0, mash.indexOf("-")))
                            mashTemps += "for ${mash.substring(mash.indexOf("-") + 1)} min, "
                        }else{
                            mashTemps += "$mash, "
                        }
                    }
                    mashTemps = mashTemps.substring(0, mashTemps.length-2)


                    view.findViewById<TextView>(R.id.methods_mashtemp_text).text = mashTemps
                    view.findViewById<TextView>(R.id.methods_fermentation_text).text = beer.methods.fermentation
                    if (beer.methods.twist == null || beer.methods.twist == ""){
                        view.findViewById<LinearLayout>(R.id.twist_container).visibility = View.INVISIBLE
                    }else{
                        view.findViewById<TextView>(R.id.methods_twist_text).text = beer.methods.twist
                    }



                    //Ingredients
//                    view.findViewById<TextView>(R.id.hops_text).text = hops[0].nameHops
//                    view.findViewById<TextView>(R.id.malts_text).text = malts[0].nameMalt

                    val recyclerHops: RecyclerView = view.findViewById<RecyclerView>(R.id.hops_text)
                    val adapterHops = context?.let { HopsSimpleListAdapter(it)}
                    recyclerHops.adapter = adapterHops
                    recyclerHops.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapterHops?.setData(hops)

                    val maltsrecycler: RecyclerView = view.findViewById<RecyclerView>(R.id.malts_text)
                    val maltsadapter = context?.let { MaltsSimpleListAdapter(it) }
                    maltsrecycler.adapter = maltsadapter
                    maltsrecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    maltsadapter?.setData(malts)

                }
            }
        }

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: Long) =
                DetailsScreenFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_PARAM1, param1)
                    }
                }
    }
}