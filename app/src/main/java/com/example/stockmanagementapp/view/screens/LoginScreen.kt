package com.example.stockmanagementapp.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmanagementapp.R
import com.example.stockmanagementapp.presentation.LoginAction
import com.example.stockmanagementapp.presentation.LoginState
import com.example.stockmanagementapp.ui.theme.StockManagementAppTheme

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface {
        Column(
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.welcome_back_headline), textAlign = TextAlign.Center, fontSize = 40.sp)

            OutlinedTextField(
                value = username,
                onValueChange = {
                username = it
                    onAction(LoginAction.ValidateUsername(username))
            }, leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox, contentDescription = null
                )
            }, label = {
                Text("Username")
            }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isError = state.error != null,
               supportingText = {
                   if (state.error != null){
                       Text(state.error)
                   }
               }
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    onAction(LoginAction.ValidatePassword(password))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle, contentDescription = null
                    )
                },
                label = {
                    Text("Password")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                isError = state.error != null,
                supportingText = {
                    if (state.error != null){
                        Text(state.error)
                    }
                }

            )
            Button(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(), onClick = {
                onAction(LoginAction.NavigateToDashboard)
            },
                enabled = state.username.isNotEmpty() && state.password.isNotEmpty(),
                content = {
                Text(stringResource(R.string.login_button_text))
            })
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    StockManagementAppTheme {
        LoginScreen(
            state = LoginState(), onAction = {})
    }
}