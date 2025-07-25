package com.example.stockmanagementapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey val id: Int,
    val date: Long,
    val type: String,
    val productId: Int,
    val quantity: Int,
    val notes: String
)