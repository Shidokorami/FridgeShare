package com.example.myapplication.presentation.add_edit_product

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.add_edit_product.AddEditProductViewModel.UiEvent
import com.example.myapplication.presentation.add_edit_product.components.AddEditTextField
import com.example.myapplication.presentation.add_edit_product.components.UnitDropdownMenu
import com.example.myapplication.presentation.householdlist.MainTopBar
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreenUi(
    viewModel: AddEditProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val name by viewModel.productName.collectAsState()
    val quantity by viewModel.productQuantity.collectAsState()
    val unit by viewModel.productUnit.collectAsState()
    val isDropdownExpanded by viewModel.isUnitDropdownExpanded.collectAsState()
    val expirationDate by viewModel.productExpirationDate.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {


                is UiEvent.SaveProductSuccess -> {
                    onNavigateBack()
                }

                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    Scaffold(
        topBar = {
            MainTopBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior
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
                        onValueChange = { viewModel.onEvent(AddEditProductEvent.EnteredName(it)) },
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
                            onValueChange = { viewModel.onEvent(AddEditProductEvent.EnteredQuantity(it)) },
                            modifier = Modifier.weight(1f)
                        )

                        UnitDropdownMenu(
                            selectedUnit = unit,
                            isExpanded = isDropdownExpanded,
                            onExpandedChange = { expanded ->
                                Log.d("DropdownClickDebug", "onExpandedChange called with: $expanded")
                                viewModel.onEvent(AddEditProductEvent.ChangeUnitDropdownExpanded(expanded))
                            },
                            onUnitSelected = { unitValue ->
                                viewModel.onEvent(AddEditProductEvent.ChangeUnitSelection(unitValue))
                            },
                            modifier = Modifier.weight(0.7f)
                        )
                    }
                }

                item {
                    Button(
                        onClick = { viewModel.onEvent(AddEditProductEvent.SaveProduct) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    )
}