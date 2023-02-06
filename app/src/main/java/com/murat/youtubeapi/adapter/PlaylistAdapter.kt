package com.murat.youtubeapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murat.youtubeapi.R
import com.murat.youtubeapi.databinding.ItemPlaylistBinding
import com.murat.youtubeapi.model.Item


class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder>() {

    private var playlists = arrayListOf<Item>()
    private lateinit var listener: OnItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHolder {
        return PlaylistHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    fun setListener(onItemClick: OnItemClick) {
        listener = onItemClick
    }

    fun setPlayList(list: List<Item>) {
        playlists.addAll(list)
        notifyItemChanged(itemCount - 1)
    }

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int) {
        holder.onBind(playlists[position], position)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    inner class PlaylistHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(playlists: Item, position: Int) {
            if (playlists.snippet.thumbnails.maxres?.url != null)
                Glide.with(binding.imgPlaylist).load(playlists.snippet.thumbnails.default.url)
                    .into(binding.imgPlaylist)
            binding.playlistVideoCount.text = itemView.context.getString(
                R.string.video_series,
                playlists.contentDetails.itemCount
            )
            binding.playlistName.text = playlists.snippet.title
            itemView.setOnClickListener {
                listener.onClicked(playlists, position)
            }

        }
    }

    interface OnItemClick {
        fun onClicked(list: Item, position: Int)
    }
}