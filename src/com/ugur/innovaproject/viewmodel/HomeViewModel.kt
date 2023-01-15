package com.ugur.innovaproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugur.innovaproject.model.CityResponse
import com.ugur.innovaproject.model.CityUpdateModel
import com.ugur.innovaproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    var cities: MutableLiveData<List<CityResponse>> = MutableLiveData<List<CityResponse>>()


    init{

    }



    fun getCities() {


            RetrofitClient().getApi().getCityList(

            )?.enqueue(object : Callback<List<CityResponse>?> {
                override fun onResponse(
                    call: Call<List<CityResponse>?>,
                    response: Response<List<CityResponse>?>
                ) {
                    if (response.isSuccessful) {


                        response.body()?.let { resultResponse ->
                            var filteredList = resultResponse.filter { x -> x.status == 1 }
                           cities.value = filteredList.toMutableList()



                        }


                    } else {


                    }


                }

                override fun onFailure(call: Call<List<CityResponse>?>, t: Throwable) {


                }

            })

        // }
    }
    fun observeCities(): LiveData<List<CityResponse>> {
        return cities
    }


}