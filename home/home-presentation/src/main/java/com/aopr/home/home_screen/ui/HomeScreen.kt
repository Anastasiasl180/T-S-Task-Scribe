package com.aopr.home.home_screen.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.aopr.home.R
import com.aopr.home.home_screen.ui.ui_elements.CalendarCard
import com.aopr.home.home_screen.ui.ui_elements.HomeCard
import com.aopr.home.home_screen.ui.ui_elements.getBookMarksButtons
import com.aopr.home.home_screen.ui.ui_elements.getCalendarButton
import com.aopr.home.home_screen.ui.ui_elements.getDrawerButtons
import com.aopr.home.home_screen.ui.ui_elements.getNotesButtons
import com.aopr.home.home_screen.ui.ui_elements.getTasksButtons
import com.aopr.home.home_screen.view_model.HomeViewModel
import com.aopr.home.home_screen.view_model.events.homeEvents.HomeEvent
import com.aopr.home.home_screen.view_model.ui_events_handler.HomeUiEventHandler
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.textGradient
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.aopr.shared_ui.util.global_view_model.events.GlobalEvents
import com.radusalagean.infobarcompose.InfoBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    BackHandler {

    }
    HomeUiEventHandler()
    val brush = background()
    val context = LocalContext.current
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("RegistrationScreen", "Notification permission granted.")
        } else {
            Log.d("RegistrationScreen", "Notification permission denied.")
        }
    }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Log.d("RegistrationScreen", "Notification permission already granted.")
            }
        }
    }
    val viewModel = koinViewModel<HomeViewModel>()
    val bitMaoImage by viewModel.bitmapImage.collectAsState()
    val todayTasks = viewModel.listOfTodaysTasks.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val cardTexts = listOf(
        stringResource(id = R.string.Notes),
        stringResource(id = R.string.Tasks),
        stringResource(id = R.string.Bookmarks),
        stringResource(id = R.string.Calendar)
    )
    var blurredCardIndex by remember { mutableIntStateOf(-1) }
    val listOfButtons = listOf(
        getNotesButtons(navigateToCreateNote = {
            viewModel.onEvent(HomeEvent.NavigateToCreateNote)
        }, navigateToAllNotes = {
            viewModel.onEvent(HomeEvent.NavigateToAllNotes)
        }), getTasksButtons(navigateToAllTasks = {
            viewModel.onEvent(HomeEvent.NavigateToAllTasks)
        }, navigateToCreateTask = {
            viewModel.onEvent(HomeEvent.NavigateToCreateTask)
        }), getBookMarksButtons(navigateToAllCategoriesOfBookmarks = {
            viewModel.onEvent(HomeEvent.NavigateToAllCategoriesOfBookmarks)
        }, navigateToCreateBookmark = {
            viewModel.onEvent(HomeEvent.NavigateToCreateBookmark)
        }),
        getCalendarButton(navigateToCalendarScreen = { viewModel.onEvent(HomeEvent.NavigateToCalendarScreen) })
    )
    val globalViewModel =
        koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    LaunchedEffect(drawerState.currentValue) {

        if (drawerState.isOpen) {
            globalViewModel.onEvent(GlobalEvents.ToMoveBottomBar(true))
        } else {
            globalViewModel.onEvent(GlobalEvents.ToMoveBottomBar(false))
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.6f),
                drawerContainerColor = Color.LightGray.copy(alpha = 0.8f)
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.15f)
                            .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.6f)
                                    .fillMaxWidth(0.6f)
                                    .clip(CircleShape)
                                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                            ) {
                                if (bitMaoImage == null) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )

                                } else {
                                    AsyncImage(
                                        model = bitMaoImage,
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(0.1f)
                            .fillMaxWidth()
                    ) {
                        userName?.let { Text(text = it) }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        getDrawerButtons(
                            navigateToSettingsScreen = {
                                viewModel.onEvent(HomeEvent.NavigateToSettingsByDrawer)

                            },
                            navigateToThemesScreen = {
                                viewModel.onEvent(HomeEvent.NavigateToThemesByDrawer)
                            },
                            navigateToProfileScreen = {
                                viewModel.onEvent(HomeEvent.NavigateToProfileByDrawer)
                            },
                            navigateToAboutAppScreen = {
                                viewModel.onEvent(HomeEvent.NavigateToAboutAppByDrawer)

                            },
                            navigateToPrivacyPolicyScreen = {
                                viewModel.onEvent(HomeEvent.NavigateToPrivacyPolicyByDrawer)

                            }
                        ).forEach { button ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f).padding(start = 10.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                button()
                            }
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.2f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { viewModel.onEvent(HomeEvent.LogOut) }) {
                                Text(
                                    text = stringResource(R.string.LogOut),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }


                }
            }
        }) {
        Scaffold(modifier = Modifier.consumeWindowInsets(WindowInsets.ime)) { _ ->
            Box(
                modifier = Modifier
                    .consumeWindowInsets(WindowInsets.ime)
                    .fillMaxSize()
                    .background(brush = brush)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.7f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "drawer", tint = Color.White
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.Hello_user),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(
                                        brush = Brush.linearGradient(
                                            colors = textGradient()
                                        )
                                    ),
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.9f), contentAlignment = Alignment.BottomCenter
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CalendarCard(tasks = todayTasks.value)

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    verticalArrangement = Arrangement.spacedBy(20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    items(4) { index ->
                                        val isBlurred = blurredCardIndex == index
                                        HomeCard(
                                            isBlurred = isBlurred, cardText = cardTexts[index],
                                            onCardClick = {
                                                blurredCardIndex =
                                                    if (blurredCardIndex == index) -1 else index
                                            },
                                            buttons = listOfButtons[index]
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
                InfoBar(offeredMessage = viewModel.infoBar.value) {

                }
            }

        }
    }

}


