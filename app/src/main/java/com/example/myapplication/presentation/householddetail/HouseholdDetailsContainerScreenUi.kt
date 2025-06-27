package com.example.myapplication.presentation.householddetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.presentation.householddetail.components.BottomNavigationBar
import com.example.myapplication.presentation.householduserlist.HouseholdUserListScreenUi
import com.example.myapplication.presentation.navigation.AddEditProductScreen
import com.example.myapplication.presentation.navigation.HouseholdDetailNavItems
import com.example.myapplication.presentation.navigation.ProductsScreen
import com.example.myapplication.presentation.navigation.RequestsScreen
import com.example.myapplication.presentation.navigation.RoommatesScreen
import com.example.myapplication.presentation.productlist.ProductListScreenUi
import com.example.myapplication.presentation.requestlist.RequestListScreenUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdDetailsContainerScreenUi(
    householdId: Long, // householdId jest typu Long
    onBackClick: () -> Unit, // Callback do nawigacji "wstecz" na głównym NavControllerze
    mainNavController: NavController, // Główny NavController do nawigacji poza tym kontenerem
    viewModel: HouseholdDetailsViewModel = hiltViewModel() // ViewModel dla HouseholdDetails
) {

    val householdNavHostController = rememberNavController()
    val householdName = viewModel.householdName.collectAsState().value

    Scaffold(
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
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = HouseholdDetailNavItems.entries,
                navController = householdNavHostController,
                currentHouseholdId = householdId
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = householdNavHostController,
            startDestination = ProductsScreen(householdId),
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<ProductsScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<ProductsScreen>()
                val currentHouseholdId = args.householdId

                ProductListScreenUi(
                    householdId = currentHouseholdId,
                    onNavigateToAddProduct = { currentHouseholdIdFromProductList ->
                        mainNavController.navigate(
                            AddEditProductScreen(
                                productId = -1L,
                                householdId = currentHouseholdIdFromProductList
                            )
                        )
                    },
                    onNavigateToEditProduct = { productId, currentHouseholdIdFromProductList ->
                        mainNavController.navigate(
                            AddEditProductScreen(
                                productId = productId,
                                householdId = currentHouseholdIdFromProductList
                            )
                        )
                    }
                )
            }
            composable<RequestsScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<RequestsScreen>()
                val currentHouseholdId = args.householdId

                RequestListScreenUi(
                    householdId = currentHouseholdId // Przekazujemy ID do ekranu
                    // Dodaj potrzebne callbacki dla RequestListScreenUi
                )
            }

            composable<RoommatesScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<RoommatesScreen>()
                val currentHouseholdId = args.householdId

                HouseholdUserListScreenUi(
                    householdId = currentHouseholdId
                )
            }
        }
    }
}