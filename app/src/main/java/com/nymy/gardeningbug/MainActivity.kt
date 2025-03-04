package com.nymy.gardeningbug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.nymy.gardeningbug.ui.screens.GardenScreen
import com.nymy.gardeningbug.ui.theme.GardeningBugTheme
import com.nymy.gardeningbug.ui.viewmodel.GardenViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GardenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GardeningBugTheme {
                GardenScreen(viewModel = viewModel)
            }
        }
    }
}