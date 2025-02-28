package com.example.home_domain.HomeRepository.HomeUseCase

import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.inter.UserDataForFireBase
import com.example.home_domain.HomeRepository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class HomeUseCase(
    private val homeRepository: HomeRepository
) {

    fun getUserDataFromDB(): Flow<Responses<UserDataForFireBase>> = flow {
        try {
            emit(Responses.Loading())
            val result = homeRepository.getUserDataFromDB()
            emit(Responses.Success(result))
        } catch (e: IOException) {

        } catch (e: Exception) {


        }
    }

    fun deleteAllUserData(): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val result = homeRepository.deleteAllDataFromRoom()
            emit(Responses.Success(result))
        } catch (e: IOException) {

        } catch (e: Exception) {

        }

    }
}