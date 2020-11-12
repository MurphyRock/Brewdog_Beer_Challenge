package com.example.brewdog_beer_challenge_abax.datacenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brewdog_beer_challenge_abax.networking.BeerAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BeerViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BeerRepository = BeerRepository(application)
    val allBeersAPI = MutableLiveData<String>()
    val allBeers: LiveData<List<BeerClass>> = repository.observeAll()!!

    init {
        getRetrofitBeers()
    }

    private fun getRetrofitBeers(){
        viewModelScope.launch {
//            try {
                val beersList = BeerAPI.retrofitService.getBeers().enqueue(
                    object: Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            allBeersAPI.value = "Failure retrieving Beers + ${t.message}"
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            allBeersAPI.value = "Success retrieving Beers: ${response.body()}"
                        }

                    })
//                allBeersAPI.value = "Success retrieving Beers: $beersList"
//            }catch (e: Exception){
//                allBeersAPI.value = "Failure retrieving Beers: ${e.message}"
//            }
        }
    }

    fun insert(beerObject: BeerClass) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBeer(beerObject)
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

    fun observeAllBeer(): LiveData<List<BeerClass>>? {
        return allBeers
    }

    fun getAllBeer(): List<BeerClass>? {
        return repository.getAllBeers()
    }

    fun observeById(id: Int): LiveData<BeerClass>?{
        return repository.observeById(id)
    }

    fun getById(id: Int): BeerClass?{
        return repository.getById(id)
    }
}