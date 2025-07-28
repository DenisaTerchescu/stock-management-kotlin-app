package com.example.stockmanagementapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.R
import com.example.stockmanagementapp.presentation.DashboardAction


@Composable
fun FeatureMainSection(
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier,
){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FeatureIconButton(
            backgroundColor = Color(0xFF66BB6A),
            size = 150.dp,
            label = stringResource(R.string.product_list_label),
            icon = Icons.Rounded.Favorite,
            contentDescription = "Product list entry point",
            onClick = { onAction(DashboardAction.NavigateToProductList) })


        FeatureIconButton(
            backgroundColor = Color(0xFF66BB6A),
            size = 150.dp,
            label = "Supplier List",
            icon = Icons.Rounded.Face,
            contentDescription = "Supplier List entry point",
            onClick = { onAction(DashboardAction.NavigateToSupplierList) })

    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FeatureIconButton(
            backgroundColor = Color(0xFF66BB6A),
            size = 150.dp,
            label = "Transaction History",
            icon = Icons.Default.DateRange,
            contentDescription = "Transaction history entry point",
            onClick = { onAction(DashboardAction.NavigateToTransactionHistory) })

        FeatureIconButton(
            backgroundColor = Color(0xFF66BB6A),
            size = 150.dp,
            label = "Stock Management",
            icon = Icons.Default.Build,
            contentDescription = "Stock Management entry point",
            onClick = { onAction(DashboardAction.NavigateToStockManagement) })

    }
}