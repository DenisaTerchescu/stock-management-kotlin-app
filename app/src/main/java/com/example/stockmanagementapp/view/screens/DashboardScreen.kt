import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.DashboardAction
import com.example.stockmanagementapp.presentation.DashboardState
import com.example.stockmanagementapp.ui.theme.StockManagementAppTheme
import com.example.stockmanagementapp.view.components.FeatureIconButton
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier,
) {

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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF66BB6A), // light green background
                    titleContentColor = Color.Black // fallback, in case text color isn't set directly
                )

            )
        }

    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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