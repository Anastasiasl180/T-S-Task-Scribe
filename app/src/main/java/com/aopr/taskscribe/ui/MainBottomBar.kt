package com.aopr.taskscribe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.navigation.NavigationBarItems
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomBar(navController: NavHostController) {
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val navItems = remember {
        NavigationBarItems.entries
    }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.9f)
                .padding(bottom = 10.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge), windowInsets = WindowInsets(bottom = 0)
        ) {
            navItems.forEach { mainNavRoute ->
                val isSelected = mainNavRoute.routes::class.qualifiedName == currentRoute
                NavigationBarItem(selected = isSelected, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Red, indicatorColor = Color.Transparent),
                    onClick = {
                        mainViewModel.onEvent(mainNavRoute.event)
                    },
                    icon = {
                        Icon(
                            imageVector = mainNavRoute.icon,
                            contentDescription = "",

                        )
                    })

            }
        }
    }

}
