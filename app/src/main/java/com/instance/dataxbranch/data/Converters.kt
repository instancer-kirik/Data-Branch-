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



    @JvmStatic
    @TypeConverter
    fun fromStringtoLongSSMap(value: String): Map<Long,Pair<String,String>> {
        val mapType = object : TypeToken<Map<Long,Pair<String,String>>>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    @JvmStatic
    fun fromLongSSMap(map: Map<Long,Pair<String,String>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
    @JvmStatic
    @TypeConverter
    fun fromStringtoSSSMap(value: String): Map<String,Pair<String,String>> {
        val mapType = object : TypeToken<Map<String,Pair<String,String>>>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    @JvmStatic
    fun fromSSSMap(map: Map<String,Pair<String,String>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
    @JvmStatic
    @TypeConverter
    fun fromStringtoSListSSMap(value: String): Map<String,Pair<List<String>,String>> {
        val mapType = object : TypeToken<Map<String,Pair<List<String>,String>>>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    @JvmStatic
    fun fromSListSSMap(map: Map<String,Pair<List<String>,String>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
    @JvmStatic
    @TypeConverter
    fun fromStringtoLongListSSMap(value: String): Map<Long,Pair<List<String>,String>> {
        val mapType = object : TypeToken<Map<Long,Pair<List<String>,String>>>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    @JvmStatic
    fun fromLongListSSMap(map: Map<Long,Pair<List<String>,String>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}