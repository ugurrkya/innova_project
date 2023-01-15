package com.ugur.innovaproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ugur.innovaproject.view.HomeFragment
import com.ugur.innovaproject.view.SettingsFragment

/**
 * Created by Uğur Kaya on 14.01.2023 for FlyBit.
 */
class TabbarPageAdapter(activity: FragmentActivity, private val tabCount: Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount


    override fun createFragment(position: Int): Fragment {
       return when(position) {
           0 -> HomeFragment()
           1 -> SettingsFragment()
           else -> HomeFragment()
       }
    }
}