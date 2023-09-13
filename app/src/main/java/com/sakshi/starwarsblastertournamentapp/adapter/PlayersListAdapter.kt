package com.sakshi.starwarsblastertournamentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsblastertournamentapp.databinding.PlayersItemLayoutBinding
import com.sakshi.starwarsblastertournamentapp.loadImage
import com.sakshi.starwarsblastertournamentapp.models.PlayerWithPoints
import com.sakshi.starwarsblastertournamentapp.JsonFileReader

class PlayersListAdapter() :
    androidx.recyclerview.widget.ListAdapter<Pair<Int, PlayerWithPoints>, PlayersListAdapter.PlayersViewHolder>(
        object : DiffUtil.ItemCallback<Pair<Int, PlayerWithPoints>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Int, PlayerWithPoints>,
                newItem: Pair<Int, PlayerWithPoints>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Pair<Int, PlayerWithPoints>,
                newItem: Pair<Int, PlayerWithPoints>
            ): Boolean {
                return oldItem.first == newItem.first
            }
        }
    ) {

    lateinit var listener: ((view: View, item: Pair<Int, PlayerWithPoints>, position: Int) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val binding = PlayersItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class PlayersViewHolder(private val viewBinding: PlayersItemLayoutBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Pair<Int, PlayerWithPoints>, position: Int) {
            bind(viewBinding, item, adapterPosition)
        }
    }

    fun bind(
        viewBinding: PlayersItemLayoutBinding,
        item: Pair<Int, PlayerWithPoints>,
        position: Int
    ) {
        viewBinding.apply {
            name.text = item.second.player.name
            points.text = item.second.points.toString()

            image.loadImage(item.second.player.icon)

            root.setOnClickListener {
                listener.invoke(it, item, position)
            }
        }
    }

}