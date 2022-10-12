package com.instance.dataxbranch.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MapConverter {
    @JvmStatic
    @TypeConverter
    fun fromString(value: String): Map<Long, Int> {
        val mapType = object : TypeToken<Map<Long, Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    @JvmStatic
    fun fromLongMap(map: Map<Long, Int>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}