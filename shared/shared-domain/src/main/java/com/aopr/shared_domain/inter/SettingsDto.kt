package com.aopr.shared_domain.inter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.serialization.Serializable
import java.io.ByteArrayOutputStream

@Serializable
data class SettingsDto(
    val theme: Themes = Themes.VIOLET,
    val isFirstLaunch: Boolean = true
)

@Serializable
data class UserDataForFireBase(
    val name: String = "",
    val profileImage: String? = null
)


data class UserData(
    val name: String = "",
    val profileImage: Bitmap? = null
)

fun UserDataForFireBase.mapToBitmapFromString(base64String: String): UserData {
    return UserData(
        profileImage = base64ToBitmap(base64String)
    )
}

@RequiresApi(Build.VERSION_CODES.P)
fun UserData.mapToBase64FromUri(uri: Uri, context: Context): UserDataForFireBase {
    val bitmap = uriToBitmap(uri, context)
    val base64String = bitmapToBase64(bitmap)
    return UserDataForFireBase(
        name = this.name,
        profileImage = base64String
    )
}


@RequiresApi(Build.VERSION_CODES.P)
private fun uriToBitmap(uri: Uri, context: Context): Bitmap {
    val source = ImageDecoder.createSource(context.contentResolver, uri)
    return ImageDecoder.decodeBitmap(source)
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

private fun base64ToBitmap(base64String: String): Bitmap {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

