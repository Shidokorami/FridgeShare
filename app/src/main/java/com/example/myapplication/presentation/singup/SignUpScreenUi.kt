package com.example.myapplication.presentation.singup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.singup.components.PasswordSignUpField
import com.example.myapplication.presentation.util.EmailField
import com.example.myapplication.presentation.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenUi(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHouseholdList: () -> Unit,
){
    val uiState = viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                }
                is UiEvent.NavigateAndPopUp -> {
                    onNavigateToHouseholdList()
                }
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopAppBar(
            title = {},
            modifier = Modifier,
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        ) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding( start = 10.dp, end = 10.dp)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text ="Create Account",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sign up to get started!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

            Spacer(Modifier.padding(5.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                EmailField(
                    value = uiState.value.email,
                    onChange = {viewModel.onEvent(SignUpEvent.EmailChanged(it))},
                    modifier = Modifier.fillMaxWidth()
                )

                PasswordSignUpField(
                    value = uiState.value.password,
                    onChange = {viewModel.onEvent(SignUpEvent.PasswordChanged(it))},
                    submit = {},
                    modifier = Modifier.fillMaxWidth()
                )

                PasswordSignUpField(
                    value = uiState.value.passwordConfirmation,
                    onChange = {viewModel.onEvent(SignUpEvent.PasswordConfirmationChanged(it))},
                    submit = { viewModel.onEvent(SignUpEvent.SignUpClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Confirm Password"
                )

                Button(
                    onClick = {viewModel.onEvent(SignUpEvent.SignUpClicked)},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text ="Sign up",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Or Sign up with",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(width = 1.dp, color = Color(0xFF747775) ),
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)

            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.android_light_sq_na),
                        contentDescription = "Google logo",
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = "Sign up with Google",
                        color = Color(0xFF1F1F1F),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

            }
        }
    }
}