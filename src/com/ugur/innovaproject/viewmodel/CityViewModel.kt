package com.ugur.innovaproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugur.innovaproject.model.CityResponse
import com.ugur.innovaproject.model.CityUpdateModel
import com.ugur.innovaproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityViewModel() : ViewModel() {
    var cities: MutableLiveData<List<CityResponse>> = MutableLiveData<List<CityResponse>>()


    init{

    }



    fun updateCity(city: String, status: Int){

        val cityUpdateModel = CityUpdateModel(status);
        RetrofitClient().getApi().updateCity(
            city,cityUpdateModel
        )?.enqueue(object : Callback<CityResponse?> {
            override fun onResponse(
                call: Call<CityResponse?>,
                response: Response<CityResponse?>
            ) {
                if (response.isSuccessful) {


                    response.body()?.let { resultResponse ->


                    }


                } else {


                }


            }

            override fun onFailure(call: Call<CityResponse?>, t: Throwable) {


            }

        })

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


                           cities.value = resultResponse.toMutableList()



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