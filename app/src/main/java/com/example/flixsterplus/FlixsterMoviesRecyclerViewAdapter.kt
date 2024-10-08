package com.example.flixsterplus

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson

const val MOVIE_DETAIL = "Movie_Detail"

class FlixsterMoviesRecyclerViewAdapter(
    private val movies: List<FlixsterMovie>,
    private val mListener: Context)
    : RecyclerView.Adapter<FlixsterMoviesRecyclerViewAdapter.MovieViewHolder>() {

    class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: FlixsterMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movieName) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(R.id.movieImg) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.mItem = movie
        holder.mMovieTitle.text = movie.title

        val loader = Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + movie.movieImageUrl)
            .placeholder(R.drawable.loading)
            .centerInside()
            .transform(RoundedCorners(30))
            .into(holder.mMovieImage)

        val details = Gson()
        val movieInfo = details.toJson(movie)
        holder.mView.setOnClickListener {
            val intent = Intent(mListener, DetailActivity::class.java)
            intent.putExtra(MOVIE_DETAIL, movieInfo)
            mListener.startActivity(intent)
        }
    }
}