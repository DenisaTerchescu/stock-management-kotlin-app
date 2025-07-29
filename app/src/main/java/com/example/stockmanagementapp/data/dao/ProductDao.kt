package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stockmanagementapp.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getProductById(id: Int): Flow<Product?>

    @Update
    suspend fun updateProduct(product: Product)
}