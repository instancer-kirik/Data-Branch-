package com.instance.dataxbranch.domain

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
val dateFormattingPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
val dateFormattingPattern2 = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSS")
fun getNow():String{
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)
    println("Current Date and Time is: $formatted")
    return formatted
}
fun parse(tbd:String){
    try{
        LocalDateTime.parse(tbd,)
    }catch(e:Error){
        Log.d("DATETIME","${e.message} \n ${e.printStackTrace()}")
        try{LocalDateTime.parse(tbd,dateFormattingPattern2)}
        catch (e:Error){Log.d("DATETIME2","${e.message} \n ${e.printStackTrace()}")}
    }
}