package com.ugur.innovaproject.view

import android.content.Context
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.ugur.innovaproject.CityActivity
import com.ugur.innovaproject.R
import com.ugur.innovaproject.adapter.CityViewPagerAdapter
import com.ugur.innovaproject.databinding.FragmentHomeBinding
import com.ugur.innovaproject.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cityViewPagerAdapter: CityViewPagerAdapter
    lateinit var homeViewModel: HomeViewModel
    var isVisibleUser = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentHomeBinding.inflate(inflater, container, false)


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.observeCities().observe(viewLifecycleOwner) {

            homeViewModel.cities.value?.let { it1 ->

                it?.let { cityResponse ->

                    cityViewPagerAdapter = context?.let { it2 -> CityViewPagerAdapter(it2) }!!
                    cityViewPagerAdapter.cities = cityResponse

                    viewPagerHome.apply {
                        adapter = cityViewPagerAdapter

                    }
                }
            }
        }

        homeViewModel.getCities()
        val addNewCityOperation = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            homeViewModel.getCities()
        }
        binding.floatingButton.setOnClickListener {
            val intent = Intent(activity, CityActivity::class.java)
            addNewCityOperation.launch(
                intent
            )
        }





        binding.viewPagerHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


        return binding.root
    }






}