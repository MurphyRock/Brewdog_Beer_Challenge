package com.example.brewdog_beer_challenge_abax.datacenter

import androidx.room.*
import com.example.brewdog_beer_challenge_abax.tools.Converters

// This is the Beer class to be use when managing the data once it is pulled from the API

data class MediatorClass(
    @Embedded var beer: BeerClass,
    @Relation(
        parentColumn = "idBeer",
        entityColumn = "idHopsBeer"
    ) var hops: List<HopsClass>,
    @Relation(
        parentColumn = "idBeer",
        entityColumn = "idMaltBeer"
    ) var malts: List<MaltClass>

)

@Entity
data class BeerClass(
    @PrimaryKey(autoGenerate = true) var idBeer: Long,
    var imageURL: String,
    var name: String,
    var abv: Double,
    var description: String,
    @Embedded var methods: MethodClass

)

@Entity
data class MethodClass(
//    @PrimaryKey(autoGenerate = true) var idMethod: Long,
    @TypeConverters(Converters::class) var mashTemp: List<String>,  // ("60 Celsius-10", "65 Celsius-30", "72 Celsius-10")
    var fermentation: String,  //  "21 Celsius"
    var twist: String?
)

@Entity
data class MaltClass(
    @PrimaryKey(autoGenerate = true) var idMalt: Long,
    var idMaltBeer: Long,
    var nameMalt: String,
    var amount: String  // "1.63 Kilograms"
)

@Entity
data class HopsClass(
    @PrimaryKey(autoGenerate = true) var idHops: Long,
    var idHopsBeer: Long,
    var nameHops: String,
    var amount: String,  // "10 Grams"
    var add: String,
    var attribute: String
)
