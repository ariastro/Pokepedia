package io.astronout.pokepedia.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.astronout.pokepedia.ui.detail.AboutFragment
import io.astronout.pokepedia.ui.detail.EvolutionFragment
import io.astronout.pokepedia.ui.detail.StatsFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutFragment()
            1 -> StatsFragment()
            2 -> EvolutionFragment()
            else -> AboutFragment()
        }
    }
}