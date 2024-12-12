package com.aopr.shared_domain.inter


import android.util.Log
import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.colors_for_theme.Themes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class HomeUseCase(private val homeRepository: HomeRepository) {

    fun updateTheme(theme:Themes): Flow<Responses<Unit>> = flow{
        try {
           emit(Responses.Loading())
            val data = homeRepository.updateTheme(theme)
            emit(Responses.Success(data))

        }catch (e: IOException){

        }
    }
    fun getTheme(): Flow<Responses<SettingsDto>> = flow{
        try {
            emit(Responses.Loading())
            val data = homeRepository.getTheme()
            emit(Responses.Success(data))

        }catch (e: IOException){
        }
    }


}