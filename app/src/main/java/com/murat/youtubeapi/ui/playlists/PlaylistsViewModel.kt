package com.murat.youtubeapi.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murat.youtubeapi.BuildConfig
import com.murat.youtubeapi.`object`.Constant
import com.murat.youtubeapi.base.BaseViewModel
import com.murat.youtubeapi.model.Playlists
import com.murat.youtubeapi.remote.ApiService
import com.murat.youtubeapi.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistsViewModel : BaseViewModel() {


    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }


    fun playlist(pageToken: String?): LiveData<Playlists> {
        return getPlaylist(pageToken)
    }

    private fun getPlaylist(pageToken: String?): LiveData<Playlists> {

        val data = MutableLiveData<Playlists>()
        setLoader(true)

        apiService.getPlaylists(
            BuildConfig.API_KEY,
            Constant.PART,
            Constant.channelId,
            Constant.maxResults,
            pageToken
        )
            .enqueue(object : Callback<Playlists> {
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if (response.isSuccessful) {
                        data.value = response.body()

                    }
                    setLoader(false)
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                    print(t.stackTrace)
                    setLoader(false)
                }

            })

        return data
    }
}