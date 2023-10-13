package com.ayush.cashcrunch.presentation.auth.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.auth.AuthViewModel
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val signUpFlow = authViewModel.signUpState.collectAsState()

    signUpFlow.value.let {
        when (it) {
            is Response.Error -> {
                Toast
                    .makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }

            Response.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is Response.Success -> {
                if(it.data) {
                    navController.navigate(Screen.HostScreen.route)
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MatteBlack)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .background(MatteBlack)
                .padding(10.dp)
        ) {
            val name = rememberSaveable {
                mutableStateOf("")
            }
            val email = rememberSaveable {
                mutableStateOf("")
            }
            val password = rememberSaveable {
                mutableStateOf("")
            }
            val confirmPassword = rememberSaveable {
                mutableStateOf("")
            }

            val isError = rememberSaveable {
                mutableStateOf(false)
            }

            val showPasswordToggled = rememberSaveable {
                mutableStateOf(false)
            }
            val passwordLength = rememberSaveable {
                mutableStateOf(false)
            }

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
                fontFamily = FontFamily(Font(R.font.grotesco_thin)),
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(2.dp)),
                placeholder = {
                    Text(
                        text = "Name",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.name),
                        contentDescription = "name_icon"

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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                modifier = Modifier
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
                )
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    passwordLength.value = password.value.length >= 6
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
                    focusedTrailingIconColor = Color.Green,
                    focusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = if (!showPasswordToggled.value) PasswordVisualTransformation() else VisualTransformation.None,
                supportingText = {
                    Row {
                        Text(
                            text = "*",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.grotesco_light)),
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(
                            modifier = Modifier
                                .width(4.dp)
                                .background(Color.Black)
                        )
                        Text(
                            text = "Password must contain 6 characters.",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.grotesco_light)),
                            fontWeight = FontWeight.Normal
                        )
                    }
                },
                isError = !passwordLength.value
            )

            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = {
                    confirmPassword.value = it
                    isError.value = confirmPassword.value == password.value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(2.dp)),
                placeholder = {
                    Text(
                        text = "Confirm Password",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.Green,
                    focusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions {
                    isError.value = confirmPassword.value != password.value
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = !isError.value,
                supportingText = {
                    if (!isError.value) {
                        Text(
                            text = "Passwords don't match",
                            color = Color.Red
                        )
                    }
                }
            )

            Button(
                onClick = {
                    if (
                        (email.value.isNotEmpty() && email.value.isNotBlank())
                        && (password.value.isNotBlank() && password.value.isNotEmpty())
                        && (name.value.isNotEmpty() && name.value.isNotBlank())
                        && (password.value == confirmPassword.value)
                    ) {
                        authViewModel.signUp(
                            email = email.value,
                            password = password.value,
                            name = name.value
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
                    text = "Sign up",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.grotesco_light)),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = "Already have an account? Click here sign in!",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 350.dp)
                .clickable {
                    navController.navigate(Screen.SigninScreen.route) {
                        popUpTo(Screen.SigninScreen.route) {
                            inclusive = true
                        }
                    }
                }
        )

    }
}