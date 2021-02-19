package com.example.testmobile.data.db

import androidx.room.*
import com.example.testmobile.data.models.*

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(vararg category: Category)

    @Query("Delete From categories WHERE categoryId=:idCategory")
    fun deleteCategory(idCategory: String)

    @Update
    fun updateCategory(vararg category: Category)

    @Query("Select * From Categories")
    fun selectCategories(): List<Category>
}