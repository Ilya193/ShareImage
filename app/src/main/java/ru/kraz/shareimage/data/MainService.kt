package ru.kraz.shareimage.data

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MainService {
    @Multipart
    @POST("/upload")
    suspend fun upload(@Part image: MultipartBody.Part, @Query("id") id: Int): Int

    @GET("/images/")
    suspend fun fetchImages(): List<String>
}