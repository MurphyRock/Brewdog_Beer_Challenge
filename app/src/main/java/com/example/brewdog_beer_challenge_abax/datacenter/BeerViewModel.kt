package com.example.brewdog_beer_challenge_abax.datacenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BeerRepository = BeerRepository(application)
    val allBeers: LiveData<List<BeerClass>> = repository.observeAll()!!

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