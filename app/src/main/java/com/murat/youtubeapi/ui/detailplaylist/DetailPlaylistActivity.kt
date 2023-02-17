package com.murat.youtubeapi.ui.detailplaylist


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.murat.youtubeapi.R
import com.murat.youtubeapi.core.network.result.Status
import com.murat.youtubeapi.core.ui.BaseActivity
import com.murat.youtubeapi.databinding.ActivityDetailPlaylistBinding
import com.murat.youtubeapi.ui.playlists.PlaylistsActivity.Companion.ID


class DetailPlaylistActivity : BaseActivity<ActivityDetailPlaylistBinding, DetailViewModel>() {

    private var detailAdapter = DetailAdapter()
    private val id: String?
        get() = intent.getStringExtra(ID)


    override fun initListener() {
        super.initListener()
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
                        binding.videoCount.text = String.format(
                            it.contentDetails.itemCount.toString(),
                            R.string.video_series)
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
        isNetworkAvailable(this)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return true.also { binding.checkInternet.root.isVisible = it }
    }

}