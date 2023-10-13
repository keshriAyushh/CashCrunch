package com.ayush.cashcrunch.presentation.auth.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.auth.AuthViewModel
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val signInState = authViewModel.signInState.collectAsState()

    val context = LocalContext.current

    signInState.value.let {
        when (it) {
            is Response.Error -> {
                Toast
                    .makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }

            Response.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is Response.Success -> {
                if (it.data) {
                    navController.navigate(Screen.HostScreen.route)
                }
            }
        }
    }

    val email = rememberSaveable() {
        mutableStateOf("")
    }
    val password = rememberSaveable() {
        mutableStateOf("")
    }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MatteBlack)
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "C",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.font2))
                )
                Text(
                    text = "ashCrunch",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp),
                    fontFamily = FontFamily(Font(R.font.font2))
                )
            }

            Text(
                text = "Enter your details below",
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font2)),
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Normal
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(2.dp)),
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.message),
                        contentDescription = "email_icon"

                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            val showPasswordToggled = rememberSaveable() {
                mutableStateOf(false)
            }
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(2.dp)),
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = if (!showPasswordToggled.value) painterResource(R.drawable.password) else painterResource(
                            id = R.drawable.unlock
                        ),
                        contentDescription = "password_icon",
                        modifier = Modifier.clickable {
                            showPasswordToggled.value = !showPasswordToggled.value
                        }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = if (!showPasswordToggled.value) PasswordVisualTransformation() else VisualTransformation.None
            )

            Button(
                onClick = {
                    if ((email.value.isNotEmpty() && email.value.isNotBlank())
                        && (password.value.isNotBlank() && password.value.isNotEmpty())
                    ) {
                        authViewModel.signIn(
                            email = email.value,
                            password = password.value
                        )

                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(1f)
                    .padding(horizontal = 15.dp),
                contentPadding = PaddingValues(15.dp)
            ) {
                Text(
                    text = "Sign in",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )


            }

            Text(
                text = "or",
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.font2)),
                modifier = Modifier.padding(top = 12.dp)
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(1f)
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(15.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.google),
                    modifier = Modifier.size(20.dp),
                    contentDescription = "google_icons",
                    tint = Color.Black
                )
                Text(
                    text = "Sign in with google",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .offset(x = -(20 / 2).dp), //default icon width = 24.dp
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Normal
                )
            }

            Text(
                text = "New here? Click here sign up!",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 350.dp)
                    .clickable {
                        navController.navigate(Screen.SignupScreen.route) {
                            popUpTo(Screen.SignupScreen.route) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    }

}
