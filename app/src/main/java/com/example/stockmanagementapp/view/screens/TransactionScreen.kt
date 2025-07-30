import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.TransactionHistoryAction
import com.example.stockmanagementapp.presentation.TransactionHistoryState
import com.example.stockmanagementapp.presentation.TransactionType
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    state: TransactionHistoryState,
    onAction: (TransactionHistoryAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onAction(TransactionHistoryAction.Init)
    }

    val context = LocalContext.current

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
                            text = "Transaction History",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(TransactionHistoryAction.ExportToCSV(context))
                },
            ) {
                Icon(
                    Icons.Filled.Share, "Share transaction history",
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier.padding(8.dp)
            ) {
                FilterChip(
                    modifier = Modifier.padding(4.dp),
                    selected = state.selectedFilter == TransactionType.SALE,
                    onClick = {
                        onAction(TransactionHistoryAction.FilterTransactionsBy(TransactionType.SALE))
                    },
                    label = {
                        Text("Sale")
                    })

                FilterChip(
                    modifier = Modifier.padding(4.dp),
                    selected = state.selectedFilter == TransactionType.RESTOCK,
                    onClick = {
                        onAction(TransactionHistoryAction.FilterTransactionsBy(TransactionType.RESTOCK))
                    },
                    label = {
                        Text("Restock")
                    })

            }
            LazyColumn {
                items(state.transactions) { transaction ->
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                    val transactionDate = dateFormat.format(transaction.date)
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            Text(text = "Transaction id: ${transaction.id}")
                            Text(text = "Transaction type: ${transaction.type}")
                            Text(text = "Transaction date: $transactionDate")
                        }

                    }

                }
            }
        }
    }
}