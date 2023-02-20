package com.murat.youtubeapi.ui.videoplayer

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.murat.youtubeapi.core.network.result.Status
import com.murat.youtubeapi.core.ui.BaseActivity
import com.murat.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.murat.youtubeapi.ui.detailplaylist.DetailPlaylistActivity


class VideoPlayerActivity : BaseActivity<ActivityVideoPlayerBinding, VideoViewModel>() {

    private val video: String?
        get() = intent.getStringExtra(DetailPlaylistActivity.VIDEOURL)






    override fun initListener() {
        super.initListener()


        }
    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun initViews() {
        super.initViews()
        loadVideo(video)
    }

    private fun loadVideo(videoUrl: String?) {

        viewModel.getVideo(videoUrl).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.items?.forEach { data ->
                        Glide.with(binding.video).load(data.snippet.thumbnails.maxres?.url)
                            .into(binding.video)
                        binding.tvTitle.text = data.snippet.localized.title
                        binding.tvDescription.text = data.snippet.localized.description

                    }
                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(layoutInflater)


    }
}




