package com.charlesmuchene.xr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.charlesmuchene.xr.ui.MainScreen
import com.charlesmuchene.xr.ui.theme.XRTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            XRTheme {
                MainScreen()
            }
        }
    }
}



