package com.example.stockmanagementapp.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.ProductListAction
import com.example.stockmanagementapp.presentation.ProductListState
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    state: ProductListState,
    onAction: (ProductListAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onAction(ProductListAction.FetchProducts)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "Product List",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 8.dp),
                            color = Color.White
                        )

                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF66BB6A), titleContentColor = Color.Black
                )

            )
        }

    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(state.products) {
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                    ElevatedCard(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(text = "Product id: ${it.id}")
                            Text(text = "Product name: ${it.name}")
                            Text(text = "Product description: ${it.description}")
                            Text(text = "Product price: ${it.price}")
                            Text(text = "Stock level: ${it.currentStockLevel}")
                        }

                    }

                }
            }
        }
    }
}