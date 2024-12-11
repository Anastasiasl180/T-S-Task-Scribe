package com.aopr.home.home_screen.drawers_screens.theme_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_domain.colors_for_theme.Themes
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThemeChooserScreen() {
    val mainViewModel = koinViewModel<MainViewModel>()

    Scaffold { _ ->

        val themesEntries = Themes.entries
        val chosenTheme = mainViewModel.chosenTheme.collectAsState()

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(modifier = Modifier.fillMaxSize(0.5f)) {

                themesEntries.forEach { theme ->

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(theme.name)
                        RadioButton(selected = (theme == chosenTheme.value), onClick = {
                            mainViewModel.onEvent(MainViewModel.MainEvent.ChosenTheme(theme))
                        })

                    }


                }

            }

        }
    }

}