package com.example.home_domain.HomeRepository

import com.aopr.shared_domain.inter.UserDataForFireBase

interface HommeRepository {
    suspend fun deleteAllDataFromRoom()
    suspend fun getUserDataFromDB():UserDataForFireBase
}