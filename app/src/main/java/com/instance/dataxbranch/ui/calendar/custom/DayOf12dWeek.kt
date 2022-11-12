package com.instance.dataxbranch.ui.calendar.custom


import java.time.DayOfWeek
import java.time.format.TextStyle
import java.time.temporal.*
import java.util.*

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

enum class DayOf12dWeek : TemporalAccessor, TemporalAdjuster {
      A,B,C,D,E,F,G,H,I,J,K,L,
    ;
    //1,2,3,4,5,6,7,8,9,0,1,2
    //12 days
    /*MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,
    ONEDAY, SOMEDAY, YESTERDAY, TOMORROW,*/
    //;


    val value: Int
        get() {
            return values().indexOf(this)
        }

    fun getDisplayName(style: TextStyle?, locale: Locale?): String {
        return this.toString()
    }

    override fun isSupported(field: TemporalField): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun range(field: TemporalField): ValueRange {
        throw RuntimeException("Stub!")
    }

    override fun get(field: TemporalField): Int {
        throw RuntimeException("Stub!")
    }

    override fun getLong(field: TemporalField): Long {
        throw RuntimeException("Stub!")
    }

    operator fun plus(days: Long): DayOf12dWeek {
        return of((this.value + days.toInt()) % DayOf12dWeek.values().size)

    }//internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7

    operator fun minus(days: Long): DayOf12dWeek {
        return of((this.value - days.toInt()) % DayOf12dWeek.values().size)
    }

    override fun <R> query(query: TemporalQuery<R>): R {
        throw RuntimeException("Stub!")
    }

    override fun adjustInto(temporal: Temporal): Temporal {
        throw RuntimeException("Stub!")
    }

    companion object {
        fun of(dayOfWeek: Int): DayOf12dWeek {
            return DayOf12dWeek.values()[dayOfWeek]
        }

        fun from(temporal: TemporalAccessor?): DayOf12dWeek {
            //return DayOf12dWeek.values()[temporal]
            throw RuntimeException("Stub!")
        }
    }
}
