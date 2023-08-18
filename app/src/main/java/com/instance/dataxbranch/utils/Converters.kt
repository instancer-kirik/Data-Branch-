package com.instance.dataxbranch.utils

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.instance.dataxbranch.data.entities.AbilityEntity
import com.instance.dataxbranch.data.entities.QuestEntity
import com.instance.dataxbranch.data.entities.User

import com.instance.dataxbranch.quests.Quest
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*


class Converters {

    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(PairAdapterFactory())
        .build()
    val adapter: JsonAdapter<Quest.QuestObjective> = moshi.adapter(Quest.QuestObjective::class.java)
    val adapter2: JsonAdapter<AbilityEntity> = moshi.adapter(AbilityEntity::class.java)//added later
    val adapter3: JsonAdapter<User> = moshi.adapter(User::class.java)//added later
    val adapter4: JsonAdapter<List<Int>> =
        moshi.adapter<List<Int>>(Types.newParameterizedType(List::class.java, Integer::class.java)).nonNull()
    val adapter5: JsonAdapter<Pair<String,String>> =
        moshi.adapter<Pair<String,String>>(Types.newParameterizedType(Pair::class.java, String::class.java,String::class.java)).nonNull()
  /*  val type4: Type = Types.newParameterizedType(
        List::class.java,
        Long::class.java
    )*/
  val type2: Type = Types.newParameterizedType(
      List::class.java,
      String::class.java
  )
    val type3: Type = Types.newParameterizedType(
        Pair::class.java,
        String::class.java
    )
    val type5 = Types.newParameterizedType(
        Pair::class.java,
        String::class.java,
        String::class.java
    )
    val adapter6 = moshi.adapter<Pair<String, String>>(type5)
    private val listOfIntAdapter: JsonAdapter<List<Int>> =
        moshi.adapter<List<Int>>(Types.newParameterizedType(List::class.java, Integer::class.java)).nonNull()

    private val listOfNullableIntAdapter: JsonAdapter<List<Int?>> =
        moshi.adapter<List<Int?>>(Types.newParameterizedType(List::class.java, Int::class.javaObjectType)).nonNull()
    val jsonAdapter: JsonAdapter<List<String>> =
        moshi.adapter(type2)

    val json = jsonAdapter.toJson(listOf())
     val pairofStringsAdapter: JsonAdapter<Pair<String,String>> =
        moshi.adapter<Pair<String,String>>(Types.newParameterizedType(Pair::class.java, String::class.java, String::class.java)).nonNull()
    val jsonAdapter2: JsonAdapter<Pair<String,String>> =
        moshi.adapter(type3)
/*
    //val jsonAdapter3: JsonAdapter<List<Long>> = moshi.adapter(Types.newParameterizedType(List::class.java, Long::class.javaObjectType)).nonNull()
    private val listOfLongAdapter: JsonAdapter<List<Long>> =
        moshi.adapter<List<Long>>(Types.newParameterizedType(List::class.java,Long::class.java)).nonNull()*/
        /*@TypeConverter newParameterizedType(Type rawType, Type... typeArguments)
        fun fromStringArrayList(value: ArrayList<String>): String {

            return Gson().toJson(value)
        }*/


    /*@TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
*/
    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromContext(context: Context): String {
        val gson = Gson()
        return gson.toJson(context)
    }
    @TypeConverter
    fun fromContext(contextIn: String): Context {
        val gson = Gson()
        return gson.fromJson(contextIn)
    }
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
    @TypeConverter
    fun fromIntList(list: List<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return try {
            Gson().fromJson<ArrayList<Int>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
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

   /* @TypeConverter
    fun toLongArrayList(value: String): ArrayList<Long> {
        return try {
            Gson().fromJson<ArrayList<Long>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
    @TypeConverter
    fun fromLongArrayList(list: ArrayList<Long?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }*/
    @TypeConverter
    fun toLongList(value: String): List<Long> {
        return try {
            Gson().fromJson<List<Long>>(value) //using extension function
        } catch (e: Exception) {
            listOf()
        }
    }
    @TypeConverter
    fun fromLongList(list: List<Long?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    fun <T> getObjectFromJson(typeOfObject: Class<T>, jsonString: String): T? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi.adapter<T>(typeOfObject)
        return jsonAdapter.fromJson(jsonString)!!
    }
    inline fun <reified T> getObject(value: String, typeOfObject: Class<T>?): T? {
       /* val value = getString( null,key) ?: return null
        return MoshiDataConverter().getObjectFromJson(typeOfObject!!, value)
*/
        return try {
            Gson().fromJson<T>(value) //using extension function
        } catch (e: Exception) {
           null
        }
    }



}

internal class PairAdapter(
    private val firstAdapter: JsonAdapter<Any>,
    private val secondAdapter: JsonAdapter<Any>,
    private val listAdapter: JsonAdapter<List<String>>
) : JsonAdapter<Pair<Any, Any>>() {

    override fun toJson(writer: JsonWriter, value: Pair<Any, Any>?) {
        value ?: throw NullPointerException("value == null")

        writer.beginArray()
        firstAdapter.toJson(writer, value.first)
        secondAdapter.toJson(writer, value.second)
        writer.endArray()
    }

    override fun fromJson(reader: JsonReader): Pair<Any, Any>? {
        val list = listAdapter.fromJson(reader) ?: return null

        require(list.size == 2) { "Pair with more or less than two elements: $list" }

        val first = firstAdapter.fromJsonValue(list[0])
            ?: throw IllegalStateException("Pair without first")
        var value:String=""
        list.getOrNull(1)?.let {
            value=it
        }

        val second = secondAdapter.fromJsonValue( value)
            ?: throw IllegalStateException("Pair without second")

        return first to second
    }
}

class PairAdapterFactory : JsonAdapter.Factory {

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        if (type !is ParameterizedType || Pair::class.java != type.rawType) return null

        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val listAdapter = moshi.adapter<List<String>>(listType)

        return PairAdapter(
            moshi.adapter(type.actualTypeArguments[0]),
            moshi.adapter(type.actualTypeArguments.getOrNull(1)?:String::class.java),
            listAdapter
        )
    }
}



