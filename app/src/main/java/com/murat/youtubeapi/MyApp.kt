package com.murat.youtubeapi

import android.app.Application
import com.murat.youtubeapi.repository.Repository

class MyApp: Application() {

     val repository : Repository by lazy {
        Repository()
    }
}