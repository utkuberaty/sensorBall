package com.data.data.local

import com.data.data.entities.SensorRecord

/**
 * Local data source for [SensorRecordDao]
 * Inserts and Gets [com.data.data.entities.SensorRecord]
 * */

class LocalDataSource(private val sensorRecordDao: SensorRecordDao) {

    suspend fun getAllRecord() = sensorRecordDao.getAllRecord()

    suspend fun saveRecord(sensorRecord: SensorRecord) = sensorRecordDao.saveRecord(sensorRecord)

}