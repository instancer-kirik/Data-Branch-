package com.instance.dataxbranch.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.instance.dataxbranch.data.entities.QuestEntity

import com.instance.dataxbranch.quests.Quest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.util.*


class Converters {

    val moshi: Moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<Quest.QuestObjective> = moshi.adapter(Quest.QuestObjective::class.java)
    val type2: Type = Types.newParameterizedType(
        ArrayList::class.java,
        String::class.java
    )
    val jsonAdapter: JsonAdapter<ArrayList<String>> =
        moshi.adapter(type2)

    val json = jsonAdapter.toJson(arrayListOf("Quest.QuestObjective())"))

        @TypeConverter
        fun fromStringArrayList(value: ArrayList<String>): String {

            return Gson().toJson(value)
        }

        @TypeConverter
        fun toStringArrayList(value: String): ArrayList<String> {
            return try {
                Gson().fromJson<ArrayList<String>>(value) //using extension function
            } catch (e: Exception) {
                arrayListOf()
            }
        }
    @TypeConverter
    fun toStringArrayListObj(value: String): ArrayList<Quest.QuestObjective> {
        return try {
            Gson().fromJson<ArrayList<Quest.QuestObjective>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
    @TypeConverter
    fun fromStringArrayListObj(value: ArrayList<Quest.QuestObjective>): String {

        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromQuestToEntity(value: Quest): QuestEntity {
        return QuestEntity()

    }

}