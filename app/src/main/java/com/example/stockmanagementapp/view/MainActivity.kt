package com.example.stockmanagementapp.view

import DashboardScreen
import TransactionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmanagementapp.presentation.DashboardViewModel
import com.example.stockmanagementapp.presentation.LoginViewModel
import com.example.stockmanagementapp.presentation.ProductListViewModel
import com.example.stockmanagementapp.presentation.TransactionHistoryViewModel
import com.example.stockmanagementapp.ui.theme.StockManagementAppTheme
import com.example.stockmanagementapp.view.navigator.ComposeNavigator
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import com.example.stockmanagementapp.view.screens.LoginScreen
import com.example.stockmanagementapp.view.screens.ProductListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockManagementAppTheme {

                val navController = rememberNavController()

                (navigator as? ComposeNavigator)?.navController = navController

                NavHost(navController, startDestination = Destination.Dashboard.route) {

                    composable(Destination.Login.route) {

                        val viewModel: LoginViewModel = hiltViewModel()
                        val state by viewModel.uiState.collectAsState()

                        LoginScreen(
                            state = state,
                            onAction = { action -> viewModel.onAction(action) },

                            )
                    }

                    composable(Destination.Dashboard.route) {
                        val viewModel: DashboardViewModel = hiltViewModel()
                        val state by viewModel.uiState.collectAsState()

                        DashboardScreen(
                            state = state,
                            onAction = { action -> viewModel.onAction(action) },

                            )
                    }

                    composable(Destination.ProductList.route) {
                        val viewModel: ProductListViewModel = hiltViewModel()
                        val state by viewModel.uiState.collectAsState()

                        ProductListScreen(
                            state = state,
                            onAction = { action -> viewModel.onAction(action) },

                            )
                    }

                    composable(Destination.TransactionHistory.route) {
                        val viewModel: TransactionHistoryViewModel = hiltViewModel()
                        val state by viewModel.uiState.collectAsState()

                        TransactionScreen(
                            state = state,
                            onAction = { action -> viewModel.onAction(action) },

                            )
                    }
                }


            }
        }
    }
}
