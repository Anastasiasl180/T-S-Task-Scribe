package com.aopr.home.home_screen.ui.ui_elements

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.aopr.home.R


@Composable
fun getDrawerButtons(
    navigateToSettingsScreen: () -> Unit,
    navigateToThemesScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateToAboutAppScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit
): Array<@Composable () -> Unit> {
    return arrayOf<@Composable () -> Unit>({
        TextButton(
            onClick = {
                navigateToThemesScreen()
            },
            modifier = Modifier
        ) {
            Text(text = stringResource(id = R.string.Themes), fontSize = 20.sp, color = Color.White)
        }
    }, {
        TextButton(
            onClick = {
                navigateToSettingsScreen()
            },
            modifier = Modifier,
        ) {
            Text(text = stringResource(id = R.string.Settings),  fontSize = 20.sp,color = Color.White)
        }
    },
        {
            TextButton(
                onClick = {
                    navigateToProfileScreen()
                },
                modifier = Modifier,
            ) {
                Text(text = stringResource(id = R.string.Profile),  fontSize = 20.sp,color = Color.White)
            }
        },
        {
            TextButton(
                onClick = {
                    navigateToAboutAppScreen()
                },
                modifier = Modifier,
            ) {
                Text(text = stringResource(id = R.string.AboutApp),  fontSize = 20.sp,color = Color.White)
            }
        },
        {
            TextButton(
                onClick = {
                    navigateToPrivacyPolicyScreen()
                },
                modifier = Modifier,
            ) {
                Text(text = stringResource(id = R.string.Privacy_policy), fontSize = 20.sp, color = Color.White)
            }
        })
}

