package com.example.cleanarchitecture_baemin.screen.order

import android.os.Bundle
import com.example.cleanarchitecture_baemin.databinding.ActivityOrderMenuListBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class OrderMenuListActivity : BaseActivity<OrderMenuListViewModel,ActivityOrderMenuListBinding>() {

    override val viewModel by viewModel<OrderMenuListViewModel>()

    override fun getViewBinding(): ActivityOrderMenuListBinding = ActivityOrderMenuListBinding.inflate(layoutInflater)

    override fun observeData() {
    }
}