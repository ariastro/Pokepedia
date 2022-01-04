package io.astronout.pokepedia.ui.detail

import android.view.View
import android.view.animation.AlphaAnimation
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentDetailBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.adapter.PagerAdapter
import io.astronout.pokepedia.utils.changeStatusBarColor
import io.astronout.pokepedia.utils.onClick
import io.astronout.pokepedia.utils.showToast
import kotlin.math.abs

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var pokemon: Pokemon

    private var isTheTitleVisible = false
    private var isTheTitleContainerVisible = true

    override fun initUI() {
        with(binding) {
            changeStatusBarColor(R.color.background_type_grass)
            startAlphaAnimation(tvToolbarTitle, 0, View.INVISIBLE)
            appBar.addOnOffsetChangedListener((AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val maxScroll = appBarLayout.totalScrollRange
                val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
                handleAlphaOnTitle(percentage)
                handleToolbarTitleVisibility(percentage)
            }))
            setupViewPager()
        }
    }

    override fun initData() {
        with(binding) {
            pokemon = args.pokemon
            tvPokemonIndex.text = pokemon.getIdString()
            tvPokemonName.text = pokemon.name
            GlideApp.with(this@DetailFragment)
                .load(pokemon.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgPokemon)
        }
    }

    override fun initAction() {
        with(binding) {

        }
    }

    override fun initObserver() {
        // do nothing
    }

    private fun setupViewPager() {
        with(binding) {
            viewPager.adapter = PagerAdapter(this@DetailFragment)
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.about)
                    1 -> tab.text = getString(R.string.stats)
                    2 -> tab.text = getString(R.string.evolution)
                }
            }.attach()
        }
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!isTheTitleVisible) {
                startAlphaAnimation(binding.tvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isTheTitleVisible = true
            }
        } else {
            if (isTheTitleVisible) {
                startAlphaAnimation(binding.tvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (isTheTitleContainerVisible) {
                startAlphaAnimation(binding.containerPokemon, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isTheTitleContainerVisible = false
            }
        } else {
            if (!isTheTitleContainerVisible) {
                startAlphaAnimation(binding.containerPokemon, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isTheTitleContainerVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE) AlphaAnimation(0F, 1F) else AlphaAnimation(1f, 0f)
        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    companion object {
        const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.4F
        const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3F
        const val ALPHA_ANIMATIONS_DURATION = 200L
    }

}