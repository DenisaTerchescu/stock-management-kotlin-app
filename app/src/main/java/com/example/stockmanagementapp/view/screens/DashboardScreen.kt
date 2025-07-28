import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmanagementapp.presentation.DashboardAction
import com.example.stockmanagementapp.presentation.DashboardState
import com.example.stockmanagementapp.ui.theme.StockManagementAppTheme
import com.example.stockmanagementapp.view.components.FeatureIconButton
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onAction(DashboardAction.Init)
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
                            text = "Dashboard",
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
                    state.lowStockItems.forEach {
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
                    state.recentTransactions.forEach {
                        val transactionDate = dateFormat.format(it.date)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = it.type, fontSize = 16.sp)
                            Spacer(modifier.width(8.dp))
                            Text(
                                text = transactionDate,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }

            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                FeatureIconButton(
                    backgroundColor = Color(0xFF66BB6A),
                    size = 150.dp,
                    label = "Product List",
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
                    .align(Alignment.CenterHorizontally)
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
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    StockManagementAppTheme {
        DashboardScreen(
            state = DashboardState(), onAction = {})
    }
}