package com.example.myapplication.presentation.householdlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CreateHouseholdDialog(
    onDismiss: () -> Unit,
    onCreateClick: (String) -> Unit
) {
    var householdName by remember { mutableStateOf(TextFieldValue("")) }
    val isNameValid = householdName.text.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create a new household") },
        text = {
            Column {
                OutlinedTextField(
                    value = householdName,
                    onValueChange = { householdName = it },
                    label = { Text("Household Name") },
                    singleLine = true,
                    isError = !isNameValid && householdName.text.isNotEmpty()
                )
                if (!isNameValid && householdName.text.isNotEmpty()) {
                    Text(
                        text = "Name cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onCreateClick(householdName.text) },
                enabled = isNameValid
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}