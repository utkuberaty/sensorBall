package com.utku.sensorball.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.currentTime(): String {
    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").apply {
        return this.format(this@currentTime)
    }
}