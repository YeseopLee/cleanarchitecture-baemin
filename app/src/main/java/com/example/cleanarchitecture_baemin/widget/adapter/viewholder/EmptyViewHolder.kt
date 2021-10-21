package com.example.cleanarchitecture_baemin.widget.adapter.viewholder

import com.example.cleanarchitecture_baemin.databinding.ViewholderEmptyBinding
import com.example.cleanarchitecture_baemin.model.Model
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener

class EmptyViewHolder(
    private val binding:ViewholderEmptyBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<Model>(binding, viewModel, resourcesProvider) {
    override fun reset() = Unit

    override fun bindViews(model: Model, adapterListener: AdapterListener) = Unit
}