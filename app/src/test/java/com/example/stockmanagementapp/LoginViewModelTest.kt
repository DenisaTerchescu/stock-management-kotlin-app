package com.example.stockmanagementapp

import com.example.stockmanagementapp.presentation.LoginAction
import com.example.stockmanagementapp.presentation.LoginViewModel
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: StockRepository
    private lateinit var navigator: Navigator
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        navigator = mockk(relaxed = true)
        viewModel = LoginViewModel(repository, navigator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the user is validated successfully`() = runTest {
        viewModel.onAction(LoginAction.ValidateUsername("testUser"))
        val state = viewModel.uiState.value

        assertEquals("testUser", state.username)
        assertNull(state.error)
    }

    @Test
    fun `when the password is validated successfully`() = runTest {
        viewModel.onAction(LoginAction.ValidatePassword("testPass"))
        val state = viewModel.uiState.value

        assertEquals("testPass", state.password)
        assertNull(state.error)
    }

    @Test
    fun `when the navigation to the dashboard screen is correct`() = runTest {
        viewModel.onAction(LoginAction.ValidateUsername("admin"))
        viewModel.onAction(LoginAction.ValidatePassword("admin"))

        viewModel.onAction(LoginAction.NavigateToDashboard)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { navigator.navigateTo(Destination.Dashboard.route) }
    }

    @Test
    fun `when navigating to the dahsboard screen with incorrect credentials shows an error`() =
        runTest {
            viewModel.onAction(LoginAction.ValidateUsername("wrongUser"))
            viewModel.onAction(LoginAction.ValidatePassword("wrongPassword"))

            viewModel.onAction(LoginAction.NavigateToDashboard)

            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            Assert.assertEquals("Incorrect credentials!", state.error)
        }
}
