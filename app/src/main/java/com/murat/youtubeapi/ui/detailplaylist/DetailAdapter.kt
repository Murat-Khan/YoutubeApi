package com.murat.youtubeapi.ui.detailplaylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murat.youtubeapi.data.remote.model.Item
import com.murat.youtubeapi.databinding.ItemDeteilBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailHolder>() {

    private var videos = arrayListOf<Item>()
    private lateinit var listener: DetailAdapter.OnItemClick


    fun setListener(onItemClick: DetailAdapter.OnItemClick) {
        listener = onItemClick
    }

    fun setPlayList(list: List<Item>) {
        videos.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
       return DetailHolder(ItemDeteilBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.onBind(videos[position],position)
    }

    override fun getItemCount(): Int {
       return videos.size
    }

    inner class DetailHolder(private val binding: ItemDeteilBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(video: Item, position: Int) {
            binding.videoDuration.text = video.snippet.publishedAt
            binding.title.text = video.snippet.title
            Glide.with(binding.imgVideo).load(video.snippet.thumbnails.medium.url)
                .into(binding.imgVideo)
            itemView.setOnClickListener {
                listener.onItemClick(video, position)
            }
        }

    }

    interface OnItemClick {
        fun onItemClick(video: Item, position: Int)
    }
}