package io.astronout.pokepedia.ui.detail

import android.view.View
import android.view.animation.AlphaAnimation
import android.viewbinding.library.fragment.viewBinding
import com.google.android.material.appbar.AppBarLayout
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentDetailBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.utils.changeStatusBarColor
import io.astronout.pokepedia.utils.onClick
import io.astronout.pokepedia.utils.showToast
import kotlin.math.abs

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()

    private var isTheTitleVisible = false
    private var isTheTitleContainerVisible = true

    companion object {
        const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6F
        const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3F
        const val ALPHA_ANIMATIONS_DURATION = 200L
    }


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

}