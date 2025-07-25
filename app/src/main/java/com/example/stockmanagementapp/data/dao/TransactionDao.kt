package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.stockmanagementapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `Transaction`")
    fun getAllTransactions(): Flow<List<Transaction>>
}