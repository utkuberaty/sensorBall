package com.data.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.data.data.entities.MovementRecordTypeConverter
import com.data.data.entities.SensorRecord

/**
 * Initialize RoomDatabase [SensorRecordDao]
 * */

@Database(entities = [SensorRecord::class], version = 1, exportSchema = true)
@TypeConverters(MovementRecordTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sensorRecordDao(): SensorRecordDao
}