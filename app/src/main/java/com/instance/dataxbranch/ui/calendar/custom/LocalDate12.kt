/*
package com.instance.dataxbranch.ui.calendar.custom

import java.io.Serializable
import java.time.*


import java.time.chrono.ChronoLocalDate
import java.time.chrono.Era
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.temporal.*

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class LocalDate12 private constructor() : Temporal, TemporalAdjuster,
    ChronoLocalDate, Serializable {
    fun toLocalDate():LocalDate {
     val r=   LocalDate.of(this.year, this.month, this.dayOfMonth)
        return r
    }

    override fun isSupported(field: TemporalField): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun isSupported(unit: TemporalUnit): Boolean {
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

    override fun getChronology(): IsoChronology {
        throw RuntimeException("Stub!")
    }

    override fun getEra(): Era {
        throw RuntimeException("Stub!")
    }

    var year: Int = 0
        get() {
            throw RuntimeException("Stub!")
        }
    val monthValue: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val month: Month
        get() {
            throw RuntimeException("Stub!")
        }
    var dayOfMonth: Int=1
        get() {
            throw RuntimeException("Stub!")
        }
    val dayOfYear: Int
        get() {
            throw RuntimeException("Stub!")
        }
    var dayOfWeek: DayOf12dWeek = DayOf12dWeek.A
        get() {
            throw RuntimeException("Stub!")
        }

    override fun isLeapYear(): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun lengthOfMonth(): Int {
        throw RuntimeException("Stub!")
    }

    override fun lengthOfYear(): Int {
        throw RuntimeException("Stub!")
    }

    override fun with(adjuster: TemporalAdjuster): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun with(field: TemporalField, newValue: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun withYear(year: Int): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun withMonth(month: Int): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun withDayOfMonth(dayOfMonth: Int): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun withDayOfYear(dayOfYear: Int): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun plus(amountToAdd: TemporalAmount): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun plus(amountToAdd: Long, unit: TemporalUnit): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun plusYears(yearsToAdd: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun plusMonths(monthsToAdd: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun plusWeeks(weeksToAdd: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun plusDays(daysToAdd: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun minus(amountToSubtract: TemporalAmount): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun minus(amountToSubtract: Long, unit: TemporalUnit): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun minusYears(yearsToSubtract: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun minusMonths(monthsToSubtract: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun minusWeeks(weeksToSubtract: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    fun minusDays(daysToSubtract: Long): LocalDate12 {
        throw RuntimeException("Stub!")
    }

    override fun <R> query(query: TemporalQuery<R>): R {
        throw RuntimeException("Stub!")
    }

    override fun adjustInto(temporal: Temporal): Temporal {
        throw RuntimeException("Stub!")
    }

    override fun until(endExclusive: Temporal, unit: TemporalUnit): Long {
        throw RuntimeException("Stub!")
    }

    override fun until(endDateExclusive: ChronoLocalDate): Period {
        throw RuntimeException("Stub!")
    }

    override fun format(formatter: DateTimeFormatter): String {
        throw RuntimeException("Stub!")
    }

    override fun atTime(time: LocalTime): LocalDateTime {
        throw RuntimeException("Stub!")
    }

    fun atTime(hour: Int, minute: Int): LocalDateTime {
        throw RuntimeException("Stub!")
    }

    fun atTime(hour: Int, minute: Int, second: Int): LocalDateTime {
        throw RuntimeException("Stub!")
    }

    fun atTime(hour: Int, minute: Int, second: Int, nanoOfSecond: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun atTime(time: OffsetTime?): OffsetDateTime {
        throw RuntimeException("Stub!")
    }

    fun atStartOfDay(): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun atStartOfDay(zone: ZoneId?): ZonedDateTime {
        throw RuntimeException("Stub!")
    }

    override fun toEpochDay(): Long {
        throw RuntimeException("Stub!")
    }

    override fun compareTo(other: ChronoLocalDate): Int {
        throw RuntimeException("Stub!")
    }

    override fun isAfter(other: ChronoLocalDate): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun isBefore(other: ChronoLocalDate): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun isEqual(other: ChronoLocalDate): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun equals(obj: Any?): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun hashCode(): Int {
        throw RuntimeException("Stub!")
    }

    override fun toString(): String {
        throw RuntimeException("Stub!")
    }

    companion object {

        val MAX: LocalDate12? = null
        val MIN: LocalDate12? = null
        fun now(): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun now(zone: ZoneId?): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun now(clock: Clock?): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun of(year: Int, month: Month?, dayOfMonth: Int): LocalDate12? {
           this.year = year
            this.month = month
            this.dayOfMonth = dayOfMonth
            return this
        }

        fun of(year: Int, month: Int, dayOfMonth: Int): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun ofYearDay(year: Int, dayOfYear: Int): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun ofEpochDay(epochDay: Long): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun from(temporal: TemporalAccessor?): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun parse(text: CharSequence?): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun parse(text: CharSequence?, formatter: DateTimeFormatter?): LocalDate12 {
            throw RuntimeException("Stub!")
        }

        fun toThis(date: LocalDate?): LocalDate12? {
            val r= date?.let { LocalDate12.of(it.year, date.month, date.dayOfMonth) }
            return r
        }

    }

    init {
        throw RuntimeException("Stub!")
    }
}
*/
