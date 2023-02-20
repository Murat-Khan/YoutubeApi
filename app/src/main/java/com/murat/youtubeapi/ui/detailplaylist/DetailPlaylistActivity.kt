package com.murat.youtubeapi.ui.detailplaylist

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.murat.youtubeapi.core.network.NetworkInfo
import com.murat.youtubeapi.core.network.result.Status
import com.murat.youtubeapi.core.ui.BaseActivity
import com.murat.youtubeapi.data.remote.model.Item
import com.murat.youtubeapi.databinding.ActivityDetailPlaylistBinding
import com.murat.youtubeapi.ui.playlists.PlaylistsActivity.Companion.ID
import com.murat.youtubeapi.ui.videoplayer.VideoPlayerActivity


class DetailPlaylistActivity : BaseActivity<ActivityDetailPlaylistBinding, DetailViewModel>(),DetailAdapter.OnItemClick {

    private var detailAdapter = DetailAdapter()
    private val id: String?
        get() = intent.getStringExtra(ID)


    override fun initListener() {
        super.initListener()

        detailAdapter.setListener(this)
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.checkInternet.btnTryAgain.setOnClickListener {
            binding.checkInternet.root.isVisible = false
            loadDetailPlaylist()

        }
    }


    override fun initViews() {
        super.initViews()
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        loadDetailPlaylist()
    }

    private fun loadDetailPlaylist() {

        viewModel.getPlaylistDetails(id!!).observe(this) { video ->
            when (video.status) {
                Status.SUCCESS -> {
                    video.data?.let { video -> detailAdapter.setPlayList(video.items) }
                    binding.rvPlaylist.adapter = detailAdapter
                    video.data?.items?.forEach {
                        binding.title.text = it.snippet.title
                        binding.description.text = it.snippet.description
                        binding.videoCount.text = it.contentDetails.itemCount.toString()

                        binding.loader.isVisible = false
                    }
                }
                Status.LOADING -> {
                    binding.loader.isVisible = true
                }

                Status.ERROR -> {
                    binding.loader.isVisible = false
                }
            }
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override fun setupConnection() {
        super.setupConnection()
        binding.checkInternet.root.isVisible = !NetworkInfo.isNetworkAvailable(this)
    }

    override fun onItemClick(video: Item,position: Int) {
        Intent(this@DetailPlaylistActivity, VideoPlayerActivity::class.java).apply {
            putExtra(VIDEOURL, video.contentDetails.videoId)

            startActivity(this)
        }
    }
    companion object{
        const val VIDEOURL = "video_url"

    }

}