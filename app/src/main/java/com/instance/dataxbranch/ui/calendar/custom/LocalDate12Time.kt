/*
package com.instance.dataxbranch.ui.calendar.custom


import java.io.Serializable
import java.time.*

import java.time.chrono.ChronoLocalDateTime
import java.time.chrono.ChronoZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.*


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class LocalDate12Time private constructor() : Temporal, TemporalAdjuster,
    ChronoLocalDateTime<LocalDate12?>, Serializable {
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

     override fun toLocalDate(): LocalDate12 {
        throw RuntimeException("Stub!")
    }
    fun toLocalDate12(): LocalDate12 {
        throw RuntimeException("Stub!")
    }
    val year: Int
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
    val dayOfMonth: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val dayOfYear: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val dayOfWeek: DayOfWeek
        get() {
            throw RuntimeException("Stub!")
        }

    override fun toLocalTime(): LocalTime {
        throw RuntimeException("Stub!")
    }

    val hour: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val minute: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val second: Int
        get() {
            throw RuntimeException("Stub!")
        }
    val nano: Int
        get() {
            throw RuntimeException("Stub!")
        }

    override fun with(adjuster: TemporalAdjuster): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    override fun with(field: TemporalField, newValue: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withYear(year: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withMonth(month: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withDayOfMonth(dayOfMonth: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withDayOfYear(dayOfYear: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withHour(hour: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withMinute(minute: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withSecond(second: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun withNano(nanoOfSecond: Int): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun truncatedTo(unit: TemporalUnit?): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    override fun plus(amountToAdd: TemporalAmount): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    override fun plus(amountToAdd: Long, unit: TemporalUnit): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusYears(years: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusMonths(months: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusWeeks(weeks: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusDays(days: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusHours(hours: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusMinutes(minutes: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusSeconds(seconds: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun plusNanos(nanos: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    override fun minus(amountToSubtract: TemporalAmount): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    override fun minus(amountToSubtract: Long, unit: TemporalUnit): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusYears(years: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusMonths(months: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusWeeks(weeks: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusDays(days: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusHours(hours: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusMinutes(minutes: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusSeconds(seconds: Long): LocalDate12Time {
        throw RuntimeException("Stub!")
    }

    fun minusNanos(nanos: Long): LocalDate12Time {
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

    override fun format(formatter: DateTimeFormatter): String {
        throw RuntimeException("Stub!")
    }

    fun atOffset(offset: ZoneOffset?): OffsetDateTime {
        throw RuntimeException("Stub!")
    }

    override fun atZone(zone: ZoneId): ChronoZonedDateTime<LocalDate12?>? {
        throw RuntimeException("Stub!")
    }

    override fun compareTo(other: ChronoLocalDateTime<*>?): Int {
        throw RuntimeException("Stub!")
    }

    override fun isAfter(other: ChronoLocalDateTime<*>?): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun isBefore(other: ChronoLocalDateTime<*>?): Boolean {
        throw RuntimeException("Stub!")
    }

    override fun isEqual(other: ChronoLocalDateTime<*>?): Boolean {
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
        val MAX: LocalDate12Time? = null
        val MIN: LocalDate12Time? = null
        fun now(): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun now(zone: ZoneId?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun now(clock: Clock?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(year: Int, month: Month?, dayOfMonth: Int, hour: Int, minute: Int): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(
            year: Int,
            month: Month?,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int
        ): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(
            year: Int,
            month: Month?,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int,
            nanoOfSecond: Int
        ): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int
        ): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int,
            nanoOfSecond: Int
        ): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun of(date: LocalDate12?, time: LocalTime?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun ofInstant(instant: Instant?, zone: ZoneId?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun ofEpochSecond(
            epochSecond: Long,
            nanoOfSecond: Int,
            offset: ZoneOffset?
        ): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun from(temporal: TemporalAccessor?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun parse(text: CharSequence?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }

        fun parse(text: CharSequence?, formatter: DateTimeFormatter?): LocalDate12Time {
            throw RuntimeException("Stub!")
        }
    }

    init {
        throw RuntimeException("Stub!")
    }
}*/
