package com.example.myapplication.presentation.loginscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.util.EmailField
import com.example.myapplication.presentation.loginscreen.components.PasswordField
import com.example.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUi(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit
){

    val uiState = viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = { TopAppBar(
            title = {}
        ) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text ="Sign In",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Welcome to Fridge Share",
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
                    onChange = {viewModel.onEvent(LoginEvent.EmailChanged(it))},
                    modifier = Modifier.fillMaxWidth()
                )

                PasswordField(
                    value = uiState.value.password,
                    onChange = {viewModel.onEvent(LoginEvent.PasswordChanged(it))},
                    submit = {viewModel.onEvent(LoginEvent.SignInClicked)},
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {viewModel.onEvent(LoginEvent.SignInClicked)},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text ="Sign in",
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
                    text = "Or Sign in with",
                    modifier = Modifier.weight(1f)
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
                        text = "Sign in with Google",
                        color = Color(0xFF1F1F1F),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.padding(2.dp))
                Text(
                    text ="Sign Up",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF799ed9),
                    modifier = Modifier.clickable{onNavigateToSignUp()}
                )
            }


        }
    }
}

