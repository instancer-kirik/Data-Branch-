package com.instance.dataxbranch.domain

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
val dateFormattingPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
val dateFormattingPattern2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
val dateFormattingPattern3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
val dateFormattingPattern4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
val dateFormattingPattern5 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//val dateFormattingPattern3 = DateTimeFormatter.ofPattern(//2022-11-05T         16:24:30.284431)
fun getNow():String{
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)
    println("Current Date and Time is: $formatted")
    return formatted
}
fun parse(tbd:String):LocalDateTime {
    var result: LocalDateTime
    try {
        result = LocalDateTime.parse(tbd, dateFormattingPattern)
    } catch (e: Exception) {
        Log.d("DATETIME", "${e.message} \n ${e.printStackTrace()}")
try {
    result = LocalDateTime.parse(tbd, dateFormattingPattern2)

}catch (e: Exception){
    Log.d("DATETIME2", "${e.message} \n ${e.printStackTrace()}")
    try {
        result = LocalDateTime.parse(tbd, dateFormattingPattern3)

    }catch (e: Exception){
        Log.d("DATETIME3", "${e.message} \n ${e.printStackTrace()}")
        try {
        result = LocalDateTime.parse(tbd, dateFormattingPattern4)
        }catch (e: Exception){
            Log.d("DATETIME4", "${e.message} \n ${e.printStackTrace()}")
            result = LocalDate.parse(tbd).atStartOfDay()//LocalDateTime.parse(tbd, dateFormattingPattern5)
        }
    }
   // result = LocalDateTime.parse(tbd, dateFormattingPattern3)
}
        }
    Log.d("DATETIME4", "result: $result")
    return result
}
fun parseJustDate(tbd:String): LocalDate? {
    return parse(tbd).toLocalDate()
}
