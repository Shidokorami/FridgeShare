package com.example.myapplication.presentation.add_edit_product.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun DeleteProductConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(dialogTitle) },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun PreviewDialog(){
    AppTheme {
        DeleteProductConfirmationDialog(
            onDismissRequest = {},
            onConfirmation = { },
            dialogTitle = "Jajko",
            dialogText = "Dzien tutaj radio gulasz"
        )
    }
}