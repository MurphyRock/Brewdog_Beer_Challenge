package com.example.brewdog_beer_challenge_abax.datacenter

import androidx.lifecycle.LiveData
import androidx.room.*

// This is the BeerDao interface. It only contains the the SQL order, represented as functions.

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(beerObject: BeerClass?)

    @Update
    suspend fun updateBeer(beerObject: BeerClass?)

    @Query("DELETE FROM beer_table")
    fun deleteAll()

    @Delete
    fun deleteBeer(beerObject: BeerClass)

    @Query("SELECT * from beer_table ORDER BY id ASC")
    fun observeAll(): LiveData<List<BeerClass>>

    @Query("SELECT * from beer_table ORDER BY id ASC")
    fun getAll(): List<BeerClass>


    @Query("SELECT * from beer_table WHERE id = :providedId")
    fun observeById(providedId: Int): LiveData<BeerClass>

    @Query("SELECT * from beer_table WHERE id = :providedId")
    fun getById(providedId: Int): BeerClass?

}