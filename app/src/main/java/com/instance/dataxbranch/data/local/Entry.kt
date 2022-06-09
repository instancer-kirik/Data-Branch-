package com.instance.dataxbranch.data.local
import androidx.room.Entity
import java.lang.reflect.ParameterizedType

@Entity
abstract class Entry<T>(tag:String){
    var classname: String = "Entry"


    lateinit var myFinalType: ParameterizedType
    internal var className: String = "default_classname"
    //var myGenericObject: T
    var id: Long? = null
    var tag: String = "default_tag_string"

    enum class EnumTypes {
        QUEST
    }//ABILITY, ITEM, NUGGET, PERSON, SKILL, USER_, ALT_CHARACTER
    fun submitMain(){
        TODO("sends for review")
    }
}