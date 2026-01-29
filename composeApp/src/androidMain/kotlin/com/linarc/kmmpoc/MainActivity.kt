package com.linarc.kmmpoc

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.util.Calendar

class MainActivity : ComponentActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            HomeList(homeViewModel)
        }
    }
}

@Composable
fun HomeList(homeViewModel: HomeViewModel) {
    val listItem by homeViewModel.homeState.collectAsState()
    val loginState by homeViewModel.loginState.collectAsState()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    // Observe Login State for Toast messages
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val name = (loginState as LoginState.Success).response.name
                Toast.makeText(context, "Welcome, $name!", Toast.LENGTH_LONG).show()
            }
            is LoginState.Error -> {
                val error = (loginState as LoginState.Error).message
                Toast.makeText(context, "Login failed: $error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Native DatePickerDialog setup
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dob = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)) {

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Enter Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // DOB Field (Read-only, clicks open native picker)
                OutlinedTextField(
                    value = dob,
                    onValueChange = { },
                    label = { Text("Date of Birth") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    enabled = false, // Disable typing
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledBorderColor = Color.Gray,
                        disabledLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = loginState !is LoginState.Loading,
                    onClick = {
                        if (username.isNotBlank() && password.isNotBlank()) {
                            homeViewModel.login(username, password)
                        } else {
                            Toast.makeText(context, "Enter username and password", Toast.LENGTH_SHORT).show()
                        }
                    },
                    content = {
                        Text("Login")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            listItem.forEach { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .background(color = Color.LightGray)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "${item.name} (${item.email})")
                        Text(text = "DOB: ${item.dob}", color = Color.DarkGray)
                    }
                }
            }
        }

        if (loginState is LoginState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}
