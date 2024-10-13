package com.example.dicodingevent.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            // Set custom timeouts here
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
                .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
                .writeTimeout(30, TimeUnit.SECONDS) // Set write timeout
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://event-api.dicoding.dev/events?active=0")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
