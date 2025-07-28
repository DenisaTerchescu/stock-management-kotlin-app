package com.example.stockmanagementapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun LowStockItemsSections(
    lowStockItems: List<Product>,
   recentTransactions: List<Transaction>,
    modifier: Modifier = Modifier,
){
    ElevatedCard(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .padding(
                18.dp
            )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Low stock items",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            lowStockItems.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = it.name, fontSize = 16.sp)
                    Spacer(modifier.width(8.dp))
                    Text(
                        text = it.currentStockLevel.toString(),
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Recent transactions",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            recentTransactions.forEach {
                val transactionDate = dateFormat.format(it.date)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = it.type, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = transactionDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

    }
}