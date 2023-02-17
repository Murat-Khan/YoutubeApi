package com.murat.youtubeapi.ui.detailplaylist

import androidx.lifecycle.LiveData
import com.murat.youtubeapi.MyApp
import com.murat.youtubeapi.core.network.result.Resource

import com.murat.youtubeapi.core.ui.BaseViewModel
import com.murat.youtubeapi.data.remote.model.Playlists

class DetailViewModel:BaseViewModel() {

    fun  getPlaylistDetails(id: String): LiveData<Resource<Playlists>> {
        return MyApp().repository.getPlaylistDetails(id)
    }

}