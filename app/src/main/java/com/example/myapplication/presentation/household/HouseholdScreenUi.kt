package com.example.myapplication.presentation.household

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.household.components.ProductItem
import com.example.myapplication.presentation.householdlist.MainTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdScreenUi(
    householdId: Long,
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
        content = { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(state.value.products){ product ->
                    ProductItem(
                        product,
                        modifier = Modifier

                    )

                }
            }

        }
    )
}