package com.example.stockmanagementapp.view.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.StockManagementAction
import com.example.stockmanagementapp.presentation.StockManagementState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockManagementScreen(
    state: StockManagementState,
    onAction: (StockManagementAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var saveButtonEnabled by remember { mutableStateOf(false) }
    var newTransaction by remember { mutableStateOf(state.transaction) }

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
                            text = "Stock Management",
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
            state.transaction.let {

                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var type by remember { mutableStateOf(it.type) }
                    var date by remember { mutableLongStateOf(it.date) }
                    var productId by remember { mutableStateOf(it.productId.toString()) }
                    var quantity by remember { mutableStateOf(it.quantity.toString()) }
                    var notes by remember { mutableStateOf(it.notes) }

                    saveButtonEnabled =
                        (quantity.isNotEmpty()) and (productId.isNotEmpty())



                    StockTypeDropdown(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        selectedType = type,
                        onTypeSelected = { newType -> type = newType }
                    )



                    DateInputField(
                        dateMillis = date, onDateSelected = { date = it })

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Product id") },

                        value = productId,
                        onValueChange = { value ->
                            productId = value
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                        )
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Stock quantity") },

                        value = quantity,
                        onValueChange = { value ->
                            quantity = value
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                        )
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Notes") },

                        value = notes,
                        onValueChange = { value ->
                            notes = value
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        )
                    )
                    Button(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        enabled = saveButtonEnabled, onClick = {
                        onAction(
                            StockManagementAction.Save(
                                newTransaction.copy(
                                    date = date,
                                    type = type,
                                    productId = productId.toInt(),
                                    quantity = quantity.toInt(),
                                    notes = notes
                                )
                            )
                        )
                    }, content = {
                        Text("Add the new stock")
                    })
                }


            }


        }
    }

}

@Composable
fun DateInputField(
    dateMillis: Long, onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { timeInMillis = dateMillis }

    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val formattedDate = remember(dateMillis) { dateFormat.format(Date(dateMillis)) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            onDateSelected(selectedCalendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    TextField(
        value = formattedDate,
        onValueChange = {},
        label = { Text("Stock date") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        readOnly = true,
        trailingIcon = {
            Icon(
                Icons.Default.DateRange, contentDescription = "",
                modifier = Modifier.clickable { datePickerDialog.show() },
            )
        })
}


@Composable
fun StockTypeDropdown(
    selectedType: String, onTypeSelected: (String) -> Unit, modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("restock", "sale")

    Box(modifier) {
        TextField(
            value = selectedType,
            onValueChange = {},
            label = { Text("Stock type") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = true },  )
            })
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = {
                    Text(option)
                }, onClick = {
                    onTypeSelected(option)
                    expanded = false
                })
            }
        }
    }
}


