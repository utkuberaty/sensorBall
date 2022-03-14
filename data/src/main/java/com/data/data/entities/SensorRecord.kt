package com.data.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "sensor_record")
data class SensorRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String = "",
    val movementRecord: List<Pair<Float, Float>> = listOf()
)

class MovementRecordTypeConverter() {
    @TypeConverter
    fun fromPermissions(movementRecord: List<Pair<Float, Float>>): String =
        Json.encodeToString(movementRecord)

    @TypeConverter
    fun toPermissions(movementRecord: String): List<Pair<Float, Float>> =
        Json.decodeFromString(movementRecord)
}
