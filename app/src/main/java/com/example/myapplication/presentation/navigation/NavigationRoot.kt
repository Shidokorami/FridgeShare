package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.presentation.add_edit_product.AddEditProductScreenUi
import com.example.myapplication.presentation.add_edit_request.AddEditRequestScreenUi
import com.example.myapplication.presentation.householddetail.HouseholdDetailsContainerScreenUi
import com.example.myapplication.presentation.householdlist.HouseholdListScreenUi
import com.example.myapplication.presentation.loginscreen.LoginScreenUi
import com.example.myapplication.presentation.singup.SignUpScreenUi

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginScreen,
        modifier = modifier
    ) {
        composable<LoginScreen> {
            LoginScreenUi(
                onNavigateToSignUp = {navController.navigate(SignUpScreen)}
            )
        }

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

        composable<AddEditRequestScreen> { backStackEntry ->
            val addEditRequestScreen = backStackEntry.toRoute<AddEditRequestScreen>()
            val requestId = addEditRequestScreen.requestId
            val householdId = addEditRequestScreen.requestId

            AddEditRequestScreenUi(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<SignUpScreen> { backStackEntry ->
            SignUpScreenUi(
                onNavigateBack = {navController.popBackStack()}
            )
        }
    }
}