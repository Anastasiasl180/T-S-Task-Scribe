package com.aopr.shared_domain

sealed class Responses<T>(val data:T?=null,val message:Int? = null) {
    class Loading<T>:Responses<T>()
    class Success<T>(data:T):Responses<T>(data)
    class Error<T>(message:Int):Responses<T>(message = message)

}
