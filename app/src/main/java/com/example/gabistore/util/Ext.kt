package com.example.gabistore.util

import android.icu.util.Calendar
import android.util.Log
import androidx.room.util.recursiveFetchArrayMap
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.definition._createDefinition
import org.koin.core.time.TimeInMillis
import java.text.SimpleDateFormat

val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->

    Log.v("error", "Error -> " + throwable.message)

}

fun stylePrice(oldPrice: String): String {

    if (oldPrice.length > 3) {

        val reversed = oldPrice.reversed()
        var newPrice = ""

        for (i in oldPrice.indices) {

            newPrice += reversed[i]

            if ((i + 1) % 3 == 0) {
                newPrice += ','
            }

        }

        var readyToGO = newPrice.reversed()

        if (readyToGO.first() == ',') {
            return readyToGO.substring(1) + " T"
        }

        return "$readyToGO T"

    }

    return "$oldPrice T"

}

fun styleTime(timeInMillis: Long) :String {

    val formatter = SimpleDateFormat("yyyy-MM-dd   hh:mm")

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis

    return formatter.format( calendar.time )

}