package com.example.myapplication.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.Household
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun HouseholdItem(
    household: Household,
    modifier: Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 100.dp)
            .padding(
                vertical = 5.dp,
                horizontal = 1.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .align(alignment =)
        ) {
            Icon(
                imageVector = Icons.Filled.Kitchen,
                contentDescription = "Fridge Icon"
            )
            Text(household.name)

        }

    }

}


@Preview
@Composable
fun Preview() {
    AppTheme {
        val household = Household(id = 1, name = "Jajeczko", creatorId = 3, createdAt = 400)

        HouseholdItem(
            household = household,
            modifier = Modifier
        )
    }

}