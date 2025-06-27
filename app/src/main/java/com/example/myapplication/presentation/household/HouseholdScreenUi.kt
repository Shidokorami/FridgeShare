package com.example.myapplication.presentation.household

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.household.components.ProductItem
import com.example.myapplication.presentation.householdlist.MainTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdScreenUi(
    householdId: Long,
    onNavigateToAddProduct: (Long) -> Unit,
    onNavigateToEditProduct: (Long, Long) -> Unit,
    viewModel: HouseholdViewModel = hiltViewModel()
){

    val state = viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            MainTopBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddProduct(householdId) }) {
                Icon(Icons.Filled.Add, "Add product")
            }
        },
        content = { padding ->
            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(state.value.products) { product ->
                        ProductItem(
                            product = product,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    product.id?.let { productId ->
                                        onNavigateToEditProduct(productId, householdId)}
                                }
                        )
                    }
                }
            }
        }
    )
}