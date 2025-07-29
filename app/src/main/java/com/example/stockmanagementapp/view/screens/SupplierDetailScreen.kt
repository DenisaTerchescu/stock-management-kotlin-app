package com.example.stockmanagementapp.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.stockmanagementapp.presentation.SupplierDetailAction
import com.example.stockmanagementapp.presentation.SupplierDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierDetailScreen(
    supplierId: Int,
    state: SupplierDetailState,
    onAction: (SupplierDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var saveButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onAction(SupplierDetailAction.Init(supplierId))
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
                            text = "Supplier Detail",
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
            state.supplier?.let {

                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var name by remember { mutableStateOf(it.name) }
                    var email by remember { mutableStateOf(it.email) }
                    var phone by remember { mutableStateOf(it.phone) }
                    saveButtonEnabled =
                        (name != it.name) or (email != it.email) or (phone != it.phone)

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Supplier Name") },

                        value = name,
                        onValueChange = { value ->
                            name = value
                        })


                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Email") },

                        value = email,
                        onValueChange = { value ->
                            email = value
                        })

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Phone") },
                        value = phone,
                        onValueChange = { value ->
                            phone = value
                        })

                    Button(enabled = saveButtonEnabled, onClick = {
                        onAction(SupplierDetailAction.SaveChanges(name, email, phone))
                    }, content = {
                        Text("Save changes")
                    })
                }
            }

        }
    }

}