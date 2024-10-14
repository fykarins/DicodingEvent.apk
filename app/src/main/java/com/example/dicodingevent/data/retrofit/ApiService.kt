package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.DetailEventResponse
import com.example.dicodingevent.data.response.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Untuk mendapatkan semua event berdasarkan status aktif
    @GET("/events")
    suspend fun getEvents(@Query("active") active: Int): Response<EventResponse>

    // Perbaikan endpoint untuk mendapatkan detail event berdasarkan ID
    @GET("/events/{id}")
    suspend fun getDetail(@Path("id") id: String): Response<DetailEventResponse>

    // Untuk mengirimkan review event
    @POST("events/{eventId}/reviews")
    suspend fun postReview(
        @Path("eventId") eventId: String,
        @Query("name") name: String,
        @Query("review") review: String
    ): Response<EventResponse>
}
