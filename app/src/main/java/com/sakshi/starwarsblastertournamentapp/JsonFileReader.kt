package com.sakshi.starwarsblastertournamentapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.sakshi.starwarsblastertournamentapp.models.PlayerWithPoints
import com.sakshi.starwarsblastertournamentapp.models.StarWarsMatchesItem
import com.sakshi.starwarsblastertournamentapp.models.StarWarsPlayersItem
import java.io.IOException
import java.io.InputStreamReader

object JsonFileReader {

    val playerWithMatchesHashMap = mutableMapOf<Int, ArrayList<StarWarsMatchesItem>>()

    val playerWithPointsHashMap = mutableMapOf<Int, PlayerWithPoints>()

    val playersList = emptyList<Pair<Int, PlayerWithPoints>>().toMutableList()

    fun readStarWarsMatchesJsonFileFromAssets(
        context: Context,
        fileName: String
    ) {
        try {
            val inputStream = context.assets.open(fileName)

            val inputStreamReader = InputStreamReader(inputStream)

            val gson = Gson()
            val jsonParser = JsonParser()
            val jsonElement: JsonElement = jsonParser.parse(inputStreamReader)

            if (jsonElement.isJsonArray) {
                val jsonArray: JsonArray = jsonElement.asJsonArray
                jsonArray.forEach { item ->
                    val model = gson.fromJson(item, StarWarsMatchesItem::class.java)

                    if (model.player1.score > model.player2.score) {
                        val points = playerWithPointsHashMap[model.player1.id]?.points ?: 0
                        playerWithPointsHashMap[model.player1.id]?.points = points + 3

                    } else if (model.player2.score > model.player1.score) {

                        val points = playerWithPointsHashMap[model.player2.id]?.points ?: 0
                        playerWithPointsHashMap[model.player2.id]?.points = points + 3

                    } else if (model.player1.score == model.player2.score) {

                        val pointsP1 = playerWithPointsHashMap[model.player1.id]?.points ?: 0
                        playerWithPointsHashMap[model.player1.id]?.points = pointsP1 + 1

                        val pointsP2 = playerWithPointsHashMap[model.player2.id]?.points ?: 0
                        playerWithPointsHashMap[model.player2.id]?.points = pointsP2 + 1
                    }

                    val listP1 = playerWithMatchesHashMap[model.player1.id]
                        ?: ArrayList(emptyList<StarWarsMatchesItem>())
                    listP1.add(model)
                    playerWithMatchesHashMap[model.player1.id] = listP1

                    val listP2 = playerWithMatchesHashMap[model.player2.id]
                        ?: ArrayList(emptyList<StarWarsMatchesItem>())
                    listP2.add(model)
                    playerWithMatchesHashMap[model.player2.id] = listP2

                    val currentScoreP1 = playerWithPointsHashMap[model.player1.id]?.score ?: 0
                    val currentScoreP2 = playerWithPointsHashMap[model.player2.id]?.score ?: 0

                    playerWithPointsHashMap[model.player1.id]?.score =
                        currentScoreP1 + model.player1.score

                    playerWithPointsHashMap[model.player2.id]?.score =
                        currentScoreP2 + model.player2.score

                }

                val customComparator =
                    compareByDescending<Pair<Int, PlayerWithPoints>> { it.second.points }.thenByDescending { it.second.score }

                playersList.clear()
                playersList.addAll(
                    playerWithPointsHashMap.toList().sortedWith(customComparator)
                )
            }

            inputStreamReader.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    fun readStarWarsPlayersJsonFileFromAssets(
        context: Context,
        fileName: String
    ) {
        try {
            val inputStream = context.assets.open(fileName)

            val inputStreamReader = InputStreamReader(inputStream)

            val gson = Gson()
            val jsonParser = JsonParser()
            val jsonElement: JsonElement = jsonParser.parse(inputStreamReader)

            if (jsonElement.isJsonArray) {
                val jsonArray: JsonArray = jsonElement.asJsonArray
                jsonArray.forEach { item ->
                    val model = gson.fromJson(item, StarWarsPlayersItem::class.java)
                    playerWithPointsHashMap[model.id] =
                        PlayerWithPoints(player = model)

                }
            }

            inputStreamReader.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
