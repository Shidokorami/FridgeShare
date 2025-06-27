package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.presentation.add_edit_product.AddEditProductScreenUi
import com.example.myapplication.presentation.householddetail.HouseholdDetailsContainerScreenUi
import com.example.myapplication.presentation.householdlist.HouseholdListScreenUi

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HouseholdListScreen,
        modifier = modifier
    ) {
        composable<HouseholdListScreen> {
            HouseholdListScreenUi(
                onHouseholdClick = { householdId ->
                    navController.navigate(HouseholdScreen(householdId))
                }
            )
        }

        composable<HouseholdScreen> { backStackEntry ->
            val householdScreen = backStackEntry.toRoute<HouseholdScreen>()
            val householdId = householdScreen.householdId

            HouseholdDetailsContainerScreenUi(
                householdId = householdId,
                onBackClick = { navController.popBackStack() },
                mainNavController = navController
            )
        }

        composable<AddEditProductScreen> { backStackEntry ->
            val addEditProductScreen = backStackEntry.toRoute<AddEditProductScreen>()
            val productId = addEditProductScreen.productId
            val householdId = addEditProductScreen.householdId

            AddEditProductScreenUi(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}