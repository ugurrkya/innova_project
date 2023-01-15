package com.ugur.innovaproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ugur.innovaproject.adapter.TabbarPageAdapter
import com.ugur.innovaproject.databinding.ActivityMainBinding
import com.ugur.innovaproject.view.HomeFragment
import com.ugur.innovaproject.view.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    //    , LocationListener
    private lateinit var binding: ActivityMainBinding

    private lateinit var locationManager: LocationManager
    var mInst: MainActivity? = null
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mInst = this;
        initTabBar()

    }



    private fun initTabBar(){
        val tabBarAdapter = TabbarPageAdapter(this, tabLayout.tabCount)
        viewPagerTab.adapter = tabBarAdapter

        viewPagerTab.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerTab.currentItem = tab.position

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

    fun getMainInstance(): MainActivity? {
        return mInst
    }
}