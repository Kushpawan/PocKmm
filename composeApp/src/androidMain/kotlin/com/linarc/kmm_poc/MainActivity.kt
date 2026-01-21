package com.linarc.kmm_poc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    val homeViewModel by viewModels<HomeViewModel>()


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
    val listItem = homeViewModel.homeState.collectAsState()
    val items = listItem.value

    Column(modifier = Modifier.fillMaxSize().padding(top = 24.dp)) {
        items.forEach { item ->

            Row(Modifier.fillMaxWidth().padding(8.dp).background(
                color = androidx.compose.ui.graphics.Color.LightGray
            )) {
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    // HomeList(homeViewModel)
}