package com.ugur.innovaproject

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


/**
 * Created by Uğur Kaya on 14.01.2023 for FlyBit.
 */
class AssistantFunctions(var activity: Activity?) {

    var sha: SharedPreferences? = null

    fun AssistantFunctions(ac: Activity) {
        activity = ac
        val app: String
        app = if (ac.applicationContext != null) {
            ac.applicationContext.packageName
        } else {
            ac.packageName
        }
        sha = ac.applicationContext.getSharedPreferences(app, Context.MODE_PRIVATE)

    }

    fun getGeneralInfo(info: String): String? {
        val sha: SharedPreferences? = activity?.getApplicationContext()
            ?.getSharedPreferences(activity?.getPackageName(), Context.MODE_PRIVATE)
        var record = sha?.getString(info, "")
        if (record == "") {
            record = ""
        }
        return record
    }

    fun updateGeneralInfo(info: String, value: String?) {
        val sha: SharedPreferences? = activity?.getApplicationContext()
            ?.getSharedPreferences(activity?.getPackageName(),Context.MODE_PRIVATE)
        val edit = sha?.edit()
        edit?.putString(info, value)
        edit?.apply()
    }
}