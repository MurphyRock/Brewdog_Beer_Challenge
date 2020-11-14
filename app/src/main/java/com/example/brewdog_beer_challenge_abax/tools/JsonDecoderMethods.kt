package com.example.brewdog_beer_challenge_abax.tools

import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.example.brewdog_beer_challenge_abax.datacenter.BeerClass
import com.example.brewdog_beer_challenge_abax.datacenter.HopsClass
import com.example.brewdog_beer_challenge_abax.datacenter.MaltClass
import com.example.brewdog_beer_challenge_abax.datacenter.MethodClass

class JsonDecoderMethods {

    companion object {
        fun getMalt(jsonMaltHopsList: JsonArray<*>): List<MaltClass> {
            var jsonMaltHopsObject: JsonObject
            val maltList: MutableList<MaltClass> = mutableListOf<MaltClass>()

            for (x in 0 until jsonMaltHopsList.size) {
                jsonMaltHopsObject = jsonMaltHopsList[x] as JsonObject

                maltList.add(MaltClass(
                    0, 0,
                    jsonMaltHopsObject["name"] as String,
                    ((jsonMaltHopsObject["amount"] as JsonObject)["value"] as Number).toString() + " " +
                            (jsonMaltHopsObject["amount"] as JsonObject)["unit"] as String
                ))
            }

            return maltList
        }

        fun getHops(jsonMaltHopsList: JsonArray<*>): List<HopsClass> {
            var jsonMaltHopsObject: JsonObject
            val hopsList: MutableList<HopsClass> = mutableListOf<HopsClass>()

            for (x in 0 until jsonMaltHopsList.size) {
                jsonMaltHopsObject = jsonMaltHopsList[x] as JsonObject

                hopsList.add(HopsClass(
                        0, 0,
                        jsonMaltHopsObject["name"] as String,
                        (((jsonMaltHopsObject["amount"] as JsonObject)["value"] as Number).toString() + " " +
                                (jsonMaltHopsObject["amount"] as JsonObject)["unit"] as String),
                        jsonMaltHopsObject["add"] as String,
                        jsonMaltHopsObject["attribute"] as String
                    )
                )
            }

            return hopsList
        }

        fun getMashTemp(beerMeshTempJsonList: JsonArray<*>): List<String> {
            var mashTempList: List<String> = emptyList()

            var jsonMeshTempJsonObject: JsonObject
            var jsonTempJsonObject: JsonObject
            var jsonMeshTempString: String
            for (x in 0 until beerMeshTempJsonList.size) {
                jsonMeshTempJsonObject = beerMeshTempJsonList[x] as JsonObject
                jsonTempJsonObject = jsonMeshTempJsonObject["temp"] as JsonObject
                jsonMeshTempString = (jsonTempJsonObject["value"] as Int).toString() + " " +
                        jsonTempJsonObject["unit"] as String + "-" +
                        (jsonMeshTempJsonObject["duration"] as Int?).toString()

                mashTempList = mashTempList + jsonMeshTempString
            }
            return mashTempList
        }

        fun getFermentation(beerMethodJsonObject: JsonObject): String {
            return (((beerMethodJsonObject["fermentation"] as JsonObject)["temp"] as JsonObject)["value"] as Int).toString() + " " +
                    ((beerMethodJsonObject["fermentation"] as JsonObject)["temp"] as JsonObject)["unit"] as String
        }
    }
}