package com.murat.youtubeapi.data.remote

import com.murat.youtubeapi.data.remote.model.Playlists
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

    @GET("playlistItems")
    fun getPlaylistDetails(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults : Int
    ): Call<Playlists>

}