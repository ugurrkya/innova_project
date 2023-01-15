package com.ugur.innovaproject.model

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse (
    @SerializedName("main"       ) var main       : Main?              = Main(),
    @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf(),
)