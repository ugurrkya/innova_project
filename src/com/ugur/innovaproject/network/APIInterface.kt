package com.ugur.innovaproject.network

import com.ugur.innovaproject.model.CityResponse
import com.ugur.innovaproject.model.CityUpdateModel
import com.ugur.innovaproject.model.WeatherApiResponse
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {
    /**
     * retrieve city list
     */
    @GET("customerhistory")
    fun getCityList(): Call<List<CityResponse>>?


    @GET("customerhistory")
    fun getCity(@Query("name") name:String): Call<List<CityResponse>>?

    @PUT("customerhistory/{id}")
    fun updateCity(@Path("id") id: String, @Body updateData: CityUpdateModel ): Call<CityResponse>?

    @GET("weather")
    fun getWeatherData(@Query("q") q:String, @Query("appid") appid: String): Call<WeatherApiResponse>?

}