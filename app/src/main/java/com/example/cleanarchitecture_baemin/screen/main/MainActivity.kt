package com.example.cleanarchitecture_baemin.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.databinding.ActivityMainBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseActivity
import com.example.cleanarchitecture_baemin.screen.main.home.HomeFragment
import com.example.cleanarchitecture_baemin.screen.main.my.MyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by viewModel<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    override fun initViews() = with(binding) {
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
                R.id.menu_my -> {
                    showFragment(MyFragment.newInstance(),MyFragment.TAG)
                    true
                }
                else -> false
            }

        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun observeData() {

    }
}