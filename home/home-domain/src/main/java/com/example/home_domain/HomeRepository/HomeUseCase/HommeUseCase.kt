package com.example.home_domain.HomeRepository.HomeUseCase

import com.aopr.shared_domain.Responses
import com.example.home_domain.HomeRepository.HommeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class HommeUseCase(private val repository: HommeRepository) {

    fun deleteAllDataFromRoom():Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteAllDataFromRoom()
            emit(Responses.Success(data))
        }catch (e:Exception){

        }

    }

}