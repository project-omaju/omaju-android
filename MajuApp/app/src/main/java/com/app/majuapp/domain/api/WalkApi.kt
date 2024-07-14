package com.app.majuapp.domain.api

import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WalkApi {

    // 산책로 호출
    // Retrieve a trail based on the current request.

    @GET("walking/walking-trails/{lat}/{lon}")
    suspend fun getWalkingTrails(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
    ): Response<WalkingTrailResultData>

} // End of WalkApi interface