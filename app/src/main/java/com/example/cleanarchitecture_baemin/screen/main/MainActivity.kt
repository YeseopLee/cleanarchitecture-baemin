package com.example.cleanarchitecture_baemin.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.databinding.ActivityMainBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseActivity
import com.example.cleanarchitecture_baemin.screen.main.home.HomeFragment
import com.example.cleanarchitecture_baemin.screen.main.like.RestaurantLikeListFragment
import com.example.cleanarchitecture_baemin.screen.main.my.MyFragment
import com.example.cleanarchitecture_baemin.util.event.MenuChangeEventBus
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

//    override val viewModel by viewModel<MainViewModel>()
//
//    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        initViews()
    }

    private fun observeData() {
        lifecycleScope.launch {
            menuChangeEventBus.mainTabMenuFlow.collect {
                goToTab(it)
            }
        }
    }

    private fun initViews() = with(binding) {
        initBottomNav()
        showFragment(HomeFragment.newInstance(),HomeFragment.TAG)
    }

    private fun initBottomNav() = with(binding) {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    showFragment(HomeFragment.newInstance(),HomeFragment.TAG)
                    true
                }
                R.id.menu_like -> {
                    showFragment(RestaurantLikeListFragment.newInstance(), RestaurantLikeListFragment.TAG)
                    true
                }

                R.id.menu_my -> {
                    showFragment(MyFragment.newInstance(),MyFragment.TAG)
                    true
                }
                else -> false
            }

        }
    }

    fun goToTab(mainTabMenu: MainTabMenu) {
        binding.bottomNav.selectedItemId = mainTabMenu.menuId
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

}


enum class MainTabMenu(@IdRes val menuId: Int) {
    HOME(R.id.menu_home), LIKE(R.id.menu_like), MY(R.id.menu_my)
}