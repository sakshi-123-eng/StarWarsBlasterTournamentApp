package com.sakshi.starwarsblastertournamentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsblastertournamentapp.R
import com.example.starwarsblastertournamentapp.databinding.MatchesItemLayoutBinding
import com.sakshi.starwarsblastertournamentapp.JsonFileReader
import com.sakshi.starwarsblastertournamentapp.models.PlayerWithPoints
import com.sakshi.starwarsblastertournamentapp.models.StarWarsMatchesItem


class MatchesListAdapter() :
    androidx.recyclerview.widget.ListAdapter<StarWarsMatchesItem, MatchesListAdapter.MatchesViewHolder>(
        object : DiffUtil.ItemCallback<StarWarsMatchesItem>() {
            override fun areItemsTheSame(
                oldItem: StarWarsMatchesItem,
                newItem: StarWarsMatchesItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StarWarsMatchesItem,
                newItem: StarWarsMatchesItem
            ): Boolean {
                return oldItem.player1.id == newItem.player1.id && oldItem.player2.id == newItem.player2.id
            }
        }
    ) {

    lateinit var listener: ((view: View, item: PlayerWithPoints, position: Int) -> Unit)

    var currentId = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val binding = MatchesItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class MatchesViewHolder(private val viewBinding: MatchesItemLayoutBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: StarWarsMatchesItem, position: Int) {
            bind(viewBinding, item, adapterPosition)
        }
    }

    fun bind(viewBinding: MatchesItemLayoutBinding, item: StarWarsMatchesItem, position: Int) {
        viewBinding.apply {
            nameP1.text = JsonFileReader.playerWithPointsHashMap[item.player1.id]?.player?.name
            nameP2.text = JsonFileReader.playerWithPointsHashMap[item.player2.id]?.player?.name

            score.text = "${item.player1.score} - ${item.player2.score}"

            if (item.player1.id == currentId && item.player1.score > item.player2.score) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.green))
            } else if (item.player2.id == currentId && item.player2.score > item.player1.score) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.green))
            } else if (item.player1.id == currentId && item.player1.score < item.player2.score) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.red))
            } else if (item.player2.id == currentId && item.player2.score < item.player1.score) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.red))
            } else if (item.player1.id == currentId && item.player1.score == item.player2.score) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.white))
            }

        }
    }
}