package com.example.myapplication.presentation.householddetail.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.presentation.navigation.HouseholdDetailNavItems
import com.example.myapplication.presentation.navigation.ProductsScreen
import com.example.myapplication.presentation.navigation.RequestsScreen
import com.example.myapplication.presentation.navigation.RoommatesScreen

@Composable
fun BottomNavigationBar(
    items: List<HouseholdDetailNavItems>,
    navController: NavController,
    currentHouseholdId: Long,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntryState = navController.currentBackStackEntryAsState()
    val currentBackStackEntry = currentBackStackEntryState.value

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val navDestinationObject: Any = when (item) {
                HouseholdDetailNavItems.Products -> ProductsScreen(currentHouseholdId)
                HouseholdDetailNavItems.Requests -> RequestsScreen(currentHouseholdId)
                HouseholdDetailNavItems.Roommates -> RoommatesScreen(currentHouseholdId)
            }

            val isSelected = currentBackStackEntry?.destination?.route?.startsWith(item.baseRoute) == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(navDestinationObject) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { item.icon() },
                label = { Text(item.label) }
            )
        }
    }
}