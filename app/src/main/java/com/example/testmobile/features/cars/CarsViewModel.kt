package com.example.testmobile.features.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.useCases.GetCars
import com.example.testmobile.interactor.useCases.UseCase
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.useCases.InitDB

class CarsViewModel
constructor(private val getCars: GetCars, private val initDB: InitDB) : BaseViewModel() {

    private val _cars: MutableLiveData<List<CarEntity>> = MutableLiveData()
    val cars: LiveData<List<CarEntity>> = _cars

    fun loadCars() = getCars(UseCase.None()) { it.fold(::handleFailure, ::handleCarList) }

    fun initDataBase() = initDB(UseCase.None()) { it.fold(::handleFailure, {}) }

    private fun handleCarList(cars: List<CarEntity>) {
        // in case we need some transformation
        _cars.value = cars
    }
}