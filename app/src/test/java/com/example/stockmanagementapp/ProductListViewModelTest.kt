package com.example.stockmanagementapp

import android.content.Context
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.presentation.ProductListAction
import com.example.stockmanagementapp.presentation.ProductListViewModel
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.utils.getCachedProductList
import com.example.stockmanagementapp.utils.saveProductListToPrefs
import com.example.stockmanagementapp.view.navigator.Navigator
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository = mockk<StockRepository>()
    private val mockNavigator = mockk<Navigator>(relaxed = true)
    private lateinit var viewModel: ProductListViewModel

    private val context = mockk<Context>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        mockkStatic("com.example.stockmanagementapp.utils.UtilsKt")
        viewModel = ProductListViewModel(mockRepository, mockNavigator)
    }

    val products = listOf(
        Product(
            1,
            "Apple",
            "It keeps the doctor away",
            5.0,
            "Fruit",
            barcode = "",
            supplierId = 0,
            currentStockLevel = 15,
            minimumStockLevel = 5
        ), Product(
            2, "Candy", "Something something", 2.0, "Sweets",
            barcode = "",
            supplierId = 1,
            currentStockLevel = 10,
            minimumStockLevel = 2,
        )
    )

    @Test
    fun `Searching product list by name was successfully completed`() = runTest {
        every { mockRepository.getAllProducts() } returns flow { emit(products) }
        every { getCachedProductList(context) } returns emptyList()
        every { saveProductListToPrefs(context, products) } just Runs

        viewModel.onAction(ProductListAction.FetchProducts(context))

        advanceUntilIdle()

        viewModel.onAction(ProductListAction.SearchProduct("apple"))
        val state = viewModel.uiState.value

        assertEquals(1, state.products.size)
        assertEquals("Apple", state.products.first().name)
    }

    @Test
    fun `After getting the products from the db, it saves the products to cache`() = runTest {
        every { mockRepository.getAllProducts() } returns flow { emit(products) }

        every { saveProductListToPrefs(context, products) } just Runs
        every { getCachedProductList(context) } returns emptyList()

        viewModel.onAction(ProductListAction.FetchProducts(context))

        advanceUntilIdle()

        Assert.assertEquals(products, viewModel.uiState.value.products)
        verify { saveProductListToPrefs(context, products) }
    }

    @Test
    fun `On error, it retrieves products from cache successfully`() = runTest {
        every { mockRepository.getAllProducts() } returns flow {
            throw IOException("No internet connection")
        }

        every { getCachedProductList(context) } returns products

        viewModel.onAction(ProductListAction.FetchProducts(context))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(products, state.products)
        assertNull(state.error)

        verify { getCachedProductList(context) }
    }

}