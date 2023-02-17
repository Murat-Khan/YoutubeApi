package com.murat.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murat.youtubeapi.BuildConfig
import com.murat.youtubeapi.data.remote.ApiService
import com.murat.youtubeapi.core.network.RetrofitClient
import com.murat.youtubeapi.core.network.result.Resource
import com.murat.youtubeapi.data.remote.model.Playlists
import com.murat.youtubeapi.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun getPlaylists(pageToken: String?): LiveData<Resource<Playlists>> {

        val data = MutableLiveData<Resource<Playlists>>()

        data.value = Resource.loading()

        apiService.getPlaylists(
            BuildConfig.API_KEY,
            Constant.PART,
            Constant.channelId,
            Constant.maxResults,
            pageToken
        ).enqueue(object : Callback<Playlists> {
            override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }

            override fun onFailure(call: Call<Playlists>, t: Throwable) {
                print(t.stackTrace)
                data.value = Resource.error(t.stackTrace.toString(), null, null)
            }
        })

        return data
    }




    fun getPlaylistDetails(id: String): LiveData<Resource<Playlists>>{
        val detailLivData = MutableLiveData<Resource<Playlists>>()

        detailLivData.value = Resource.loading()

        apiService.getPlaylistDetails(BuildConfig.API_KEY,Constant.PART,id,Constant.maxResults)
            .enqueue(object : Callback<Playlists>{
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if (response.isSuccessful && response.body()!=null){

                        detailLivData.value = Resource.success(response.body())
                    }
                }
                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                  detailLivData.value = Resource.error(t.stackTrace.toString(), null, null)
                }
            })
        return detailLivData
    }

}