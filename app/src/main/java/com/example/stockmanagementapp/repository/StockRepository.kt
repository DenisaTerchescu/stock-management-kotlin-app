package com.example.stockmanagementapp.repository

import com.example.stockmanagementapp.data.dao.ProductDao
import com.example.stockmanagementapp.data.dao.SupplierDao
import com.example.stockmanagementapp.data.dao.TransactionDao
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Supplier
import com.example.stockmanagementapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow


class StockRepository(
    private val productDao: ProductDao,
    private val supplierDao: SupplierDao,
    private val transactionDao: TransactionDao
) {
    // For the products
    suspend fun insertProduct(product: Product) = productDao.insert(product)
    suspend fun deleteProduct(product: Product) = productDao.delete(product)
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    // For the suppliers
    suspend fun insertSupplier(supplier: Supplier) = supplierDao.insert(supplier)
    suspend fun deleteSupplier(supplier: Supplier) = supplierDao.delete(supplier)
    fun getAllSuppliers(): Flow<List<Supplier>> = supplierDao.getAllSuppliers()

    // For the transactions
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()
}