package com.e.iselleradmin.Util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.Proxy
import java.util.*
import kotlin.collections.ArrayList

class IO {


    companion object{
        const val SHARE_PREFERENCES = "com.e.iselleramin.SHARED_PREFERENCES"

        @JvmStatic
        fun setData(context: Context, keyValue:String, value:String){
            val editor = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE).edit()
            editor.putString(keyValue, value)
            editor.commit()

        }

        @JvmStatic
        fun  getData(context: Context, keyValue:String):String?{
            val pref = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)
            return pref.getString(keyValue, "")
        }
        @JvmStatic
        fun deleteData(context: Context, keyValue: String){
            val pref = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)
            pref.edit().remove(keyValue).apply()
        }

        @JvmStatic
        fun saveArrayList(context: Context, key:String,arrayList:ArrayList<ByteArray>){
            val editor = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE).edit()
            val gson= Gson()
            val json = gson.toJson(arrayList)
            editor.putString(key,json)
            editor.commit()

        }
        @JvmStatic
        fun getArrayList(context: Context,key:String):ArrayList<ByteArray>?{
            val pref = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)
            val gson=Gson()
            val json = pref.getString(key, "")
            val types= object :TypeToken<ArrayList<ByteArray>>(){}.type
            return  gson.fromJson(json, types)
        }
    }
}