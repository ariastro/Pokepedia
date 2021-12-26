package io.astronout.pokepedia.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.astronout.pokepedia.databinding.ItemPokemonBinding
import io.astronout.pokepedia.di.GlideApp

class PokemonAdapter(private val onClickListener: () -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

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
        holder.bind()
    }

    override fun getItemCount() = 10

    inner class PokemonViewHolder(private val itemBinding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind() {
            with(itemBinding) {
                GlideApp.with(itemBinding.root.context)
                    .load(R.drawable.bulbasaur)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(itemBinding.imgPokemon)
                cardPokemon.setCardBackgroundColorResource(R.color.background_type_grass)
                tvPokemonIndex.text = "#001"
                tvPokemonName.text = "Bulbasaur"
                root.setOnClickListener {
                    onClickListener()
                }
            }
        }
    }

}