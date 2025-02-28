package com.example.bookmarks_presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aopr.shared_ui.util.global_view_model.GlobalViewModel
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.top_app_bar.searchBarScrollBehaviour
import com.aopr.shared_ui.util.global_view_model.GlobalViewModelStoreOwner
import com.example.bookmarks_presentation.R
import com.example.bookmarks_presentation.view_models.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.ui.ui_elements.CustomDropDownMenu
import com.example.bookmarks_presentation.ui.ui_elements.FilePreview
import com.example.bookmarks_presentation.ui.ui_elements.LinkPreview
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import com.radusalagean.infobarcompose.InfoBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingBookMarkScreen(uiEventHandler: @Composable () -> Unit) {
    uiEventHandler()
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val globalViewModel = koinViewModel<GlobalViewModel>(viewModelStoreOwner = GlobalViewModelStoreOwner)
    val backgroundTheme = background()
    val tittleOfBookmark by viewModel.tittle.collectAsState()
    val fileUri by viewModel.fileUri.collectAsState()
    val isDropdownExpanded by viewModel.isDropDownMenuExpanded
    val heightScreen = LocalConfiguration.current.screenHeightDp
    val listOFCategories by viewModel.listOfCategories.collectAsState()
    val contentUrl by viewModel.contentUrl.collectAsState()
    val idOfCategory by viewModel.idOfCategory.collectAsState()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(CreatingBookmarkEvents.AddFile(it.toString()))

            }

        }
    )
    val topAppBarDefaults = searchBarScrollBehaviour()
    Scaffold(modifier = Modifier.nestedScroll(topAppBarDefaults.nestedScrollConnection),
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ), scrollBehavior = topAppBarDefaults,
                actions = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.95f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            IconButton(
                                onClick = { viewModel.onEvent(CreatingBookmarkEvents.NavigateBack) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.DarkGray.copy(alpha = 0.6f))
                                    .size(50.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                    contentDescription = "",
                                    tint = Color.White, modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(
                                        CreatingBookmarkEvents.SaveBookmark(
                                            globalViewModel.userId.value.toString()
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.DarkGray.copy(alpha = 0.6f))
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    tint = Color.White, modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                },
                title = { }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundTheme),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Spacer(modifier = Modifier.height(35.dp))
                    Box(modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier
                                    .height((heightScreen * 0.1).dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                OutlinedTextField(
                                    shape = MaterialTheme.shapes.medium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .border(
                                            width = 0.5.dp,
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    placeholder = {
                                        Text(
                                            text = stringResource(
                                                com.aopr.shared_ui.R.string.tittle
                                            ),
                                        )
                                    },
                                    value = tittleOfBookmark,
                                    onValueChange = {
                                        viewModel.onEvent(
                                            CreatingBookmarkEvents.UpdateTittleOfBookmark(
                                                it
                                            )
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                        focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                        unfocusedBorderColor = Color.Transparent,
                                        cursorColor = Color.White
                                    ),

                                    )

                            }

                            Row(
                                modifier = Modifier
                                    .height((heightScreen * 0.15f).dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                CustomDropDownMenu(
                                    chosenCategory = idOfCategory,
                                    listOfCategories = listOFCategories,
                                    onExpanded = {
                                        viewModel.onEvent(CreatingBookmarkEvents.ExpandDropDownMenu)
                                    },
                                    isExpanded = isDropdownExpanded,
                                    onOptionSelected = { categoryId ->
                                        viewModel.onEvent(
                                            CreatingBookmarkEvents.SelectCategory(
                                                categoryId
                                            )
                                        )
                                    }
                                )

                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),

                            ) {

                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                val safeFileUri = fileUri
                                if (safeFileUri != null) {
                                    FilePreview(
                                        safeFileUri,
                                        cancelFile = { viewModel.onEvent(CreatingBookmarkEvents.CancelFile) })
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(0.8f)
                                            .fillMaxWidth(0.75f)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color.LightGray.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center,
                                    ) {

                                        Text(
                                            stringResource(R.string.tap_to_pick_the_file),
                                            color = Color.White,
                                            modifier = Modifier.clickable {
                                                launcher.launch(
                                                    arrayOf("*/*")
                                                )
                                            }
                                        )


                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val url = contentUrl
                                if (url != null) {

                                    LinkPreview(url, cancelLink = {
                                        viewModel.onEvent(
                                            CreatingBookmarkEvents.CancelLink
                                        )
                                    })


                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(0.8f)
                                            .fillMaxWidth(0.75f)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color.LightGray.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center,
                                    ) {

                                        TextField(

                                            shape = MaterialTheme.shapes.medium,
                                            modifier = Modifier
                                                .fillMaxWidth(0.7f)
                                                .height(60.dp),
                                            placeholder = {
                                                Text(text = stringResource(R.string.paste_url))
                                            },
                                            value = contentUrl ?: "",
                                            onValueChange = {
                                                viewModel.onEvent(
                                                    CreatingBookmarkEvents.AddLink(it)
                                                )
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor = Color.LightGray.copy(
                                                    alpha = 0.2f
                                                ),
                                                unfocusedContainerColor = Color.LightGray.copy(
                                                    alpha = 0.2f
                                                ),
                                                focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                                unfocusedBorderColor = Color.Transparent,
                                                cursorColor = Color.White
                                            ),

                                            )
                                    }
                                }


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
