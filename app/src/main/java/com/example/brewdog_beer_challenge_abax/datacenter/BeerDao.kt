package com.example.brewdog_beer_challenge_abax.datacenter

import androidx.lifecycle.LiveData
import androidx.room.*

// This is the BeerDao interface. It only contains the the SQL order, represented as functions.

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(beerObject: BeerClass?): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHop(hopObject: HopsClass?): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMalt(maltObject: MaltClass?): Long

    @Update
    suspend fun updateBeer(beerObject: BeerClass?)

    @Query("DELETE FROM BeerClass")
    fun deleteAll()

    @Delete
    fun deleteBeer(beerObject: BeerClass)

    @Transaction
    @Query("SELECT * from BeerClass ORDER BY idBeer ASC")
    fun observeAll(): LiveData<List<MediatorClass>>

    @Transaction
    fun insertBeer(beer: BeerClass?, hopsList: List<HopsClass>, maltsList: List<MaltClass>) {
        // Save rowId of inserted CompanyEntity as companyId
        val beerId: Long = insertBeer(beer)

        // Set companyId for all related employeeEntities
        for (hop in hopsList) {
            hop.idHopsBeer = beerId
            insertHop(hop)
        }
        for (malt in maltsList) {
            malt.idMaltBeer = beerId
            insertMalt(malt)
        }
    }

    @Query("SELECT * from BeerClass ORDER BY idBeer ASC")
    fun getAll(): List<BeerClass>


    @Query("SELECT * from BeerClass WHERE idBeer = :providedId")
    fun observeById(providedId: Int): LiveData<BeerClass>

    @Query("SELECT * from BeerClass WHERE idBeer = :providedId")
    fun getById(providedId: Int): BeerClass?

}
