package com.example.myapplication.presentation.requestlist.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun RequestItem(
    request: ProductRequest,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 75.dp)
            .padding(
                vertical = 5.dp,
                horizontal = 3.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Start)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically


        ) {
            Text(
                text =request.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

        }

    }
}

@Preview
@Composable
fun RequestItemPreview(){
    val request = ProductRequest(
        id = 1,
        householdId = 2,
        creatorId = 3,
        name = "Pączki",
        quantity = BigDecimal(3),
        unit = "unit",
        price = null,
        expirationDate = null,
        fulfilled = false,
        fulfilledBy = null,
        moneyReturned = false
    )

    AppTheme {
        RequestItem(request)
    }
}