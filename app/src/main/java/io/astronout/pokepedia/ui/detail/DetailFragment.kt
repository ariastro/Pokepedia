package io.astronout.pokepedia.ui.detail

import android.view.View
import android.view.animation.AlphaAnimation
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.*
import io.astronout.pokepedia.vo.Resource
import kotlin.math.abs

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val navController: NavController? by lazy { findNavController() }

    private var isTheTitleVisible = false
    private var isTheTitleContainerVisible = true

    override fun initUI() {
        with(binding) {
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
        // do nothing
    }

    override fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                navController?.navigateUp()
            }
        }
    }

    override fun initObserver() {
        collectLifecycleFlow(viewModel.getPokemonDetails(args.pokemon.id)) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> showToast("Loading")
                is Resource.Success -> {
                    it.data?.let { pokemon ->
                        showData(pokemon)
                    }
                }
            }
        }
    }

    private fun showData(pokemon: Pokemon) {
        with(binding) {
            tvPokemonIndex.text = pokemon.getIdString()
            tvPokemonName.text = pokemon.name.capitalize()
            tvToolbarTitle.text = pokemon.name.capitalize()
            tvPokemonNameBanner.text = pokemon.name
            cgPokemonType.apply {
                removeAllViews()
                pokemon.types.forEach {
                    cgPokemonType.addChip(it.name)
                }
            }
            GlideApp.with(this@DetailFragment)
                .load(pokemon.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgPokemon)
            pokemon.types.firstOrNull()?.let {
                changeStatusBarColor(it.name.getPokemonTypeBackground())
                root.setBackgroundResource(it.name.getPokemonTypeBackground())
                appBar.setBackgroundResource(it.name.getPokemonTypeBackground())
                tvPokemonNameBanner.setTextColorResource(it.name.getPokemonTypeBackground())
            }
        }
    }

    private fun setupViewPager() {
        with(binding) {
            viewPager.adapter = PagerAdapter(this@DetailFragment)
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
                startAlphaAnimation(
                    binding.tvToolbarTitle,
                    ALPHA_ANIMATIONS_DURATION,
                    View.INVISIBLE
                )
                isTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (isTheTitleContainerVisible) {
                startAlphaAnimation(
                    binding.containerPokemon,
                    ALPHA_ANIMATIONS_DURATION,
                    View.INVISIBLE
                )
                isTheTitleContainerVisible = false
            }
        } else {
            if (!isTheTitleContainerVisible) {
                startAlphaAnimation(
                    binding.containerPokemon,
                    ALPHA_ANIMATIONS_DURATION,
                    View.VISIBLE
                )
                isTheTitleContainerVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation =
            if (visibility == View.VISIBLE) AlphaAnimation(0F, 1F) else AlphaAnimation(1f, 0f)
        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    companion object {
        const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.4F
        const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.2F
        const val ALPHA_ANIMATIONS_DURATION = 200L
    }

}