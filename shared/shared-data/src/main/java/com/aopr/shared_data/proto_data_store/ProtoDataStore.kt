package com.aopr.shared_data.proto_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.inter.SettingsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
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
    suspend fun updateIsFirstLaunch(isFirstLaunch:Boolean){
        dataStore.updateData { it->
            it.copy(isFirstLaunch = isFirstLaunch)
        }
    }
}


object SettingsDtoSerializer : Serializer<SettingsDto> {
    override val defaultValue:SettingsDto
        get() = SettingsDto()

    override suspend fun readFrom(input: InputStream): com.aopr.shared_domain.inter.SettingsDto {
        return try {
            Json.decodeFromString(input.readBytes().decodeToString())

        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: com.aopr.shared_domain.inter.SettingsDto,
        output: OutputStream
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = com.aopr.shared_domain.inter.SettingsDto.serializer(),
                    value = t
                ).encodeToByteArray()

            )
        }
    }

}