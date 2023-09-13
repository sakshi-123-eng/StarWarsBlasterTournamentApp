package com.sakshi.starwarsblastertournamentapp.models

data class StarWarsMatchesItem(
    val match: Int,
    val player1: StarWarsMatchesPlayer,
    val player2: StarWarsMatchesPlayer
)