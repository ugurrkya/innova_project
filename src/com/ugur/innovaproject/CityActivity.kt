package com.ugur.innovaproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ugur.innovaproject.adapter.CitiesAdapter
import com.ugur.innovaproject.databinding.ActivityCityBinding
import com.ugur.innovaproject.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.city_layout.view.*


class CityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityBinding
    lateinit var cityViewModel: CityViewModel
    lateinit var cityAdapter: CitiesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initRecycler()

        cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]


        cityViewModel.observeCities().observe(this) {

            cityViewModel.cities.value?.let { it1 ->

                it?.let { cityResponse ->


                    cityAdapter.differ.submitList(cityResponse.toList())

                }
            }
        }

        try{
            cityViewModel.getCities()
        }catch (e: Exception){
            e.printStackTrace()
        }





        cityAdapter.setOnCheckBoxChangeListener {
            if(it.status == 1){
                it.id?.let { it1 -> cityViewModel.updateCity(it1.toString(), 0)
                    it.status = 0};
            }else{
                it.id?.let { it1 -> cityViewModel.updateCity(it1.toString(), 1)
                    it.status = 1};

            }
        }


    }

    private fun initRecycler() {
        cityAdapter = CitiesAdapter()
        cityRecycler.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(this@CityActivity)
        }
    }

}