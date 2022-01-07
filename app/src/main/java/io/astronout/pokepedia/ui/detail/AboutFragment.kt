package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentAboutBinding
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.utils.getColoredString

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding: FragmentAboutBinding by viewBinding()

    override fun initData() {
        // do nothing
    }

    override fun initUI() {
        with(binding) {
            tvGender.append(requireContext().getColoredString("♂ 87.5%, ", R.color.height_medium))
            tvGender.append(requireContext().getColoredString("♀ 12.5%", R.color.height_short))
        }
    }

    override fun initAction() {
        // do nothing
    }

    override fun initObserver() {
        // do nothing
    }


}