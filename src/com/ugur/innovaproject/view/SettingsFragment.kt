package com.ugur.innovaproject.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ugur.innovaproject.AssistantFunctions
import com.ugur.innovaproject.R
import com.ugur.innovaproject.databinding.FragmentSettingsBinding
import com.ugur.innovaproject.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(), LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    var assistantFunctions: AssistantFunctions? = null
    var activity: Activity? = null
    lateinit var settingsViewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        assistantFunctions = AssistantFunctions(activity)



        getActivity()?.let { getActivity()?.let { it1 -> assistantFunctions!!.AssistantFunctions(it1) }

            binding.gpsButton.isChecked = getActivity()?.resources?.getString(R.string.gps_control)
                ?.let { it1 -> assistantFunctions!!.getGeneralInfo(it1) } == getActivity()?.resources?.getString(R.string.gps_enabled)
        }





        binding.gpsButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), resources.getString(R.string.gps_enabled))
            }else{
                assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), "")
            }
        }





        if((context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED)){
            locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10000f,
                this as LocationListener
            )
            assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), resources.getString(R.string.gps_enabled))
            binding.gpsButton.isChecked = true
        }

        val locationOpenRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(locationEnabled()){
                locationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    10000f,
                    this as LocationListener
                )


                assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), resources.getString(R.string.gps_enabled))
                binding.gpsButton.isChecked = true

            }
        }


        val locationPermissionRequest = registerForActivityResult(RequestMultiplePermissions()
        ) { result: Map<String?, Boolean?> ->
            val fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false

            )
            val coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false
            )
            if (fineLocationGranted != null && fineLocationGranted) {


                if (!locationEnabled()){
                    val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    locationOpenRequest.launch(
                       intent
                    )

                }
                locationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    10000f,
                    this as LocationListener
                )

                if(locationEnabled()){
                    assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), resources.getString(R.string.gps_enabled))
                    binding.gpsButton.isChecked = true
                }



                // Precise location access granted.
            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                // Only approximate location access granted.
            } else {
                // No location access granted.
            }
        }


        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )





        return binding.root

    }



    private fun locationEnabled() : Boolean {
        val locationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }




    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lng = location.longitude

        val geoCoder = Geocoder(getActivity(),Locale.getDefault())
        try {
            val address: List<Address> = geoCoder.getFromLocation(lat, lng, 1)

            val addressStr: String = address[0].adminArea


            try{
                settingsViewModel.updateCity(addressStr,1)


            }catch (e: Exception){
                e.printStackTrace()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }


    }


    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionCode) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                     if (
                             activity?.let {
                                 ContextCompat.checkSelfPermission(
                                     it,
                                     Manifest.permission.ACCESS_FINE_LOCATION
                                 )
                             }
                          != PackageManager.PERMISSION_GRANTED &&
                             activity?.let {
                                 ContextCompat.checkSelfPermission(
                                     it,
                                     Manifest.permission.ACCESS_COARSE_LOCATION
                                 )
                             }
                          != PackageManager.PERMISSION_GRANTED
                     ) {

                         return
                     }

                locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    this as LocationListener
                )
                    assistantFunctions!!.updateGeneralInfo(resources.getString(R.string.gps_control), resources.getString(R.string.gps_enabled))
                    binding.gpsButton.isChecked = true


            }
            else {
            }
        }



    }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }



}