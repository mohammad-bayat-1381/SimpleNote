package com.example.simplenote.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/") // Emulator's access to localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}
