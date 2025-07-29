package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stockmanagementapp.data.model.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {

    @Insert
    suspend fun insert(supplier: Supplier)

    @Update
    suspend fun updateSupplier(supplier: Supplier)

    @Query("SELECT * FROM Supplier")
    fun getAllSuppliers(): Flow<List<Supplier>>

    @Query("SELECT * FROM supplier WHERE id = :id")
     fun getSupplierById(id: Int): Flow<Supplier?>
}