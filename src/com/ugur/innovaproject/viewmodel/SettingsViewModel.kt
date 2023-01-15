package com.ugur.innovaproject.viewmodel

import androidx.lifecycle.ViewModel
import com.ugur.innovaproject.model.CityResponse
import com.ugur.innovaproject.model.CityUpdateModel
import com.ugur.innovaproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsViewModel: ViewModel() {


    init {

    }
    fun updateCity(city: String, status: Int){




        val cityUpdateModel = CityUpdateModel(status);


        RetrofitClient().getApi().getCity(
            city
        )?.enqueue(object : Callback<List<CityResponse>?> {
            override fun onResponse(
                call: Call<List<CityResponse>?>,
                response: Response<List<CityResponse>?>
            ) {


                if (response.isSuccessful) {




                    response.body()?.let { resultResponse ->


                        RetrofitClient().getApi().updateCity(
                            resultResponse.get(0).id.toString(),cityUpdateModel
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


                } else {


                }


            }

            override fun onFailure(call: Call<List<CityResponse>?>, t: Throwable) {


            }

        })



    }
}