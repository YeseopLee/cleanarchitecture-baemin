package com.example.cleanarchitecture_baemin.screen.main.my

import com.example.cleanarchitecture_baemin.databinding.FragmentHomeBinding
import com.example.cleanarchitecture_baemin.databinding.FragmentMyBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import com.example.cleanarchitecture_baemin.screen.main.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel


class MyFragment: BaseFragment<MyViewModel, FragmentMyBinding>() {


    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    companion object {

        fun newInstance() = MyFragment()

        const val TAG = "MyFragment"
    }
}