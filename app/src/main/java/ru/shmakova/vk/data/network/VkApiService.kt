package ru.shmakova.vk.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.shmakova.vk.data.network.models.IgnoreResponse
import ru.shmakova.vk.data.network.models.LikeResponse
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

    @POST("newsfeed.ignoreItem")
    fun ignoreItem(
        @Query("type") type: String = "wall",
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long,
        @Query("access_token") token: String,
        @Query("v") apiVersion: Double = 5.87
    ): Single<IgnoreResponse>

    @POST("likes.add")
    fun likeItem(
        @Query("type") type: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long,
        @Query("access_token") token: String,
        @Query("v") apiVersion: Double = 5.87
    ): Single<LikeResponse>
}
