import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockmanagementapp.presentation.SupplierListAction
import com.example.stockmanagementapp.presentation.SupplierListState
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierListScreen(
    state: SupplierListState, onAction: (SupplierListAction) -> Unit, modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onAction(SupplierListAction.FetchSuppliers)
    }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Supplier List",
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
            TextField(
                value = state.searchValue,
                onValueChange = { onAction(SupplierListAction.SearchSupplier(it))  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search supplier") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") }
            )

            LazyColumn {
                items(state.supplierList) {
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                    ElevatedCard(modifier = Modifier
                        .clickable {
                            onAction(SupplierListAction.NavigateToSupplierDetailScreen(it.id))
                        }
                        .padding(8.dp)
                        .fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(text = "Supplier id: ${it.id}")
                            Text(text = "Supplier name: ${it.name}")
                            Text(text = "Contact Person: ${it.contactPerson}")
                            Text(text = "Phone: ${it.phone}")
                            Text(text = "Address: ${it.address}")
                        }

                    }

                }
            }
        }
    }
}