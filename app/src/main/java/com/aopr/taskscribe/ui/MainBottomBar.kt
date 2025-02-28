package com.aopr.taskscribe.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.navigation.NavigationBarItems
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun BottomBar(navController: NavHostController) {
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val navItems = remember {
        NavigationBarItems.entries
    }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val isBottomBarMoved by globalViewModel.isBottomBarMoved.collectAsState()


    val offsetX by animateDpAsState(
        targetValue = if (isBottomBarMoved) 120.dp else 0.dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        NavigationBar(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.5f)
                .offset(y = offsetX)
                .padding(bottom = 10.dp, start = 10.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            containerColor = Color.DarkGray.copy(alpha = 0.6f),
            windowInsets = WindowInsets(bottom = 0)
        ) {
            navItems.forEach { mainNavRoute ->
                val isSelected = mainNavRoute.routes::class.qualifiedName == currentRoute
                val alpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 35f,
                    label = ""
                )
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1f else .98f,
                    label = "",
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )
                NavigationBarItem(selected = isSelected,
                    modifier = Modifier
                        .alpha(alpha)
                        .scale(scale),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    ),
                    onClick = {
                        globalViewModel.onEvent(mainNavRoute.event)
                    },
                    icon = {
                        Icon(
                            imageVector = mainNavRoute.icon,
                            contentDescription = ""
                        )
                    })

            }
        }
    }

}
