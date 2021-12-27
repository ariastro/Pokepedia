package io.astronout.pokepedia.ui.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentHomeBinding
import io.astronout.pokepedia.ui.home.adapter.LoadStateAdapter
import io.astronout.pokepedia.ui.home.adapter.PokemonAdapter
import io.astronout.pokepedia.utils.collectLatestLifecycleFlow
import io.astronout.pokepedia.utils.collectLifecycleFlow
import io.astronout.pokepedia.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = PokemonAdapter(onClickListener = {

    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPokemonList()

    }

    private fun setupPokemonList() {
        with(binding) {
            rvPokemon.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { adapter.retry() },
                footer = LoadStateAdapter { adapter.retry() }
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

            collectLifecycleFlow(adapter.loadStateFlow) { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // show empty list
//                showEmptyState(isListEmpty)
                msvPokemon.viewState = if (loadState.source.refresh is LoadState.Loading) MultiStateView.ViewState.LOADING else MultiStateView.ViewState.CONTENT
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
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