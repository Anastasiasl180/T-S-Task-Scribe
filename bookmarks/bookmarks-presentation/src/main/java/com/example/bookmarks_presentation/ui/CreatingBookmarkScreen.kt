package com.example.bookmarks_presentation.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.cardsView.background
import com.aopr.shared_ui.cardsView.cardViews
import com.aopr.shared_ui.top_app_bar.searchBarScrollBehaviour
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.example.bookmarks_presentation.R
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.ui.ui_elements.CustomDropDownMenu
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingBookMarkScreen(uiEventHandler: @Composable () -> Unit) {
    uiEventHandler()
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val backgroundTheme = background()
    val tittleOfBookmark by viewModel.tittle.collectAsState()
    val fileUri by viewModel.fileUri.collectAsState()
    val isDropdownExpanded by viewModel.isDropDownMenuExpanded
    val heightScreen = LocalConfiguration.current.screenHeightDp
    val listOFCategories by viewModel.listOfCategories.collectAsState()
    val contentUrl by viewModel.contentUrl.collectAsState()
    val idOfCategory by viewModel.idOfCategory.collectAsState()
    val context = LocalContext.current

    val topAppBarDefaults = searchBarScrollBehaviour()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(CreatingBookmarkEvents.AddFile(it.toString()))

            }

        }
    )
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
                                            mainViewModel.userId.value.toString()
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
                        .fillMaxHeight()
                        .fillMaxWidth(),
                ) {

                    Spacer(modifier = Modifier.height(35.dp))

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



                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            /*  Row(
                                  modifier = Modifier
                                      .weight(1f)
                                      .fillMaxWidth(),
                                  verticalAlignment = Alignment.CenterVertically
                              ) {
                              }*/
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight(0.4f)
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
                                            .fillMaxHeight()
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
                                    .fillMaxHeight(0.7f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight(0.9f)
                                        .fillMaxWidth(0.75f)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.LightGray.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
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
                                                focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                                unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                                                focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                                unfocusedBorderColor = Color.Transparent,
                                                cursorColor = Color.White
                                            ),

                                            )

                                        /*  Button(onClick = {
                                            openLink(context = context, contentUrl!!)
                                        }) {
                                            Text("go")
                                        }*/
                                        if (contentUrl != null) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight(0.75f)
                                                    .fillMaxWidth(0.45f),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(shape = MaterialTheme.shapes.medium)
                                                        .background(Color.White.copy(alpha = 0.1f)),
                                                    contentAlignment = Alignment.TopCenter
                                                ) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        verticalArrangement = Arrangement.SpaceEvenly,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.Delete,
                                                            contentDescription = "",
                                                            tint = Color.White.copy(alpha = 0.6f),
                                                            modifier = Modifier
                                                                .fillMaxSize(0.6f)
                                                                .weight(1f)
                                                                .clickable {
                                                                    viewModel.onEvent(
                                                                        CreatingBookmarkEvents.CancelLink
                                                                    )
                                                                }
                                                        )
                                                        Icon(
                                                            painter = painterResource(R.drawable.follow_the_link_icon),
                                                            contentDescription = "",
                                                            tint = Color.White.copy(alpha = 0.6f),
                                                            modifier = Modifier
                                                                .fillMaxSize(0.6f)
                                                                .weight(1f)
                                                                .clickable {
                                                                    openLink(
                                                                        context = context,
                                                                        contentUrl!!
                                                                    )
                                                                }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                            }

                        }


                    }


                }
            }
        }
    }
}

@Composable
fun FilePreview(fileUri: String, cancelFile: () -> Unit) {
    val context = LocalContext.current
    val uri = Uri.parse(fileUri)
    val cardColor = cardViews()
    var fileType by remember { mutableStateOf("unknown") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(fileUri) {
        fileType = getFileType(context, uri)

        bitmap = when (fileType) {
            "pdf" -> getPdfThumbnail(context, uri)
            "video" -> getVideoThumbnail(context, uri)
            else -> null
        }
    }


    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .clickable { openFile(context, fileUri) },
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when {
                fileType == "image" -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        AsyncImage(
                            model = uri,
                            contentDescription = "Image Preview",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize(0.75f)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.75f)
                                .fillMaxWidth(0.45f), contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(Color.White.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "",
                                        tint = Color.White.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .fillMaxSize(0.6f)
                                            .weight(1f)
                                            .clickable { cancelFile() }
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.open_file_icon),
                                        contentDescription = "",
                                        tint = Color.White.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .fillMaxSize(0.6f)
                                            .weight(1f)
                                            .clickable { openFile(context, fileUri) }
                                    )
                                }
                            }
                        }
                    }
                }

                fileType == "pdf" && bitmap != null -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.75f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                        ) {
                            Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = "PDF Preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(40.dp))
                            )

                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.75f)
                                .fillMaxWidth(0.45f), contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(Color.White.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "",
                                        tint = Color.White.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxSize(0.7f)
                                            .clickable { cancelFile() }
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.open_file_icon),
                                        contentDescription = "",
                                        tint = Color.White.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxSize(0.7f)
                                            .clickable { openFile(context, fileUri) }
                                    )
                                }

                            }
                        }

                    }
                }

                fileType == "video" && bitmap != null -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Video Thumbnail",
                            modifier = Modifier.fillMaxSize(0.9f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.75f)
                                .fillMaxWidth(0.45f), contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(Color.White.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "", tint = Color.White.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .fillMaxSize(0.6f)
                                        .clickable { cancelFile() }
                                )
                            }
                        }
                    }

                }

                else -> {
                    Icon(Icons.Default.Info, contentDescription = "File Icon")
                }
            }

        }

    }
}

fun getFileType(context: Context, fileUri: Uri): String {
    val mimeType = context.contentResolver.getType(fileUri) ?: return "unknown"

    return when {
        mimeType.startsWith("image") -> "image"
        mimeType == "application/pdf" -> "pdf"
        mimeType.startsWith("video") -> "video"
        else -> "other"
    }
}

fun getVideoThumbnail(context: Context, videoUri: Uri): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, videoUri)
        val bitmap = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        retriever.release()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getPdfThumbnail(context: Context, pdfUri: Uri): Bitmap? {
    return try {
        val fileDescriptor = context.contentResolver.openFileDescriptor(pdfUri, "r") ?: return null
        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(0)

        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdfRenderer.close()
        fileDescriptor.close()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun openFile(context: Context, fileUri: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(fileUri), getMimeType(fileUri))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show()
    }
}

fun getMimeType(fileUri: String): String {
    val extension = MimeTypeMap.getFileExtensionFromUrl(fileUri)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
}

fun openLink(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open this link", Toast.LENGTH_SHORT).show()
    }
}