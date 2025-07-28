package com.example.stockmanagementapp.view.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.stockmanagementapp.presentation.ProductListAction
import com.example.stockmanagementapp.presentation.ProductListState

@Composable
fun ProductListScreen(
    state: ProductListState,
    onAction: (ProductListAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit){
        onAction(ProductListAction.FetchProducts)
    }

    LazyColumn {
        items(state.products) { product ->
            Text(text = product.name)
        }
    }
}