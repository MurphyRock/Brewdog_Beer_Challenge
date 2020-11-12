package com.example.brewdog_beer_challenge_abax.datacenter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brewdog_beer_challenge_abax.tools.Converters

@Database(entities = [BeerClass::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BeerRoomDB: RoomDatabase() {

    abstract fun beerDao(): BeerDao

    companion object{
        @Volatile
        private var INSTANCE: BeerRoomDB? = null

        fun getDatabase(context: Context): BeerRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeerRoomDB::class.java,
                    "beer_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}