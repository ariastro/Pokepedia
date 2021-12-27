package io.astronout.pokepedia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.ItemPokemonBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.utils.capitalize
import io.astronout.pokepedia.utils.setCardBackgroundColorResource
import java.util.*

class PokemonAdapter(private val onClickListener: () -> Unit) :
    PagingDataAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PokemonViewHolder(private val itemBinding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pokemon: Pokemon) {
            with(itemBinding) {
                cardPokemon.setCardBackgroundColorResource(R.color.background_type_grass)
                GlideApp.with(itemBinding.root.context)
                    .load(pokemon.image)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(itemBinding.imgPokemon)
                tvPokemonIndex.text = pokemon.getIdString()
                tvPokemonName.text = pokemon.name.capitalize()
                root.setOnClickListener {
                    onClickListener()
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
        }
    }

}