package com.example.stockmanagementapp.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmanagementapp.presentation.ProductListViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val products by viewModel.products.collectAsState()

    LazyColumn {
        items(products) { product ->
            Text(text = product.name)
        }
    }
}