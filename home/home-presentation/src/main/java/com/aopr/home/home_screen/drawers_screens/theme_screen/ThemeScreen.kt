package com.aopr.home.home_screen.drawers_screens.theme_screen

import android.util.Log
import android.util.Log.wtf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_ui.theme.sunsetTheme
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThemeChooserScreen() {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)

    Scaffold { _ ->

        val themesEntries = Themes.entries
        val chosenTheme by mainViewModel.chosenTheme.collectAsState()

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {

            wtf("io2",MaterialTheme.colorScheme.primary.value.toString())
            Column(modifier = Modifier.fillMaxSize(0.5f)) {

                themesEntries.forEach { theme ->

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(theme.name, color = Color.Red)
                        RadioButton(selected = (theme == chosenTheme), onClick = {
                            mainViewModel.onEvent(MainViewModel.MainEvent.ChosenTheme(theme))
                        })

                    }


                }

            }

        }
    }

}