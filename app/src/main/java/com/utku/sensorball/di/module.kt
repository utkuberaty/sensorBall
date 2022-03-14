package com.utku.sensorball.di

import androidx.room.Room
import com.data.data.local.AppDatabase
import com.data.data.local.LocalDataSource
import com.data.data.repository.SensorRecordRepository
import com.utku.sensorball.ui.root.RootViewModel
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    /**
     * This is kotlin serialization, use for convert json to data class
     * or data class to json
     * */

    single {
        Json {
            prettyPrint = true
            isLenient = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "SensorRecord.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { (get() as AppDatabase).sensorRecordDao() }

    single { SensorRecordRepository(get()) }

    single { LocalDataSource(get()) }

}

val viewModelModule = module {
    viewModel { RootViewModel(get()) }
}

val allModules = dataModule + viewModelModule