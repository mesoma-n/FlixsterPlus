package com.example.flixsterplus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movieTitle = findViewById<TextView>(R.id.movieName)
        val movieImage = findViewById<ImageView>(R.id.movieImg)
        val description = findViewById<TextView>(R.id.description)
        val popularity = findViewById<TextView>(R.id.popularity)
        val date= findViewById<TextView>(R.id.date)
        val movieJson = intent.getStringExtra(MOVIE_DETAIL)
        val gson = Gson()
        val movieInfo = gson.fromJson(movieJson, FlixsterMovie::class.java)
        movieTitle.text = movieInfo.title
        description.text = movieInfo.description
        popularity.text = "Popularity: ${movieInfo.popular.toString()}"
        date.text = "relased: ${movieInfo.release}"
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movieInfo.posterImageUrl)
            .transform(RoundedCorners(30))
            .into(movieImage)

    }

}