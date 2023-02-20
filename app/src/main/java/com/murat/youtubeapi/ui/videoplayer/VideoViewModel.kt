package com.murat.youtubeapi.ui.videoplayer


import androidx.lifecycle.LiveData
import com.murat.youtubeapi.MyApp
import com.murat.youtubeapi.core.network.result.Resource
import com.murat.youtubeapi.core.ui.BaseViewModel
import com.murat.youtubeapi.data.remote.model.Video


class VideoViewModel : BaseViewModel() {

    fun getVideo(video: String?): LiveData<Resource<Video>> {
        return MyApp().repository.getVideo(video)
}
}