package com.steghold.stegoradio

import android.graphics.RenderEffect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTopPadding()
        setSongMetadata("Master of Puppets", "Metallica")
        loadArtwork("https://upload.wikimedia.org/wikipedia/en/b/b2/Metallica_-_Master_of_Puppets_cover.jpg")
    }

    private fun setTopPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView)
        { _: View?, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val space = findViewById<Space>(R.id.spacer)
            space.layoutParams = ConstraintLayout.LayoutParams(0, insets.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setSongMetadata(title: String, artist: String) {
        val songName = findViewById<TextView>(R.id.songNameText)
        val artistName = findViewById<TextView>(R.id.artistText)

        songName.text = title
        artistName.text = artist
    }

    private fun loadArtwork(url: String) {
        val artworkImage = findViewById<ImageView>(R.id.artworkImage)
        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)

        Picasso.get()
            .load(url)
            .placeholder(R.color.black)
            .into(artworkImage)

        Picasso.get()
            .load(url)
            .placeholder(R.color.black)
            .fit()
            .centerCrop()
            .transform(BlurTransform())
            .into(backgroundImage)
    }
}