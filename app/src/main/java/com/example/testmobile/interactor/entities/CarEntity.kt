package com.example.testmobile.interactor.entities

import java.util.*

data class CarEntity(
    val carId: Long,
    var seats: Int = 0,
    var price: Double = 0.0,
    var isNew: Boolean? = null,
    var model: String? = null,
    var isUpdatable: Boolean = true,
    var dateReleased: String? = null,
    val categoryId: String,
    var properties: List<PropertyEntity>
)

data class PropertyEntity(
    val propertyId: Long,
    var isNumber: Boolean?,
    var name: String?,
    var value: String
)


data class CategoryEntity(
    val categoryId: String
){
    override fun toString(): String {
        return categoryId;
    }
}