package com.example.testmobile.data.db

import androidx.room.*
import com.example.testmobile.data.models.*

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(vararg category: Category)

    @Delete
    fun deleteCategory(vararg category: Category)

    @Update
    fun updateCategory(vararg category: Category)

    @Query("Select * From Categories")
    fun selectCategories(): List<Category>
}