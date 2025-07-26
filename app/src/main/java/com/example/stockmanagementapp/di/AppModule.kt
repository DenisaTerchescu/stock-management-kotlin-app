package com.example.stockmanagementapp.di

import android.app.Application
import androidx.room.Room
import com.example.stockmanagementapp.data.dao.ProductDao
import com.example.stockmanagementapp.data.dao.SupplierDao
import com.example.stockmanagementapp.data.dao.TransactionDao
import com.example.stockmanagementapp.data.db.StockDatabase
import com.example.stockmanagementapp.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(app, StockDatabase::class.java, "stock_db").build()
    }

    @Provides
    fun provideProductDao(db: StockDatabase): ProductDao = db.productDao()
    @Provides
    fun provideSupplierDao(db: StockDatabase): SupplierDao = db.supplierDao()
    @Provides
    fun provideTransactionDao(db: StockDatabase): TransactionDao = db.transactionDao()

    @Singleton
    @Provides
    fun provideInventoryRepository(
        productDao: ProductDao,
        supplierDao: SupplierDao,
        transactionDao: TransactionDao
    ): StockRepository {
        return StockRepository(productDao, supplierDao, transactionDao)
    }
}