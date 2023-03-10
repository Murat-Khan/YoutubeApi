package com.murat.youtubeapi.ui.playlists

import androidx.lifecycle.LiveData
import com.murat.youtubeapi.MyApp
import com.murat.youtubeapi.core.network.result.Resource
import com.murat.youtubeapi.core.ui.BaseViewModel
import com.murat.youtubeapi.data.remote.model.Playlists


class PlaylistsViewModel : BaseViewModel() {

    fun getPlaylists(pageToken: String?): LiveData<Resource<Playlists>> {
        return MyApp().repository.getPlaylists(pageToken)
    }

}