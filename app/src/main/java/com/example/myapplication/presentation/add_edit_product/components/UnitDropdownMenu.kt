package com.example.myapplication.presentation.add_edit_product.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun UnitDropdownMenu(
    selectedUnit: String?,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onUnitSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val unitList = listOf("Kg", "Unit")

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedUnit.orEmpty(),
            onValueChange = {},
            label = { Text("Unit") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Nakładka na cały TextField przechwytująca kliknięcia
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    Log.d("DropdownClickDebug", "Overlay clicked")
                    onExpandedChange(!isExpanded)
                }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            unitList.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun UnitDropdownMenuPreview() {
    AppTheme {
        var selectedUnit by remember { mutableStateOf("Kg") }
        var isExpanded by remember { mutableStateOf(false) }

        UnitDropdownMenu(
            selectedUnit = selectedUnit,
            isExpanded = isExpanded,
            onExpandedChange = { expanded ->
                isExpanded = expanded
            },
            onUnitSelected = { unit ->
                selectedUnit = unit
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}