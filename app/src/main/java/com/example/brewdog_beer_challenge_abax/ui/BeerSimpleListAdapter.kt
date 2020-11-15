package com.example.brewdog_beer_challenge_abax.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.R
import com.example.brewdog_beer_challenge_abax.datacenter.MediatorClass
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_screen_list_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class BeerSimpleListAdapter(context: Context, private var itemClickListener: ItemClickListener) : RecyclerView.Adapter<BeerSimpleListAdapter.ViewHolder>(){

    private var beerList = emptyList<MediatorClass>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textName: TextView = itemView.findViewById(R.id.name_text)
        val textAbv: TextView = itemView.findViewById(R.id.abv_text)
        val container: View = itemView.findViewById(R.id.container)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerSimpleListAdapter.ViewHolder {
        val itemView = inflater.inflate(R.layout.main_screen_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBeer = beerList[position]

        Picasso.get().load(currentBeer.beer.imageURL).into(holder.imageView)
        holder.textName.text = currentBeer.beer.name
        holder.textAbv.text = "ABV: ${currentBeer.beer.abv} %"

        holder.container.setOnClickListener{
            itemClickListener.itemClick(currentBeer)
        }

    }

    interface ItemClickListener {
        fun itemClick(beerObject: MediatorClass){
//            Log.v("Item clicked!!", "Selected beer is " + beerObject.beer.name)
        }
    }

    internal fun setData(beerList: List<MediatorClass>){
        this.beerList = beerList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return beerList.size
    }

}