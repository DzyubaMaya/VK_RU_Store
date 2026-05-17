package ru.rustore.mvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.rustore.mvp.di.AppContainer
import ru.rustore.mvp.ui.navigation.RuStoreNavHost
import ru.rustore.mvp.ui.theme.RuStoreTheme

class MainActivity : ComponentActivity() {

    private val container by lazy { AppContainer(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuStoreTheme {
                val viewModel: MainViewModel = viewModel(factory = MainViewModel.factory(container))
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                if (!uiState.isReady) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    RuStoreNavHost(
                        container = container,
                        startDestination = uiState.startDestination,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
