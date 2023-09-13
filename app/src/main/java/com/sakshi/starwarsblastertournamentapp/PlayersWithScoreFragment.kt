package com.sakshi.starwarsblastertournamentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.starwarsblastertournamentapp.R
import com.example.starwarsblastertournamentapp.databinding.FragmentPlayersWithScoreBinding
import com.google.gson.Gson
import com.sakshi.starwarsblastertournamentapp.adapter.PlayersListAdapter


class PlayersWithScoreFragment : Fragment() {


    private var binding: FragmentPlayersWithScoreBinding? = null

    private var playersAdapter: PlayersListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentPlayersWithScoreBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playersAdapter = PlayersListAdapter().apply {
            JsonFileReader.playersList.let {
                submitList(it)
            }
        }

        binding?.playersListAdapter = playersAdapter

        playersAdapter?.listener = { view, item, position ->
            findNavController().navigate(R.id.playersDashBoard, Bundle().apply {
                putString(
                    "matchesArray", Gson().toJson(
                        JsonFileReader.playerWithMatchesHashMap[item.first]
                    )
                )

                putString("currentPlayerId", item.first.toString())
            })
        }
    }

}