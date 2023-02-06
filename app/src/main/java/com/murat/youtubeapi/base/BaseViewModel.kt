package com.murat.youtubeapi.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loaderData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setLoader(value: Boolean) {
        loaderData.value = value
    }

}