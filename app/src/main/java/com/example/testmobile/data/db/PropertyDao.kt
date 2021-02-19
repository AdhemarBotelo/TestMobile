package com.example.testmobile.data.db

import androidx.room.*
import com.example.testmobile.data.models.Property

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProperty(vararg property: Property)

    @Delete
    fun deleteProperty(vararg property: Property)

    @Update
    fun updateProperty(vararg property: Property)

    @Query("Select * From Properties Where propertyCategoryId=:idCategory")
    fun selectPropertyByCategory(idCategory: String): List<Property>
}