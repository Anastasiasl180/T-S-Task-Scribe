package com.example.home_domain.HomeRepository

import com.aopr.shared_domain.interactors.UserDataForFireBase

interface HomeRepository {
    suspend fun deleteAllDataFromRoom()
    suspend fun getUserDataFromDB():UserDataForFireBase
}