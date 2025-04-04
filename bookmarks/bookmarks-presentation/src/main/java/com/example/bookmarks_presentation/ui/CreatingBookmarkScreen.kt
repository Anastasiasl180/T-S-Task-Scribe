package com.example.bookmarks_presentation.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aopr.shared_ui.MainViewModel
import com.aopr.shared_ui.util.MainViewModelStoreOwner
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.ui.ui_elements.CustomDropDownMenu
import com.example.bookmarks_presentation.view_models.CreatingBookmarkViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatingBookMarkScreen(uiEventHandler:@Composable ()-> Unit) {
    uiEventHandler()
    val viewModel = koinViewModel<CreatingBookmarkViewModel>()
    val mainViewModel = koinViewModel<MainViewModel>(viewModelStoreOwner = MainViewModelStoreOwner)
    val tittleOfBookmark by viewModel.tittle.collectAsState()
    val fileUri by viewModel.fileUri.collectAsState()
    val isDropdownExpanded by viewModel.isDropDownMenuExpanded

    val listOFCategories by viewModel.listOfCategories.collectAsState()
    val contentUrl by viewModel.contentUrl.collectAsState()
    val idOfCategory by viewModel.idOfCategory.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                viewModel.oEvent(CreatingBookmarkEvents.AddFile(it.toString()))

            }

        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            TextField(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                placeholder = {
                    Text(text = "tittle")
                },
                value = tittleOfBookmark,
                onValueChange = {
                    viewModel.oEvent(
                        CreatingBookmarkEvents.UpdateTittleOfBookmark(
                            it
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),

                )
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)) {

                CustomDropDownMenu(
                    chosenCategory = idOfCategory,
                    listOfCategories = listOFCategories,
                    onExpanded = {
                        viewModel.oEvent(CreatingBookmarkEvents.ExpandDropDownMenu)
                    },
                    isExpanded = isDropdownExpanded,
                    onOptionSelected = { categoryId ->
                        viewModel.oEvent(CreatingBookmarkEvents.SelectCategory(categoryId))
                    }
                )

            }

            if (fileUri == null) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    launcher.launch(arrayOf("*/*"))
                }) {
                    Text("Pick file")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp),
                    placeholder = {
                        Text(text = "url")
                    },
                    value = contentUrl ?: "",
                    onValueChange = {
                        viewModel.oEvent(
                            CreatingBookmarkEvents.AddLink(it)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),

                    )

                Button(onClick = {
                    openLink(context = context, contentUrl!!)
                }) {
                    Text("go")
                }


            }

            if (fileUri != null) {
                Text(
                    text = "Attached File: $fileUri",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            openFile(context, fileUri!!)
                        },
                    color = Color.Gray
                )
            }
            Button(onClick = {
                viewModel.oEvent(CreatingBookmarkEvents.SaveBookmark(mainViewModel.userId.value.toString()))
            }) {
                Text("save")
            }
        }
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
/*

suspend fun fetchLinkMetadata(url: String): LinkMetadata? {
    return try {
        val parser =
        val openGraph = parser.parse(url)

        LinkMetadata(
            title = openGraph["title"] ?: "",
            description = openGraph["description"] ?: "",
            imageUrl = openGraph["image"] ?: ""
        )
    } catch (e: Exception) {
        null
    }
}

data class LinkMetadata(
    val title: String,
    val description: String,
    val imageUrl: String
)package*/
