package com.example.taskapp.utila

import android.content.Context
import android.content.SharedPreferences
class Preferences(private val context: Context) {
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences("onBoard", Context.MODE_PRIVATE)

    fun setBoardingShowed(isShow: Boolean) {
        return sharedPreference.edit().putBoolean("onBoard", isShow).apply()
    }

    fun isBoardingShowed(): Boolean {
        return sharedPreference.getBoolean("onBoard", false)
    }

    fun getName(): String? {
        return sharedPreference.getString(PREF_SAVE_NAME, "")
    }
    fun saveName(title: String) {
        sharedPreference.edit().putString(PREF_SAVE_NAME,title).apply()
    }
    fun getImage(): String? {
        return sharedPreference.getString(PREF_IMAGE_PROFILE,"")
    }
    fun saveImage(image:String){
        sharedPreference.edit().putString(PREF_IMAGE_PROFILE,image).apply()
    }
    companion object{
        private const val PREF_SAVE_NAME = "name"
        private const val PREF_IMAGE_PROFILE = "image"
    }
}