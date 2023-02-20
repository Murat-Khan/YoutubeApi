package com.murat.youtubeapi.data.remote

import com.murat.youtubeapi.BuildConfig
import com.murat.youtubeapi.core.network.BaseDataSource
import com.murat.youtubeapi.core.network.RetrofitClient
import com.murat.youtubeapi.core.network.result.Resource
import com.murat.youtubeapi.data.remote.model.Playlists
import com.murat.youtubeapi.data.remote.model.Video
import com.murat.youtubeapi.utils.Constant

class RemoteDataSource : BaseDataSource() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    suspend fun getPlaylists(pageToken: String?) : Resource<Playlists>{
        return getResult {
            apiService.getPlaylists(
                BuildConfig.API_KEY,
                Constant.PART,
                Constant.channelId,
                Constant.maxResults,
                pageToken
            )
        }
    }

    suspend fun getPlaylistDetails(id: String) : Resource<Playlists>{
        return getResult {
            apiService.getPlaylistDetails(BuildConfig.API_KEY,Constant.PART,id,Constant.maxResults)
        }
    }

    suspend fun getVideo(video:String?): Resource<Video>{
        return getResult { apiService.getVideo(Constant.PART,video,BuildConfig.API_KEY) }

    }
}