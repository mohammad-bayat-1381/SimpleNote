package com.example.simplenote.data.remote

import com.example.simplenote.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private lateinit var tokenManager: TokenManager

    fun init(manager: TokenManager) {
        tokenManager = manager
    }

    private val retrofitNoAuth: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val authService: AuthService by lazy {
        retrofitNoAuth.create(AuthService::class.java)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(tokenManager, authService)) // ✅ break the loop
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                tokenManager.getAccessToken()?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val noteApi: NoteApi by lazy {
        retrofit.create(NoteApi::class.java)
    }
}
