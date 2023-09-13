package com.sakshi.starwarsblastertournamentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.starwarsblastertournamentapp.R
import com.sakshi.starwarsblastertournamentapp.models.StarWarsMatchesItem
import com.sakshi.starwarsblastertournamentapp.models.StarWarsPlayersItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.let {
            JsonFileReader.readStarWarsPlayersJsonFileFromAssets(
                it,
                "StarWarsPlayers.json"
            )

            JsonFileReader.readStarWarsMatchesJsonFileFromAssets(
                it,
                "StarWarsMatches.json"
            )
        }
    }
}