package com.example.stockmanagementapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockmanagementapp.data.dao.ProductDao
import com.example.stockmanagementapp.data.dao.SupplierDao
import com.example.stockmanagementapp.data.dao.TransactionDao
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Supplier
import com.example.stockmanagementapp.data.model.Transaction

@Database(entities = [Product::class, Supplier::class, Transaction::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun supplierDao(): SupplierDao
    abstract fun transactionDao(): TransactionDao
}
