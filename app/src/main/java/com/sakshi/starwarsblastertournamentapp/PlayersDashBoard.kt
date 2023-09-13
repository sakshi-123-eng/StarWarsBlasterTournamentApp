package com.sakshi.starwarsblastertournamentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.starwarsblastertournamentapp.databinding.FragmentPlayersDashBoardBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sakshi.starwarsblastertournamentapp.adapter.MatchesListAdapter
import com.sakshi.starwarsblastertournamentapp.models.StarWarsMatchesItem

class PlayersDashBoard : Fragment() {

    private var binding: FragmentPlayersDashBoardBinding? = null

    private var matchesAdapter: MatchesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentPlayersDashBoardBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val matchesArrayRaw = arguments?.getString("matchesArray")
        val token = object : TypeToken<ArrayList<StarWarsMatchesItem>>() {}.type
        var matchesArray = Gson().fromJson<ArrayList<StarWarsMatchesItem>>(matchesArrayRaw, token)


        val id = arguments?.getString("currentPlayerId")
        val idInt = id?.toInt()

        matchesAdapter = MatchesListAdapter().apply {
            idInt?.let { currentId = it }
            submitList(matchesArray)
        }

        binding?.matchesListAdapter = matchesAdapter

        binding?.toolbar?.setTitle(JsonFileReader.playerWithPointsHashMap[idInt]?.player?.name)
        binding?.toolbar?.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}