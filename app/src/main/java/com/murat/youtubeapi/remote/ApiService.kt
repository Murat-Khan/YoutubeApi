package com.murat.youtubeapi.remote

import com.murat.youtubeapi.model.Playlists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    fun getPlaylists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults : Int,
        @Query("pageToken") pageToken : String?
    ): Call<Playlists>

}