package com.example.testmobile.data.models

import androidx.room.*
import java.util.*

@Entity(tableName = "Cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val carId: Long = 0,
    var seats: Int = 0,
    var price: Double = 0.0,
    var isNew: Boolean? = null,
    var model: String? = null,
    var isUpdatable: Boolean = true,
    var dateReleased: String? = null,
    val categoryId: String
)

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey val categoryId: String
)

@Entity(tableName = "Properties")
data class Property(
    @PrimaryKey(autoGenerate = true) val propertyId: Long = 0,
    var isNumber: Boolean? = null,
    var name: String? = null,
    val propertyCategoryId: String
)

@Entity(primaryKeys = ["carId", "propertyId"])
data class CarPropertyCrossRef(
    var carId: Long,
    val propertyId: Long,
    val value:String
)


data class CarWithProperties(
    @Embedded val car: Car,
    @Embedded val properties: Property,
    val value:String
)

data class CategoryWithProperties(
    @Embedded val categoryCar: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "propertyCategoryId"
    )
    val properties: List<Property>
)