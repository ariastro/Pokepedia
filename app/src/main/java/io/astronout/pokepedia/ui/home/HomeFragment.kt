package io.astronout.pokepedia.ui.home

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.kennyc.view.MultiStateView
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentHomeBinding
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.home.adapter.LoadStateAdapter
import io.astronout.pokepedia.ui.home.adapter.PokemonAdapter
import io.astronout.pokepedia.utils.changeStatusBarColor
import io.astronout.pokepedia.utils.collectLatestLifecycleFlow
import io.astronout.pokepedia.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val navController: NavController? by lazy { findNavController() }

    private val adapter = PokemonAdapter(onClickListener = {
        navController?.navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
    })

    override fun initData() {
        setupPokemonList()
    }

    override fun initUI() {
        changeStatusBarColor(R.color.light)
    }

    private fun setupPokemonList() {
        with(binding) {
            val footer = LoadStateAdapter { adapter.retry() }
            rvPokemon.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { adapter.retry() },
                footer = footer
            )

            lifecycleScope.launch {
                adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for the paging source changes.
                    .distinctUntilChangedBy { it.source.refresh }
                    // Only react to cases where REFRESH completes i.e., NotLoading.
                    .map { it.source.refresh is LoadState.NotLoading }
                    .collectLatest { rvPokemon.scrollToPosition(0) }
            }

            collectLatestLifecycleFlow(viewModel.pokemonList, adapter::submitData)

            adapter.addLoadStateListener { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
//                showEmptyState(isListEmpty)

                footer.loadState =
                    loadState.mediator?.refresh?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                        ?: loadState.prepend
                msvPokemon.viewState = if (loadState.source.refresh is LoadState.Loading) MultiStateView.ViewState.LOADING else MultiStateView.ViewState.CONTENT
//            binding.tvRetry.isVisible = loadState.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    showToast("\uD83D\uDE28 Wooops ${it.error}")
                }
            }
        }
    }

}