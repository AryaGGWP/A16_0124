package com.example.finalprojectpam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalprojectpam.ui.theme.FinalprojectpamTheme
import com.example.finalprojectpam.ui.viewmodel.KeuanganApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalprojectpamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                 KeuanganApp(
                     modifier = Modifier.padding(innerPadding)
                 )
                }
            }
        }
    }
}