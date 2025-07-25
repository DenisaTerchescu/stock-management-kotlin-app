package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.stockmanagementapp.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAllProducts(): Flow<List<Product>>
}