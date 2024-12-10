package com.example.home_data.impl.proto_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.aopr.shared_ui.colors_for_theme.Themes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import java.io.InputStream
import java.io.OutputStream


@Single
fun provideSettingsDataStore(context:Context): DataStore<SettingsDto> {
    return DataStoreFactory.create(serializer = SettingsDtoSerializer, produceFile = {
        context.dataStoreFile("settings")
    })
}

@Factory
class SettingsDataStoreManager(private val dataStore: DataStore<SettingsDto>) {

    val data = dataStore.data
    suspend fun updateTheme(themes: Themes) {
        dataStore.updateData { it ->
            it.copy(theme = themes)
        }
    }
}

@Serializable
data class SettingsDto(
    val theme: Themes = Themes.VIOLET
)

object SettingsDtoSerializer : Serializer<SettingsDto> {
    override val defaultValue: SettingsDto
        get() = SettingsDto()

    override suspend fun readFrom(input: InputStream): SettingsDto {
        return try {
            Json.decodeFromString(input.readBytes().decodeToString())

        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: SettingsDto,
        output: OutputStream
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = SettingsDto.serializer(),
                    value = t
                ).encodeToByteArray()

            )
        }
    }

}