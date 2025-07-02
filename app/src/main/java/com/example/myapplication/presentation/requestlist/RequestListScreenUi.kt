package com.example.myapplication.presentation.requestlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.presentation.requestlist.components.FulfillRequestDialog
import com.example.myapplication.presentation.requestlist.components.RequestItem
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreenUi(
    householdId: String,
    viewModel: RequestListViewModel = hiltViewModel(),
    onNavigateToEditRequest: (String, String) -> Unit,
    onNavigateToAddRequest: (String) -> Unit
) {

    val activeRequests by viewModel.unfulfilledRequests.collectAsState()
    val fulfilledRequests by viewModel.fulfilledRequests.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // State for showing dialogs:
    var showDeleteDialog by remember { mutableStateOf(false) }
    var requestToDelete by remember { mutableStateOf<ProductRequest?>(null) }

    var showFulfillDialog by remember { mutableStateOf(false) }
    var requestToFulfill by remember { mutableStateOf<ProductRequest?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddRequest(householdId) }
            ) {
                Icon(Icons.Filled.Add, "Add request")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().consumeWindowInsets(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Active", style = MaterialTheme.typography.titleLarge) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Fulfilled", style = MaterialTheme.typography.titleLarge) }
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                when (selectedTabIndex) {
                    0 -> {
                        LazyColumn {
                            items(activeRequests) { request ->
                                RequestItem(
                                    request = request,
                                    onClickedDelete = {
                                        requestToDelete = request
                                        showDeleteDialog = true
                                    },
                                    onClickedFulfill = {
                                        requestToFulfill = request
                                        showFulfillDialog = true
                                    }
                                )
                            }
                        }
                    }
                    1 -> {
                        LazyColumn {
                            items(fulfilledRequests) { request ->
                                RequestItem(
                                    request = request,
                                    onClickedDelete = {
                                        requestToDelete = request
                                        showDeleteDialog = true
                                    },
                                    onClickedFulfill = {
                                        requestToFulfill = request
                                        showFulfillDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog && requestToDelete != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this request?") },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    // TU dopisz wywołanie use case lub event do viewmodelu
                    viewModel.onEvent(RequestListEvent.OnDeleteRequestClick(requestToDelete!!.id))
                    showDeleteDialog = false
                    requestToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showDeleteDialog = false
                    requestToDelete = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Fulfill request dialog
    if (showFulfillDialog && requestToFulfill != null) {
        FulfillRequestDialog(
            request = requestToFulfill!!,
            onDismiss = {
                showFulfillDialog = false
                requestToFulfill = null
            },
            onConfirm = { quantity, unit, name, price ->
                // Stwórz zaktualizowany obiekt ProductRequest
                val updatedRequest = requestToFulfill!!.copy(
                    name = name,
                    quantity = quantity.toBigDecimalOrNull(),
                    unit = unit,
                    price = price.toBigDecimalOrNull(),
                    fulfilled = true,
                    fulfilledDate = System.currentTimeMillis(),
                    fulfilledBy = FirebaseAuth.getInstance().currentUser?.uid
                    // Możesz dodać więcej pól jeśli trzeba, np. fulfillerName itd.
                )

                viewModel.onEvent(RequestListEvent.OnConfirmFulfillRequest(updatedRequest))

                showFulfillDialog = false
                requestToFulfill = null
            }
        )
    }

}
