package com.example.myapplication.presentation.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.productlist.components.ProductItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreenUi(
    householdId: Long,
    onNavigateToAddProduct: (Long) -> Unit,
    onNavigateToEditProduct: (Long, Long) -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddProduct(householdId) }) {
                Icon(Icons.Filled.Add, "Add product")
            }
        },
        content = { padding ->
            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(60.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
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
                                        onNavigateToEditProduct(productId, householdId)
                                    }
                                }
                        )
                    }
                }
            }
        }
    )
}