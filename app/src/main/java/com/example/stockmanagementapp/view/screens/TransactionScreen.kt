

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.stockmanagementapp.presentation.ProductListAction
import com.example.stockmanagementapp.presentation.ProductListState
import com.example.stockmanagementapp.presentation.TransactionHistoryAction
import com.example.stockmanagementapp.presentation.TransactionHistoryState

@Composable
fun TransactionScreen(
    state: TransactionHistoryState,
    onAction: (TransactionHistoryAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit){
        onAction(TransactionHistoryAction.Init)
    }

    LazyColumn {
        items(state.transactions) { transaction ->
            Text(text = transaction.type)
        }
    }
}