package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.stockmanagementapp.data.model.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {

    @Query("SELECT * FROM Supplier")
    fun getAllSuppliers(): Flow<List<Supplier>>
}