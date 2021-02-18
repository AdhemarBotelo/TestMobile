package com.example.testmobile.features.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.useCases.GetCatergories
import com.example.testmobile.interactor.useCases.UseCase

class CarViewModel constructor(private val getCatergories: GetCatergories) : BaseViewModel() {

    private val _categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData()
    val categories: LiveData<List<CategoryEntity>> = _categories

    fun loadCategories() =
        getCatergories(UseCase.None()) { it.fold(::handleFailure, ::handleCategoriersList) }

    private fun handleCategoriersList(category: List<CategoryEntity>) {
        // in case we need some transformation
        _categories.value = category
    }
}