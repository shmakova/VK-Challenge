package ru.shmakova.vk.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.shmakova.vk.data.network.models.NewsFeedModel

interface VkApiService {
    @GET("newsfeed.getDiscoverForContestant")
    fun getDiscoverForContestant(
        @Query("count") count: Int = 30,
        @Query("start_from") startFrom: String? = null,
        @Query("extended") extended: Int = 1,
        @Query("access_token") token: String,
        @Query("v") apiVersion: Double = 5.87
    ): Single<NewsFeedModel>
}
