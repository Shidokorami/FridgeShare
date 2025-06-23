// app/src/main/java/com/example/myapplication/presentation/navigation/NavigationRoot.kt

package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute // Ważne dla odczytywania argumentów
import com.example.myapplication.presentation.household.HouseholdScreenUi
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
            val args = backStackEntry.toRoute<HouseholdScreen>()
            HouseholdScreenUi(householdId = args.householdId)
        }
    }
}