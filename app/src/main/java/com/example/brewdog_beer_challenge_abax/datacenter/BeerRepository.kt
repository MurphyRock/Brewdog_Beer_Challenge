package com.example.brewdog_beer_challenge_abax.datacenter

import android.app.Application
import androidx.lifecycle.LiveData

class BeerRepository(application: Application) {

    private var beerDao: BeerDao? = null
    private var allBeers: LiveData<List<BeerClass>>? = null

    init {
        val db: BeerRoomDB = BeerRoomDB.getDatabase(application!!)
        beerDao = db.beerDao()
        allBeers = beerDao!!.observeAll()
    }

    // Suspend methods so they have to be called by using coroutines
    // It's optional, depending on the requirements, but you can also implement the background process here

    suspend fun insertBeer(beerObject: BeerClass) {
        beerDao?.insertBeer(beerObject)
    }

    suspend fun updateBeer(beerObject: BeerClass){
        beerDao?.updateBeer(beerObject)
    }

    suspend fun deleteAll(){
        beerDao?.deleteAll()
    }

    suspend fun deleteBeer(beerObject: BeerClass){
        beerDao?.deleteBeer(beerObject)
    }

    fun observeAll(): LiveData<List<BeerClass>>? {
        return allBeers
    }

    fun getAllBeers(): List<BeerClass>? {
        return beerDao?.getAll()
    }

    fun observeById(id: Int): LiveData<BeerClass>?{
        return beerDao?.observeById(id)
    }

    fun getById(id: Int): BeerClass?{
        return beerDao?.getById(id)
    }
}