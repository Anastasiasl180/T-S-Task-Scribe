package com.example.bookmarks_presentation.ui.ui_elements

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.bookmarks_presentation.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


@Composable
fun FilePreview(fileUri: String, cancelFile: () -> Unit) {
    val context = LocalContext.current
    val uri = Uri.parse(fileUri)
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
            .fillMaxHeight(0.8f)
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


@Composable
fun LinkPreview(url: String, cancelLink: () -> Unit) {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(url) {
        imageUrl = fetchLinkPreview(url, context)
    }


    Box(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth(0.75f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .clickable { openYoutubeLink(context, url) },
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            imageUrl?.let { data ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    imageUrl?.let { imageUrl ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.75f)
                                .clip(RoundedCornerShape(30.dp))
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(imageUrl),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize(), contentScale = ContentScale.FillBounds
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
                                            .clickable { cancelLink() }
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.open_file_icon),
                                        contentDescription = "",
                                        tint = Color.White.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxSize(0.7f)
                                            .clickable { openYoutubeLink(context, url) }
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



suspend fun fetchLinkPreview(url: String, context: Context): String? {
    return withContext(Dispatchers.IO) {
        try {
            if (url.isBlank() || !Patterns.WEB_URL.matcher(url).matches()) {
                throw IllegalArgumentException("Must supply a valid URL")
            }

            if (url.contains("youtube.com") || url.contains("youtu.be")) {
                val videoId = extractYouTubeVideoId(url)
                if (videoId.isNotBlank()) {
                    return@withContext "https://img.youtube.com/vi/$videoId/0.jpg"
                }
            }


            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get()

            val imageUrl = doc.select("meta[property=og:image]").attr("content")
            return@withContext imageUrl.ifBlank { null }
        } catch (e: Exception) {
            Log.e("LinkPreview", "Error fetching image", e)
            null
        }
    }
}

fun extractYouTubeVideoId(url: String): String {
    val regexList = listOf(
        "v=([a-zA-Z0-9_-]+)".toRegex(),
        "youtu\\.be/([a-zA-Z0-9_-]+)".toRegex(),
        "embed/([a-zA-Z0-9_-]+)".toRegex(),
        "shorts/([a-zA-Z0-9_-]+)".toRegex()
    )

    for (regex in regexList) {
        val matchResult = regex.find(url)
        if (matchResult != null) {
            return matchResult.groups[1]?.value ?: ""
        }
    }

    return ""
}
fun openYoutubeLink(context: Context, url: String) {
    val intent =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}


///////////////      getting files from devise   ///////////////

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
