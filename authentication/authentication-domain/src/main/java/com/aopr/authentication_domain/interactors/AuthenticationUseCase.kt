package com.aopr.authentication_domain.interactors

import com.aopr.firebase_domain.fier_store_uxer_data.FireUser
import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class AuthenticationUseCase(private val repository: AuthenticationRepository){

    fun registerUser(gmail: String, password: String): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.registerUser(gmail, password)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }
    fun saveUser(user: com.aopr.firebase_domain.fier_store_uxer_data.FireUser): Flow<Responses<String?>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.saveFireUser(user)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }
    fun logInUser(gmail: String, password: String): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.logInUser(gmail, password)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }

    fun retrieveUserById(id:String): Flow<Responses<Flow<com.aopr.firebase_domain.fier_store_uxer_data.FireUser?>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.retrieveUser(id)
            emit(Responses.Success(data))

        } catch (e: IOException) {

        }
    }


}