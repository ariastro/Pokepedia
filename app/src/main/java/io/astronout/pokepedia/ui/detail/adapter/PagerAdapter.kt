package io.astronout.pokepedia.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return Fragment()
//        return when (position) {
//            0 -> HomeFragment()
//            1-> HomeFragment()
//            else -> HomeFragment()
//        }
    }
}