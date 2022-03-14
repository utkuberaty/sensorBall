package com.data.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.data.data.entities.SensorRecord

@Dao
interface SensorRecordDao {

    @Query("SELECT * FROM sensor_record")
    suspend fun getAllRecord(): List<SensorRecord>

    @Insert
    suspend fun saveRecord(sensorRecord: SensorRecord)

}