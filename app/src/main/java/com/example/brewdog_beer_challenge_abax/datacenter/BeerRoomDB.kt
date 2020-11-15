package com.example.brewdog_beer_challenge_abax.datacenter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brewdog_beer_challenge_abax.tools.Converters

/*
 - BeerRoomDB is a database layer on top of an SQLite database.
 - BeerRoomDB takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 - BeerRoomDB uses the DAO to issue queries to its database.
 - By default, to avoid poor UI performance, BeerRoomDB doesn't allow to issue queries on the main thread.
    When Room queries return LiveData, the queries are automatically run asynchronously on a background thread.
 - BeerRoomDB provides compile-time checks of SQLite statements.
*/

@Database(entities = [BeerClass::class, HopsClass::class, MaltClass::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BeerRoomDB: RoomDatabase() {

    abstract fun beerDao(): BeerDao

    companion object{
        // Singleton prevents multiple instances of database opening at the same time.
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