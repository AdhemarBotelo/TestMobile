package com.example.testmobile.features.di

import androidx.room.Room
import com.example.testmobile.data.db.CarsDatabase
import com.example.testmobile.data.repository.CarRepository
import com.example.testmobile.data.repository.CarRepositoryImpl
import com.example.testmobile.data.repository.CarStore
import com.example.testmobile.data.repository.CarStoreImpl
import com.example.testmobile.features.cars.CarViewModel
import com.example.testmobile.features.cars.CarsViewModel
import com.example.testmobile.interactor.useCases.GetCars
import com.example.testmobile.interactor.useCases.GetCatergories
import com.example.testmobile.interactor.useCases.InitDB
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// defines a factory creates a new instance every time
// defines a single = singleton

val mLocalModules = module {
    single() {
        Room.databaseBuilder(androidApplication(), CarsDatabase::class.java, "cars_database")
            .build()
    }
}

val mRepositoryModules = module {
    single<CarStore> { CarStoreImpl(get()) }
    single<CarRepository> { CarRepositoryImpl(get()) }
}

val mUseCaseModules = module {
    factory { GetCars(get()) }
    factory { GetCatergories(get()) }
    factory { InitDB(get()) }
}

val mViewModelsModules = module {
    viewModel {
        CarsViewModel(get(), get())
    }
    viewModel {
        CarViewModel(get())
    }

}


