package com.ugur.innovaproject.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Uğur Kaya on 14.01.2023 for FlyBit.
 */
data class CityUpdateModel(@SerializedName("status" ) @Expose var status : Int?    = null)




