package io.astronout.pokepedia.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {

    open fun initData() {}
    open fun initUI() {}
    open fun initAction() {}
    open fun initObserver() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initUI()
        initAction()
        initObserver()

    }

}