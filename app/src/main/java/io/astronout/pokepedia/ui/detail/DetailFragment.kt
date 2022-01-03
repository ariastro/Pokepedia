package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentDetailBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.utils.onClick
import io.astronout.pokepedia.utils.showToast

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()

    override fun initUI() {
        with(binding) {
            tvPokemonIndex.text = "#001"
            tvPokemonName.text = "Bulbasaur"
            GlideApp.with(this@DetailFragment)
                .load(R.drawable.bulbasaur)
                .into(imgPokemon)
        }
    }

    override fun initData() {
        // do nothing
    }

    override fun initAction() {
        with(binding) {
            tvPokemonName.onClick {
                showToast("bulbasaur")
            }
        }
    }

    override fun initObserver() {
        // do nothing
    }

}