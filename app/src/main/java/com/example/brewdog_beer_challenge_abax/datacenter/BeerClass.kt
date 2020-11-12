package com.example.brewdog_beer_challenge_abax.datacenter

import androidx.room.*
import com.example.brewdog_beer_challenge_abax.tools.Converters

// This is the Beer class to be use when managing the data once it is pulled from the API

@Entity(tableName = "beer_table")
class BeerClass(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") private var id: Int,

    @ColumnInfo(name = "imageURL") private var imageURL: String,
    @ColumnInfo(name = "name") private var name: String,
    @ColumnInfo(name = "abv") private var abv: Double,
    @ColumnInfo(name = "description") private var description: String,
//    @ColumnInfo(name = "hopsList")
    @TypeConverters(Converters::class) @ColumnInfo(name = "hopsList") private var hopsList: ArrayList<String>,
    @TypeConverters(Converters::class) @ColumnInfo(name = "maltList") private var maltList: ArrayList<String>,
    @TypeConverters(Converters::class) @ColumnInfo(name = "methodList") private var methodList: ArrayList<String>
) {

    fun getId(): Int{
        return id;
    }
    fun getImageURL(): String{
        return imageURL;
    }
    fun getName(): String{
        return name;
    }
    fun getAbv(): Double{
        return abv;
    }
    fun getDescription(): String{
        return description;
    }
    fun getHopsList(): ArrayList<String>{
        return hopsList;
    }
    fun getMaltList(): ArrayList<String>{
        return maltList;
    }
    fun getMethodList(): ArrayList<String>{
        return methodList;
    }

    override fun toString(): String {
        return "BeerClass(id=$id, imageURL='$imageURL', name='$name', abv=$abv, description='$description', hopsList=$hopsList, maltList=$maltList, methodList=$methodList)"
    }

}
