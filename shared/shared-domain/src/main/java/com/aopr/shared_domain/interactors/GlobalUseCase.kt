package com.aopr.shared_domain.interactors


import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class GlobalUseCase(private val globalRepository: GlobalRepository) {


    fun updateIsFirstLaunch(isFirstLaunch: Boolean): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = globalRepository.updateIsFirstLaunch(isFirstLaunch)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }

    fun getIsFirstLaunch(): Flow<Responses<SettingsDto>> = flow {
        try {
            emit(Responses.Loading())
            val data = globalRepository.getIsFirstLaunch()
            emit(Responses.Success(data))
        } catch (e: IOException) {

        }
    }


    fun updateTheme(theme: Themes): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = globalRepository.updateTheme(theme)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }

    fun getTheme(): Flow<Responses<SettingsDto>> = flow {
        try {
            emit(Responses.Loading())
            val data = globalRepository.getTheme()
            emit(Responses.Success(data))

        } catch (e: IOException) {
        }
    }


}