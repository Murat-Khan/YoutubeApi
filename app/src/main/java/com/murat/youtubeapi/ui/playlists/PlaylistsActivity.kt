package com.murat.youtubeapi.ui.playlists


import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murat.youtubeapi.adapter.PlaylistAdapter
import com.murat.youtubeapi.base.BaseActivity
import com.murat.youtubeapi.databinding.ActivityPlaylistsBinding
import com.murat.youtubeapi.model.Item
import com.murat.youtubeapi.ui.detailplaylist.DetailPlaylistActivity

class PlaylistsActivity : BaseActivity<ActivityPlaylistsBinding, PlaylistsViewModel>(), PlaylistAdapter.OnItemClick {

    private var playlistAdapter = PlaylistAdapter()
    private var loading = true
    private var totalCount: Int = 1
    private var pageToken: String? = null

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(layoutInflater)

    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[PlaylistsViewModel::class.java]

        viewModel.loaderData.observe(this) { binding.loader.isVisible = it }

        loadPlaylist()
    }

    private fun loadPlaylist() {
        loading = true
        viewModel.playlist(pageToken).observe(this) {

            if (it.items.firstOrNull()?.snippet != null) {
                pageToken = it.nextPageToken
                totalCount = it.pageInfo.totalResults
                playlistAdapter.setPlayList(it.items)
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
    }

    override fun onClicked(list: Item, position: Int) {
        Intent(this@PlaylistsActivity, DetailPlaylistActivity::class.java).apply {
            putExtra(ID, list.id)
            startActivity(this)
        }
    }
    companion object {
        const val ID = "id"
    }
}