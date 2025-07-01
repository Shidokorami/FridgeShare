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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.requestlist.components.RequestItem

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

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
    floatingActionButton = {FloatingActionButton(
        onClick = { onNavigateToAddRequest(householdId)}
    ){
        Icon(Icons.Filled.Add, "Add request")
    }}
    ) { padding->
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
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                when (selectedTabIndex) {
                    0 -> {
                        LazyColumn(

                        ) {
                            items(activeRequests) { request ->
                                RequestItem(request)
                            }
                        }
                    }

                    1 -> {
                        LazyColumn() {
                            items(fulfilledRequests) { request ->
                                RequestItem(request)
                            }
                        }
                    }
                }
            }


        }
    }


}

