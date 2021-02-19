package com.example.testmobile.features.di

import androidx.room.Room
import com.example.testmobile.data.db.CarsDatabase
import com.example.testmobile.data.repository.*
import com.example.testmobile.features.cars.CarViewModel
import com.example.testmobile.features.cars.CarsViewModel
import com.example.testmobile.features.categories.ViewModelCategory
import com.example.testmobile.interactor.useCases.*
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
    single<CategoryRepository> { CategoryRepositoryImp(get()) }
}

val mUseCaseModules = module {
    factory { GetCars(get()) }
    factory { GetCar(get()) }
    factory { getCatergories(get()) }
    factory { GetPropertiesByCategory(get()) }
    factory { UpdateCar(get()) }
    factory { DeleteCar(get()) }
    factory { InsertCar(get()) }
    factory { InitDB(get()) }
    factory { insertCategory(get()) }
    factory { deleteCategory(get()) }
}

val mViewModelsModules = module {
    viewModel {
        CarsViewModel(get(), get(), get())
    }
    viewModel {
        CarViewModel(get(), get(), get(), get(), get())
    }
    viewModel {
        ViewModelCategory(get(), get(), get())
    }

}