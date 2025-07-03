package com.example.myapplication.presentation.requestlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.presentation.util.UnitDropdownMenu

@Composable
fun FulfillRequestDialog(
    request: ProductRequest,
    onDismiss: () -> Unit,
    onConfirm: (
        quantity: String,
        unit: String,
        name: String,
        price: String
    ) -> Unit
) {
    var quantity by remember { mutableStateOf(TextFieldValue(request.quantity?.toString().toString())) }
    var unit by remember { mutableStateOf(request.unit ?: "") }
    var name by remember { mutableStateOf(TextFieldValue(request.name)) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var isUnitDropdownExpanded by remember { mutableStateOf(false) }

    val isValid = name.text.isNotBlank() && quantity.text.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Fulfill Request") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                UnitDropdownMenu(
                    selectedUnit = unit,
                    isExpanded = isUnitDropdownExpanded,
                    onExpandedChange = { isUnitDropdownExpanded = it },
                    onUnitSelected = { unit = it },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price (optional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        quantity.text,
                        unit,
                        name.text,
                        price.text
                    )
                },
                enabled = isValid
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
