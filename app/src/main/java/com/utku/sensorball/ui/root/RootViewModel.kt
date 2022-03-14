package com.utku.sensorball.ui.root

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.data.data.entities.SensorRecord
import com.data.data.repository.SensorRecordRepository

class RootViewModel(private val repository: SensorRecordRepository) : ViewModel() {

    val selectedRecord = MutableLiveData<SensorRecord>()

    fun getAllRecord() = repository.getAllRecord().asLiveData()

    fun saveRecord(sensorRecord: SensorRecord) = repository.saveRecord(sensorRecord).asLiveData()

}