package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantEntity
import com.example.cleanarchitecture_baemin.databinding.ActivityRestaurantDetailBinding
import com.example.cleanarchitecture_baemin.extensions.fromDpToPx
import com.example.cleanarchitecture_baemin.extensions.load
import com.example.cleanarchitecture_baemin.screen.base.BaseActivity
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListFragment
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Math.abs


class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel,ActivityRestaurantDetailBinding>() {

    override fun getViewBinding(): ActivityRestaurantDetailBinding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
    override val viewModel by viewModel<RestaurantDetailViewModel> {
        parametersOf(
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    companion object {
        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) = Intent(context, RestaurantDetailActivity::class.java).apply {
            putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
        }

    }

    override fun initViews() {
        initAppBar()
    }

    private fun initAppBar() = with(binding) {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{ appBarLayout, verticalOffset ->
            val topPadding = 300f.fromDpToPx().toFloat()
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticaloffset: Float = if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding

            if(abstractOffset < topPadding) {
                restaurantTitleTextView.alpha = 0f
                return@OnOffsetChangedListener
            }

            val percentage = realAlphaVerticaloffset / realAlphaScrollHeight
            restaurantTitleTextView.alpha = 1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2)

        })
        toolbar.setNavigationOnClickListener { finish() }
        callButton.setOnClickListener {
            viewModel.getRestaurantTelNumber()?.let { telNumber ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telNumber"))
                startActivity(intent)
            }
        }
        likeButton.setOnClickListener {
            viewModel.toggleLikedRestaurant()
        }
        shareButton.setOnClickListener {
            viewModel.getRestaurantInfo()?.let { restaurantInfo ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = MIMETYPE_TEXT_PLAIN
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "식당 이름: ${restaurantInfo.restaurantTitle}" +
                                "\n 평점: ${restaurantInfo.grade}" +
                                "\n 연락처: ${restaurantInfo.restaurantTelNumber}"
                    )
                    Intent.createChooser(this, "친구에게 공유하기")
                }
                startActivity(intent)
            }
        }
    }

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this) {
        when (it) {
            is RestaurantDetailState.Success -> {
                handleSuccess(it)
            }
        }
    }

    private fun handleSuccess(state: RestaurantDetailState.Success) = with(binding) {
        val restaurantEntity = state.restaurantEntity

        callButton.isGone = restaurantEntity.restaurantTelNumber == null
        restaurantTitleTextView.text = restaurantEntity.restaurantTitle
        restaurantImage.load(restaurantEntity.restaurantImageUrl)
        restaurantMainTitleTextView.text = restaurantEntity.restaurantTitle
        ratingBar.rating = restaurantEntity.grade
        deliveryTimeText.text =
            getString(R.string.delivery_expected_time, restaurantEntity.deliveryTimeRange.first, restaurantEntity.deliveryTimeRange.second)
        deliveryTipText.text =
            getString(R.string.delivery_tip_range, restaurantEntity.deliveryTipRange.first, restaurantEntity.deliveryTipRange.second)

        likeText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(this@RestaurantDetailActivity, if (state.isLiked == true) {
                R.drawable.ic_heart_enable
            } else {
                R.drawable.ic_heart_disable
            }),
            null, null, null
        )
    }

}