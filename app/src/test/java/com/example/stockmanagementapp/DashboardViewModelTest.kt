package com.example.stockmanagementapp

import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Transaction
import com.example.stockmanagementapp.presentation.DashboardAction
import com.example.stockmanagementapp.presentation.DashboardViewModel
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

@ExperimentalCoroutinesApi
class DashboardViewModelTest {


    private val repository = mockk<StockRepository>(relaxed = true)
    private val navigator = mockk<Navigator>(relaxed = true)

    private lateinit var viewModel: DashboardViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository, navigator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Successful fetch of low stock items and of recent transactions`() = runTest {
        val lowStockProducts = listOf(
            Product(
                id = 1,
                name = "Prod1",
                description = "",
                price = 0.0,
                category = "",
                barcode = "",
                supplierId = 0,
                currentStockLevel = 2,
                minimumStockLevel = 5
            ),
            Product(id = 2, name = "Prod2", description = "", price = 0.0, category = "", barcode = "", supplierId = 0, currentStockLevel = 1, minimumStockLevel = 3),
        )
        val recentTransactions = listOf(
            Transaction(id = 1, date = 0L, type = "sale", productId = 1, quantity = 5, notes = ""),
            Transaction(id = 2, date = 0L, type = "restock", productId = 2, quantity = 10, notes = "")
        )

        coEvery { repository.getLowStockProducts() } returns flowOf(lowStockProducts)
        coEvery { repository.getRecentTransactions(3) } returns flowOf(recentTransactions)

        viewModel.onAction(DashboardAction.Init)
        testScheduler.advanceUntilIdle()

        assertEquals(lowStockProducts, viewModel.uiState.value.lowStockItems)
        assertEquals(recentTransactions, viewModel.uiState.value.recentTransactions)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `Fetching the low stock items from the database throws an error`() = runTest {
        val errorMessage = "Database error"
        coEvery { repository.getLowStockProducts() } returns flow { throw Exception(errorMessage) }
        coEvery { repository.getRecentTransactions(3) } returns flowOf(emptyList())

        viewModel.onAction(DashboardAction.Init)
        testScheduler.advanceUntilIdle()

        assertEquals(errorMessage, viewModel.uiState.value.error)
    }

    @Test
    fun `Fetching the recent transactions throws an error`() = runTest {
        val errorMessage = "Transaction fetch error"
        coEvery { repository.getLowStockProducts() } returns flowOf(emptyList())
        coEvery { repository.getRecentTransactions(3) } returns flow { throw Exception(errorMessage) }

        viewModel.onAction(DashboardAction.Init)
        testScheduler.advanceUntilIdle()

        assertEquals(errorMessage, viewModel.uiState.value.error)
    }

    @Test
    fun `Navigation to the stock management screen is successful`() = runTest {
        viewModel.onAction(DashboardAction.NavigateToStockManagement)
        testScheduler.advanceUntilIdle()

        coVerify { navigator.navigateTo(Destination.StockManagement.route) }
    }
}