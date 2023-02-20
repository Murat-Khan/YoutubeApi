package com.murat.youtubeapi.data.remote


import com.murat.youtubeapi.data.remote.model.Playlists
import com.murat.youtubeapi.data.remote.model.Video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
   suspend fun getPlaylists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults : Int,
        @Query("pageToken") pageToken : String?
    ): Response<Playlists>

    @GET("playlistItems")
   suspend fun getPlaylistDetails(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults : Int
    ): Response<Playlists>

    @GET("videos")
   suspend fun getVideo(
        @Query("part") part:String ,
        @Query("id") videoId:String? ,
        @Query("key") apiKey :String
    ): Response<Video>

}