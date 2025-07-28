package com.example.stockmanagementapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.stockmanagementapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM `Transaction`")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY date DESC LIMIT :limit")
    fun getRecentTransactions(limit: Int = 3): Flow<List<Transaction>>
}