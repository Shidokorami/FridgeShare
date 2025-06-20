package com.example.myapplication.presentation.screen


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
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.viewmodel.HouseholdListViewModel
import com.example.myapplication.ui.theme.AppTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.HouseholdItem
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreen(
    viewModel: HouseholdListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    viewModel.getHouseholds()

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
                items(state.value.households){ household ->
                    HouseholdItem(household, modifier = Modifier)

                }
            }

        }
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    AppTheme {
        MainTopBar(
            modifier = Modifier,
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }

}

//@Preview
//@Composable
//fun MainPreview() {
//    AppTheme { HouseholdListScreen() }
//
//}