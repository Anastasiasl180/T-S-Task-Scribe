package com.aopr.shared_data.proto_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.aopr.shared_domain.colors_for_theme.Themes
import com.aopr.shared_domain.interactors.SettingsDto
import com.aopr.shared_domain.interactors.UserDataForFireBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import java.io.InputStream
import java.io.OutputStream


@Single
fun provideUserDataDataStore(context: Context): DataStore<UserDataForFireBase> {
    return DataStoreFactory.create(serializer = UserDtoSerializer, produceFile = {
        context.dataStoreFile("userData")
    })
}

@Factory
class UserDataDataStoreManager(private val dataStore: DataStore<UserDataForFireBase>) {
    val data = dataStore.data
    suspend fun updateUserData(userDataForFireBase:UserDataForFireBase) {
        dataStore.updateData { it ->
            it.copy()

        }
    }
}

@Single
fun provideSettingsDataStore(context: Context): DataStore<SettingsDto> {
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

    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData { it ->
            it.copy(isFirstLaunch = isFirstLaunch)
        }
    }
}

object UserDtoSerializer : Serializer<UserDataForFireBase> {
    override val defaultValue: UserDataForFireBase
        get() = UserDataForFireBase()

    override suspend fun readFrom(input: InputStream): UserDataForFireBase {
        return try {
            Json.decodeFromString(input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserDataForFireBase, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = UserDataForFireBase.serializer(),
                    value = t
                ).encodeToByteArray()

            )
        }
    }

}

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