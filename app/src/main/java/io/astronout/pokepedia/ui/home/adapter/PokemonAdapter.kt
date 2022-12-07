package io.astronout.pokepedia.ui.home.adapter

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.ItemPokemonBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.utils.capitalize
import io.astronout.pokepedia.utils.getColorResource

class PokemonAdapter(private val onClickListener: (pokemon: Pokemon, itemPokemonBinding: ItemPokemonBinding) -> Unit) :
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
                tvPokemonIndex.text = pokemon.getIdString()
                tvPokemonName.text = pokemon.name.capitalize()
                imgPokemon.transitionName = pokemon.name
                loadImage(pokemon.image)
                root.setOnClickListener {
                    onClickListener(pokemon, this)
                }
            }
        }
    }

    private fun ItemPokemonBinding.loadImage(imageUrl: String) {
        GlideApp.with(root)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val drawable = resource as BitmapDrawable
                    val bitmap = drawable.bitmap
                    Palette.Builder(bitmap).generate {
                        it?.let { palette ->
                            val dominantColor = palette.getDominantColor(
                                root.context.getColorResource(R.color.light)
                            )
                            cardPokemon.setCardBackgroundColor(dominantColor)
                        }
                    }
                    return false
                }

            })
            .into(imgPokemon)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
        }
    }

}