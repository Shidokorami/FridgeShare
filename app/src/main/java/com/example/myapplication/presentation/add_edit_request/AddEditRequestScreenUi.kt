package com.example.myapplication.presentation.add_edit_request

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.add_edit_product.AddEditProductViewModel.UiEvent
import com.example.myapplication.presentation.add_edit_product.components.DeleteProductConfirmationDialog
import com.example.myapplication.presentation.util.AddEditTextField
import com.example.myapplication.presentation.util.UnitDropdownMenu
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRequestScreenUi(
    viewModel: AddEditRequestViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val name by viewModel.requestName.collectAsState()
    val quantity by viewModel.requestQuantity.collectAsState()
    val unit by viewModel.requestUnit.collectAsState()
    val isDropdownExpanded by viewModel.isUnitDropdownExpanded.collectAsState()
    //val expirationDate by viewModel.requestExpirationDate.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()

    val householdName = viewModel.householdName.collectAsState().value

    val showDeleteConfirmationDialog by viewModel.showDeleteConfirmationDialog.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {


                is UiEvent.SaveProductSuccess -> {
                    onNavigateBack()
                }

                is UiEvent.DeleteProductSuccess -> {

                    onNavigateBack()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )

                }

                AddEditRequestViewModel.UiEvent.DeleteRequestSuccess -> {
                    onNavigateBack()
                }

                AddEditRequestViewModel.UiEvent.SaveRequestSuccess -> {
                    onNavigateBack()
                }

                is AddEditRequestViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    dismissActionContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = householdName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                    AddEditTextField(
                        text = name,
                        label = "Product Name",
                        onValueChange = { viewModel.onEvent(AddEditRequestEvent.EnteredName(it)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AddEditTextField(
                            text = quantity,
                            label = "Quantity",
                            onValueChange = {
                                viewModel.onEvent(
                                    AddEditRequestEvent.EnteredQuantity(
                                        it
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )

                        UnitDropdownMenu(
                            selectedUnit = unit,
                            isExpanded = isDropdownExpanded,
                            onExpandedChange = { expanded ->
                                viewModel.onEvent(
                                    AddEditRequestEvent.ChangeUnitDropdownExpanded(
                                        expanded
                                    )
                                )
                            },
                            onUnitSelected = { unitValue ->
                                viewModel.onEvent(AddEditRequestEvent.ChangeUnitSelection(unitValue))
                            },
                            modifier = Modifier.weight(0.7f)
                        )
                    }
                }

//                item {
//                    ExpirationDatePicker(
//                        selectedDate = expirationDate,
//                        onDateSelected = { newDate ->
//                            viewModel.onEvent(AddEditRequestEvent.ChangeExpirationDate(newDate))
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }

                item {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (isEditing) {
                            Button(
                                onClick = { viewModel.onEvent(AddEditRequestEvent.ShowDeleteConfirmation) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xffdb042f),
                                    contentColor = MaterialTheme.colorScheme.onError
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.onEvent(AddEditRequestEvent.SaveProduct) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .weight(2f)
                        ) {
                            Text("Save")
                        }
                    }

                }
            }
        }
    )

    if (showDeleteConfirmationDialog) {
        DeleteProductConfirmationDialog(
            onDismissRequest = { viewModel.onEvent(AddEditRequestEvent.HideDeleteConfirmation) },
            onConfirmation = { viewModel.onEvent(AddEditRequestEvent.DeleteProduct) },
            dialogTitle = "Confirm Deletion",
            dialogText = "Are you sure you want to delete this request? This action cannot be undone."
        )

    }
}