package com.example.brewdog_beer_challenge_abax.ui

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brewdog_beer_challenge_abax.R
import com.example.brewdog_beer_challenge_abax.datacenter.HopsClass
import com.example.brewdog_beer_challenge_abax.datacenter.MaltClass

class MaltsSimpleListAdapter(context: Context) : RecyclerView.Adapter<MaltsSimpleListAdapter.ViewHolder>(){

    private var ingredientsList = emptyList<MaltClass>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textName: TextView = itemView.findViewById(R.id.name_text)
        val textAmount: TextView = itemView.findViewById(R.id.amount_text)
        val textExtra: TextView = itemView.findViewById(R.id.extra_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaltsSimpleListAdapter.ViewHolder {
        val itemView = inflater.inflate(R.layout.hops_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHop = ingredientsList[position]

        holder.textName.text = currentHop.nameMalt
        holder.textAmount.text = currentHop.amount
        holder.textExtra.visibility = View.GONE

    }


    internal fun setData(ingredientsList: List<MaltClass>){
        this.ingredientsList = ingredientsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

}