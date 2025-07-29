package com.example.stockmanagementapp.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.ProductDetailAction
import com.example.stockmanagementapp.presentation.ProductDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var saveButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onAction(ProductDetailAction.Init(productId))
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
                            text = "Product Detail",
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
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            state.product?.let {

                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var name by remember { mutableStateOf(it.name) }
                    var description by remember { mutableStateOf(it.description) }
                    var category by remember { mutableStateOf(it.category) }
                    saveButtonEnabled = (name != it.name) or (description != it.description) or (category != it.category)

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Product Name") },

                        value = name, onValueChange = { value ->
                            name = value
                        })


                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Product description") },

                        value = description, onValueChange = { value ->
                            description = value
                        })

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Product category") },

                        value = category, onValueChange = { value ->
                            category = value
                        })

                    Button(enabled = saveButtonEnabled, onClick = {
                        onAction(ProductDetailAction.SaveChanges(name,description,category))
                    }, content = {
                        Text("Save changes")
                    })
                }



            }


        }
    }

}