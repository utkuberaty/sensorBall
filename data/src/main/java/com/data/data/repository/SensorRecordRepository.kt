package com.data.data.repository

import com.data.data.entities.SensorRecord
import com.data.data.local.LocalDataSource
import kotlinx.coroutines.flow.flow

class SensorRecordRepository(private val localDataSource: LocalDataSource) : BaseRepository() {

    fun getAllRecord() = performGetOperation({ localDataSource.getAllRecord() })

    fun saveRecord(sensorRecord: SensorRecord) = flow {
        localDataSource.saveRecord(sensorRecord)
        emit(sensorRecord)
    }

}