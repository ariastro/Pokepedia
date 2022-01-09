package io.astronout.pokepedia.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.astronout.pokepedia.databinding.ItemPokemonStatsBinding
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.utils.capitalize
import io.astronout.pokepedia.utils.getColorResource

class PokemonStatsAdapter : ListAdapter<Pokemon.Stats, PokemonStatsAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonStatsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(private val itemBinding: ItemPokemonStatsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(stats: Pokemon.Stats) {
            with(itemBinding) {
                tvStatName.text = stats.name.getStatName().capitalize()
                tvStatValue.text = stats.baseStat.toString()
                progressStat.progress = stats.baseStat.toFloat()
                progressStat.highlightView.color = root.context.getColorResource(stats.color)
            }
        }
    }

    private fun String.getStatName(): String {
        return when(this) {
            "hp" -> "HP"
            "special-attack" -> "Sp. Atk"
            "special-defense" -> "Sp. Def"
            else -> this
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon.Stats>() {
            override fun areItemsTheSame(oldItem: Pokemon.Stats, newItem: Pokemon.Stats) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon.Stats, newItem: Pokemon.Stats) = oldItem == newItem
        }
    }

}