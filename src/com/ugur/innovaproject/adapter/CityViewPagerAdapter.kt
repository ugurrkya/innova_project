package com.ugur.innovaproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.ugur.innovaproject.R
import com.ugur.innovaproject.model.CityResponse
import com.ugur.innovaproject.model.WeatherApiResponse
import com.ugur.innovaproject.network.RetrofitClientWeather
import kotlinx.android.synthetic.main.weather_data_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityViewPagerAdapter(val context: Context) : PagerAdapter() {
    lateinit var cities: List<CityResponse>
    var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return cities.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.weather_data_layout, container, false)

        view.cityName.text = String.format(
            context.resources.getString(R.string.city_desc),
            cities.get(position).name)



        cities.get(position).name?.let {
            RetrofitClientWeather().getApi().getWeatherData(
                it, context.resources.getString(R.string.weather_api)
            )?.enqueue(object : Callback<WeatherApiResponse?> {
                override fun onResponse(
                    call: Call<WeatherApiResponse?>,
                    response: Response<WeatherApiResponse?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultResponse ->
                            view.humidity.text = String.format(
                                context.resources.getString(R.string.humidity_text),
                                resultResponse.main?.humidity.toString())
                            view.degreeArea.text = String.format(
                                context.resources.getString(R.string.temp_min_max),
                                resultResponse.main?.tempMin.toString(), resultResponse.main?.tempMax.toString() )
                            view.situation.text = String.format(
                                context.resources.getString(R.string.situation_text),
                                resultResponse.weather.get(0).description)
                            view.pressure.text = String.format(
                                context.resources.getString(R.string.pressure_text),
                                resultResponse.main?.pressure.toString())
                        }


                    } else {


                    }


                }

                override fun onFailure(call: Call<WeatherApiResponse?>, t: Throwable) {


                }

            })
        }


        container.addView(view)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }


}