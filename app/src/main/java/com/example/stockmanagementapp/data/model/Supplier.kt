package com.example.stockmanagementapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Supplier(
    @PrimaryKey val id: Int,
    val name: String,
    val contactPerson: String,
    val phone: String,
    val email: String,
    val address: String
)