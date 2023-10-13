package com.ayush.cashcrunch.core

import android.util.Log

object DateUtil {

    fun formatDate(date: String): String {
        val shortenedDate = date.subSequence(2, date.length)

        val dateArray = shortenedDate.split("-")

        val reversed: List<String> = dateArray.asReversed()

        Log.d("reversed", reversed.toString())

        var date = ""
        for(i in reversed.indices) {
            if(i < 2) {
                date = date + reversed[i] + "/"
            } else {
                date += reversed[i]
            }
        }

        return date
    }
}