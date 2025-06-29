package com.example.myapplication.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun EmailField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    placeholder: String = "john_smith@example.com"
){

    val leadingIcon = @Composable{
        Icon(
            Icons.Default.Email,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        placeholder = {Text(placeholder)},
        label = {Text(label)},
        singleLine = true

    )
}

@Preview
@Composable
fun PreviewLoginField(){

    AppTheme {
        Surface {
            EmailField(
                value =  "",
                onChange = {}
            )
        }
    }
}