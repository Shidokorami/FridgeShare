package com.example.myapplication.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.AppTheme

private data class PreviewDestination(
    val route: String,
    val icon: @Composable () -> Unit,
    val label: String
)

private val previewDestinations = listOf(
    PreviewDestination("products", { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Products") }, "Products"),
    PreviewDestination("requests", { Icon(Icons.Default.ShoppingCart, contentDescription = "Requests") }, "Requests"),
    PreviewDestination("users", { Icon(Icons.Default.People, contentDescription = "Users") }, "Users")
)

@Composable
fun NavigationBottomBarPreview(
    selectedIndex: Int = 0
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        previewDestinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { },
                icon = destination.icon,
                label = { Text(destination.label) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_NavigationBottomBar() {
    AppTheme {
        NavigationBottomBarPreview(selectedIndex = 0)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_NavigationBottomBar_RequestsSelected() {
    AppTheme {
        NavigationBottomBarPreview(selectedIndex = 1)
    }
}