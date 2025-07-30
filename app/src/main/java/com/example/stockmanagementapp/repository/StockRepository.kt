package com.example.stockmanagementapp.repository

import com.example.stockmanagementapp.data.dao.ProductDao
import com.example.stockmanagementapp.data.dao.SupplierDao
import com.example.stockmanagementapp.data.dao.TransactionDao
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Supplier
import com.example.stockmanagementapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class StockRepository(
    private val productDao: ProductDao,
    private val supplierDao: SupplierDao,
    private val transactionDao: TransactionDao
) {
    // For the products
    fun getProductById(id: Int): Flow<Product?> {
        return productDao.getProductById(id)
    }

    suspend fun insertProduct(product: Product) = productDao.insert(product)
    suspend fun deleteProduct(product: Product) = productDao.delete(product)
    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    fun getLowStockProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { products ->
            products.filter { it.currentStockLevel < it.minimumStockLevel }
        }
    }

    suspend fun populateProductsIfEmpty() {
        val products = productDao.getAllProducts().first()

        if (products.isEmpty()) {
            val defaultProducts = listOf(
                Product(
                    id = 0,
                    name = "Chocolate",
                    description = "Dark chocolate with a sprinkle of vanilla",
                    price = 100.0,
                    category = "Sweets",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 10,
                    minimumStockLevel = 1
                ), Product(
                    id = 1,
                    name = "IceCream",
                    description = "Strawberry icecream",
                    price = 100.0,
                    category = "Sweets",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 10,
                    minimumStockLevel = 1
                ), Product(
                    id = 5,
                    name = "Apples",
                    description = "Lovely fruit",
                    price = 2.0,
                    category = "Fruit",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 5,
                    minimumStockLevel = 10
                ), Product(
                    id = 6,
                    name = "Blueberries",
                    description = "Lovely fruits",
                    price = 5.0,
                    category = "Forest fruit",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 7,
                    minimumStockLevel = 15
                ), Product(
                    id = 7,
                    name = "Pineapple",
                    description = "Lovely fruit",
                    price = 10.0,
                    category = "Fruit",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 10,
                    minimumStockLevel = 20
                ), Product(
                    id = 955987,
                    name = "Mango",
                    description = "Very healthy",
                    price = 10.0,
                    category = "Fruit",
                    barcode = "",
                    supplierId = 0,
                    currentStockLevel = 0,
                    minimumStockLevel = 0
                )
            )
            productDao.insertAll(defaultProducts)
        }
    }

    suspend fun populateTransactionsIfEmpty() {
        val transactions = transactionDao.getAllTransactions().first()

        if (transactions.isEmpty()) {
            val defaultTransactions = listOf(
                Transaction(
                    id = 0,
                    date = 1751321959558,
                    type = "sale",
                    productId = 7,
                    quantity = 10,
                    notes = "No notes"
                ), Transaction(
                    id = 1,
                    date = 1753735667551,
                    type = "sale",
                    productId = 5,
                    quantity = 2,
                    notes = "Sold 2 units"
                ), Transaction(
                    id = 2,
                    date = 1753649267551,
                    type = "restock",
                    productId = 6,
                    quantity = 10,
                    notes = "Restocked 10 units"
                ), Transaction(
                    id = 3,
                    date = 1753562867551,
                    type = "sale",
                    productId = 7,
                    quantity = 1,
                    notes = "Customer purchase"
                ), Transaction(
                    id = 513325,
                    date = 1753827923834,
                    type = "restock",
                    productId = 0,
                    quantity = 100,
                    notes = ""
                ), Transaction(
                    id = 944192,
                    date = 1698967422274,
                    type = "restock",
                    productId = 1,
                    quantity = 10,
                    notes = ""
                )
            )
            transactionDao.insertAll(defaultTransactions)
        }
    }

    suspend fun populateSuppliersIfEmpty() {
        val suppliers = supplierDao.getAllSuppliers().first()

        if (suppliers.isEmpty()) {
            val defaultSuppliers = listOf(
                Supplier(
                    id = 1,
                    name = "Global Supplies Inc.",
                    contactPerson = "Alice",
                    phone = "0723636312312",
                    email = "alice@globalsupplies.com",
                    address = "Bucharest"
                ),
                Supplier(
                    id = 2,
                    name = "TechTrade Comp.",
                    contactPerson = "Bob",
                    phone = "0723512536",
                    email = "bob@techtrade.co",
                    address = "Bucharest"
                ),
                Supplier(
                    id = 3,
                    name = "EcoGoods Ltd.",
                    contactPerson = "Carla",
                    phone = "0327871238",
                    email = "carla@ecogoods.org",
                    address = "Bucharest"
                )
            )
            supplierDao.insertAll(defaultSuppliers)
        }
    }


    // For the suppliers
    suspend fun getSupplierById(id: Int): Flow<Supplier?> {
        return supplierDao.getSupplierById(id)
    }

    suspend fun insertSupplier(supplier: Supplier) = supplierDao.insert(supplier)
    suspend fun updateSupplier(supplier: Supplier) {
        supplierDao.updateSupplier(supplier)
    }

    fun getAllSuppliers(): Flow<List<Supplier>> = supplierDao.getAllSuppliers()

    // For the transactions
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()
    fun getRecentTransactions(limit: Int = 3): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(limit)
    }
}