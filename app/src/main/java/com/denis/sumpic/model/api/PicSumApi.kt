package com.denis.sumpic.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PicSumApi {
    @GET("v2/list")
    fun getJsonData(
        @Query("page") page:Int,
        @Query("limit") limit: Int
    ): Call<List<PicSumDataJson>>
}