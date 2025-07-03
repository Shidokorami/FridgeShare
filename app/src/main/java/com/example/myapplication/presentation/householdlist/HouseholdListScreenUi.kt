package com.example.myapplication.presentation.householdlist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.AppTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.householdlist.components.HouseholdItem
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.presentation.householdlist.components.CreateHouseholdDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreenUi(
    onHouseholdClick: (String) -> Unit,
    viewModel: HouseholdListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    var showCreateHouseholdDialog by remember { mutableStateOf(false) }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            MainTopBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {showCreateHouseholdDialog = true }) {
                Icon(Icons.Filled.Add, "Create a new household")
            }
        },

        content = { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(state.value.households){ household ->
                    HouseholdItem(
                        household,
                        modifier = Modifier
                            .clickable{

                                onHouseholdClick(household.id)
                            }
                    )

                }
            }

        }
    )

    if (showCreateHouseholdDialog) {
        CreateHouseholdDialog(
            onDismiss = { showCreateHouseholdDialog = false },
            onCreateClick = { householdName ->
                viewModel.onEvent(HouseholdListEvent.ClickedCreateButton(householdName))
                showCreateHouseholdDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title =
            {
                Text(text = "To jest topbar")
            },
        navigationIcon = {
            IconButton(
                onClick = {/* TODO: Add navigation logic */},

                ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

