package com.example.brewdog_beer_challenge_abax.datacenter

import android.app.Application
import androidx.lifecycle.LiveData

// BeerRepository that abstracts access to multiple data sources
// not part of the Architecture Components libraries, but it is, as far as I know, a suggested practice for code separation and architecture.
// A Repository manages queries and allows you to use multiple backends.
class BeerRepository(application: Application) {

    private var beerDao: BeerDao? = null
    private var allBeers: LiveData<List<MediatorClass>>? = null

    init {
        val db: BeerRoomDB = BeerRoomDB.getDatabase(application!!)
        beerDao = db.beerDao()
        allBeers = beerDao!!.observeAll()
    }

    // Suspend methods so they have to be called by using coroutines
    // It's optional, depending on the requirements, but you can also implement the background process here

    suspend fun insertBeer(beerObject: BeerClass, hopsList: List<HopsClass>, maltsList: List<MaltClass>) {
        beerDao?.insertBeer(beerObject, hopsList, maltsList)
    }

    suspend fun deleteAll(){
        beerDao?.deleteAll()
    }

    fun observeAll(): LiveData<List<MediatorClass>>? {
        return allBeers
    }

    fun getById(id: Long): BeerClass?{
        return beerDao?.getById(id)
    }
    fun getHopsById(id: Long): List<HopsClass>?{
        return beerDao?.getHopsById(id)
    }
    fun getMaltsById(id: Long): List<MaltClass>?{
        return beerDao?.getMaltsById(id)
    }
}