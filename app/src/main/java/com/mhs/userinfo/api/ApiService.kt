package com.mhs.userinfo.api

import com.mhs.userinfo.response.UserDetailsResponse
import com.mhs.userinfo.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //List all user
    @GET("characters")
    suspend fun getUsersList(): Response<UserListResponse>

    //Get user details data
    @GET("character/{id}")
    suspend fun getUserDetails(@Path("id") id: String) : Response<UserDetailsResponse>
}