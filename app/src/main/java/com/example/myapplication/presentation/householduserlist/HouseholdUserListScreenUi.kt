package com.example.myapplication.presentation.householduserlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.model.User

@Composable
fun HouseholdUserListScreenUi(
    householdId: String,
    viewModel: HouseholdUserListViewModel = hiltViewModel()
) {
    val users by viewModel.users.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (users.isEmpty()) {
            Text(
                text = "No users in household",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "Household members (${users.size})",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(users) { user ->
                    UserListItem(user = user)
                }
            }
        }
    }
}

@Composable
fun UserListItem(user: User, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${user.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Username: ${user.username}")
        }
    }
}