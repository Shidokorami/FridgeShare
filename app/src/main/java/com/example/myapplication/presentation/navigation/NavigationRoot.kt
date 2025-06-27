// app/src/main/java/com/example/myapplication/presentation/navigation/NavigationRoot.kt

package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.presentation.add_edit_product.AddEditProductScreenUi
import com.example.myapplication.presentation.household.HouseholdScreenUi
import com.example.myapplication.presentation.householdlist.HouseholdListScreenUi
import com.example.myapplication.presentation.navigation.AddEditProductScreen

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

            HouseholdScreenUi(
                householdId = householdId,
                onNavigateToAddProduct = { currentHouseholdId -> // Z householdId z ekranu
                    navController.navigate(AddEditProductScreen(productId = -1L, householdId = currentHouseholdId))
                },
                onNavigateToEditProduct = { productId, currentHouseholdId -> // Z productId i householdId z ekranu
                    navController.navigate(AddEditProductScreen(productId = productId, householdId = currentHouseholdId))
                }
            )
        }

        composable<AddEditProductScreen> { backStackEntry ->
            // productId i householdId są pobierane w ViewModelu z SavedStateHandle
            // Nie musimy ich jawnie przekazywać do AddEditProductScreenUi,
            // ale musimy je odczytać z trasy, aby były dostępne w SavedStateHandle
            val addEditProductScreen = backStackEntry.toRoute<AddEditProductScreen>()
            val productId = addEditProductScreen.productId // Pamiętaj, aby odczytać, nawet jeśli nie przekazujesz jawnie
            val householdId = addEditProductScreen.householdId // Pamiętaj, aby odczytać

            AddEditProductScreenUi(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}