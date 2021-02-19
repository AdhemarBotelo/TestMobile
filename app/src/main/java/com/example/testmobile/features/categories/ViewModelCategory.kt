package com.example.testmobile.features.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.useCases.UseCase
import com.example.testmobile.interactor.useCases.deleteCategory
import com.example.testmobile.interactor.useCases.getCatergories
import com.example.testmobile.interactor.useCases.insertCategory

class ViewModelCategory
constructor(
    private val getCatergories: getCatergories,
    private val deleteCategory: deleteCategory,
    private val insertCategory: insertCategory
) : BaseViewModel() {

    private val _categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData()
    val categories: LiveData<List<CategoryEntity>> = _categories

    private val _wasProcessed: MutableLiveData<Boolean> = MutableLiveData()
    val wasProcessed: LiveData<Boolean> = _wasProcessed

    fun loadCategories() =
        getCatergories(UseCase.None()) { it.fold(::handleFailure, ::handleCategories) }

    private fun handleCategories(categories: List<CategoryEntity>) {
        _categories.value = categories
    }

    fun deleteCategory(id: String) {
        deleteCategory(id) { it.fold(::handleFailure, ::handleProcessed) }
    }

    fun insertCategory(category: CategoryEntity) {
        insertCategory(category) { it.fold(::handleFailure, ::handleProcessed) }
    }

    private fun handleProcessed(result: Boolean) {
        _wasProcessed.value = result
    }
}