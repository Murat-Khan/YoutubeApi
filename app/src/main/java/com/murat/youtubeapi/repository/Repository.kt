package com.murat.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.murat.youtubeapi.core.network.result.Resource
import com.murat.youtubeapi.data.remote.RemoteDataSource
import com.murat.youtubeapi.data.remote.model.Playlists
import com.murat.youtubeapi.data.remote.model.Video
import kotlinx.coroutines.Dispatchers

class Repository {

    private val dataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    fun getPlaylists(pageToken: String?): LiveData<Resource<Playlists>> = liveData (Dispatchers.IO){
        emit(Resource.loading())
        val response = dataSource.getPlaylists(pageToken)
        emit(response)
    }


    fun getPlaylistDetails(id: String): LiveData<Resource<Playlists>> = liveData (Dispatchers.IO){
        emit(Resource.loading())
        val response = dataSource.getPlaylistDetails(id)
        emit(response)
    }

    fun getVideo(video:String?):LiveData<Resource<Video>> = liveData (Dispatchers.IO){
        emit(Resource.loading())
        val response = dataSource.getVideo(video)
        emit(response)

    }

}