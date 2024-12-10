package com.example.home_domain.interactors


import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class HomeUseCase(private val homeRepository: HomeRepository) {

    fun updateTheme(theme:Sett): Flow<Responses<Unit>> = flow{
        try {
           emit(Responses.Loading())
            val data = homeRepository.updateTheme(theme)
            emit(Responses.Success(data))

        }catch (e: IOException){

        }
    }


}