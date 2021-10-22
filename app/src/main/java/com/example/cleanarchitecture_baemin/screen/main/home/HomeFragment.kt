package com.example.cleanarchitecture_baemin.screen.main.home

import com.example.cleanarchitecture_baemin.databinding.FragmentHomeBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    companion object {

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }
}