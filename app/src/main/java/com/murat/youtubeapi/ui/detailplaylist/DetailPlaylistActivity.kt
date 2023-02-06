package com.murat.youtubeapi.ui.detailplaylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.murat.youtubeapi.R

import com.murat.youtubeapi.ui.playlists.PlaylistsActivity.Companion.ID

class DetailPlaylistActivity : AppCompatActivity() {

    private val id : String?
    get() = intent.getStringExtra(ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_playlist)

        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
    }
}