package com.ayush.cashcrunch.presentation.main.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.auth.AuthViewModel
import com.ayush.cashcrunch.presentation.main.home.HomeViewModel
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUserDetails()
    }
    val userDetailsState = homeViewModel.userDetailsState.collectAsState()
    val signOutState = authViewModel.signOutState.collectAsState()

    signOutState.value.let {
        when(it) {
            is Response.Error -> {
                showToast(it.message, context)
            }
            Response.Loading -> {
                ShowProgressIndicator()
            }
            is Response.Success -> {
                if(it.data) {
                    navController.navigate("ROOT"){
                        popUpTo("ROOT") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    userDetailsState.value.let {
        when (it) {
            is Response.Error -> {

            }

            Response.Loading -> {

            }
            is Response.Success -> {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f)
                            .background(MatteBlack),
                        contentAlignment = Alignment.Center

                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "pfp",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        shape = CircleShape,
                                        color = MatteBlack
                                    )
                                    .border(
                                        width = 0.5.dp,
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                    .padding(20.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.data.name,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.font2)),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .background(Color.White)
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Email: ${it.data.email}",
                                color = MatteBlack,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.font2))
                            )
                            Button(
                                onClick = {
                                    authViewModel.signOut()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Text(
                                    text = "Sign out",
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun showToast(message: String, context: Context) {
    Toast
        .makeText(context, message, Toast.LENGTH_SHORT)
        .show()
}

@Composable
fun ShowProgressIndicator() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}
