package com.example.composetest.util

import java.lang.NullPointerException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy")
    val calendar: Calendar = Calendar.getInstance()

    fun longToDate(longVal: Long): Date
    {
        return Date(longVal)
    }

    fun dateToLong(dateVal: Date): Long{
        return (dateVal.time)/1000
    }

    fun dateToString(dateVal: Date): String{
        return simpleDateFormat.format(dateVal)
    }

    fun stringToDate(stringVal: String):Date{
        return simpleDateFormat.parse(stringVal)?: throw NullPointerException("Given String value can't be converted to date ")
    }

    fun getTimeInMillis(): Long{
        return System.currentTimeMillis()
    }

    fun createTimestamp(): Date
    {
        return Date()
    }

}