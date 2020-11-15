package com.example.brewdog_beer_challenge_abax.datacenter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.brewdog_beer_challenge_abax.networking.BeerAPI
import com.example.brewdog_beer_challenge_abax.tools.JsonDecoderMethods
//import com.example.brewdog_beer_challenge_abax.tools.JsonDecoderMethods.Companion.getFermentation
//import com.example.brewdog_beer_challenge_abax.tools.JsonDecoderMethods.Companion.getHops
//import com.example.brewdog_beer_challenge_abax.tools.JsonDecoderMethods.Companion.getMalt
//import com.example.brewdog_beer_challenge_abax.tools.JsonDecoderMethods.Companion.getMashTemp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader
import java.lang.Exception
import kotlin.math.log

class BeerViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BeerRepository = BeerRepository(application)
    val allBeers: LiveData<List<MediatorClass>> = repository.observeAll()!!

    private val _allBeersAPI = MutableLiveData<String>()
    private val allBeersAPI: LiveData<String>
        get() = _allBeersAPI

    init {
        getRetrofitBeers()
    }

    private fun getRetrofitBeers(){
        viewModelScope.launch {
            var beersJsonString: String = ""
            try {
                beersJsonString = BeerAPI.retrofitService.getBeers()
                Log.v("API request succeed", "Saving data into the DB")
            }catch (e: Exception){
                Log.v("API request failed", e.toString())
                Log.v("API request failed", "Using internal data")
            }
            if (beersJsonString != "") {
                try {
                    val json = Klaxon().parseJsonArray(StringReader(beersJsonString))
                    repository.deleteAll()
                    jsonToDB(json)
                }catch (e: Exception){
                    Log.v("Saving data failed", e.toString())
                }
            }
        }
    }

    private suspend fun jsonToDB(json: JsonArray<*>){
        var jsonBeerObject: JsonObject
        for (x in 0 until json.size){
            jsonBeerObject = json[x] as JsonObject

            val beerName = jsonBeerObject["name"] as String
            val beerImage = jsonBeerObject["image_url"] as String
            val beerAbv = jsonBeerObject["abv"] as Number
            val beerDescription = jsonBeerObject["description"] as String
            val beerMethodJsonObject = jsonBeerObject["method"] as JsonObject

            val beerObject = BeerClass(0,
                beerImage,
                beerName,
                beerAbv.toDouble(),
                beerDescription,
                MethodClass(
                    JsonDecoderMethods.getMashTemp(beerMethodJsonObject["mash_temp"] as JsonArray<*>),
                    JsonDecoderMethods.getFermentation(beerMethodJsonObject),
                    beerMethodJsonObject["twist"] as String?
                )
            )

            val beerMaltJsonList = (jsonBeerObject["ingredients"] as JsonObject)["malt"] as JsonArray<*>
            val beerHopsJsonList = (jsonBeerObject["ingredients"] as JsonObject)["hops"] as JsonArray<*>
            val beerMaltList: List<MaltClass> = JsonDecoderMethods.getMalt(beerMaltJsonList)
            val beerHopsList: List<HopsClass> = JsonDecoderMethods.getHops(beerHopsJsonList)

            repository.insertBeer(beerObject, beerHopsList, beerMaltList)
        }
    }

    fun insert(beerObject: BeerClass) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBeer(beerObject)
    }
    suspend fun insert(beerObject: BeerClass, hopsList: List<HopsClass>, maltsList: List<MaltClass>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBeer(beerObject, hopsList, maltsList)
    }

    fun updateBeer(beerObject: BeerClass) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateBeer(beerObject)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun  deleteItem(beerObject: BeerClass) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBeer(beerObject)
    }

    fun observeAllBeer(): LiveData<List<MediatorClass>>? {
        return allBeers
    }

    fun getAllBeer(): List<BeerClass>? {
        return repository.getAllBeers()
    }

    fun observeById(id: Int): LiveData<BeerClass>?{
        return repository.observeById(id)
    }

    suspend fun getById(id: Long): BeerClass?{
        return repository.getById(id)
    }
    suspend fun getHopsById(id: Long): List<HopsClass>?{
        return repository.getHopsById(id)
    }
    suspend fun getMaltsById(id: Long): List<MaltClass>?{
        return repository.getMaltsById(id)
    }
}