package com.example.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Constanta {

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}