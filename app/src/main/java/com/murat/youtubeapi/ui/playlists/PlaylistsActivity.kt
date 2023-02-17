package com.murat.youtubeapi.ui.playlists

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murat.youtubeapi.core.network.result.Status
import com.murat.youtubeapi.core.ui.BaseActivity
import com.murat.youtubeapi.databinding.ActivityPlaylistsBinding
import com.murat.youtubeapi.data.remote.model.Item
import com.murat.youtubeapi.ui.detailplaylist.DetailPlaylistActivity

class PlaylistsActivity : BaseActivity<ActivityPlaylistsBinding, PlaylistsViewModel>(),
    PlaylistAdapter.OnItemClick {

    private var playlistAdapter = PlaylistAdapter()
    private var loading = true
    private var totalCount: Int = 1
    private var pageToken: String? = null




    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[PlaylistsViewModel::class.java]

        viewModel.loading.observe(this) {
            if (pageToken == null){

                binding.loader.isVisible = it
            }
            binding.loader.isVisible = false
            binding.loaderData.isVisible = it
        }

        loadPlaylist()
    }

    private fun loadPlaylist() {
        loading = true
        viewModel.getPlaylists(pageToken).observe(this) {

            when(it.status){

                Status.SUCCESS -> {

                    pageToken = it.data?.nextPageToken
                    totalCount = it.data?.pageInfo?.totalResults?:0
                    it.data?.items?.let { it1 -> playlistAdapter.setPlayList(it1) }
                    viewModel.loading.value = false

                }

                Status.LOADING -> {

                    viewModel.loading.value = true
                }

                Status.ERROR -> {
                    binding.checkInternet.root.isVisible = true
                    viewModel.loading.value = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            loading = false
        }
    }

    override fun initViews() {
        super.initViews()
        binding.rvPlaylist.adapter = playlistAdapter
        setupPagination()

    }

    private fun setupPagination() {
        binding.rvPlaylist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loading && playlistAdapter.itemCount < totalCount) {
                    if (lastItem == playlistAdapter.itemCount - 1) {
                        loadPlaylist()
                    }
                }
            }
        })
    }

    override fun initListener() {
        super.initListener()
        playlistAdapter.setListener(this)

        binding.checkInternet.btnTryAgain.setOnClickListener {

            binding.checkInternet.root.isVisible = false
            loadPlaylist()

        }
    }

    override fun onItemClick(list: Item) {
        Intent(this@PlaylistsActivity, DetailPlaylistActivity::class.java).apply {
            putExtra(ID, list.id)
            startActivity(this)
        }
    }

    companion object {
        const val ID = "id"

    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(layoutInflater)

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








