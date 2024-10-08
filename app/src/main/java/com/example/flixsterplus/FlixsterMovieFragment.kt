package com.example.flixsterplus

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.Headers
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlixsterMovieFragment(private val movieContext: Context) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list, container, false)
        val progress = view.findViewById<View>(R.id.progressBar) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.movieList) as RecyclerView
        val context =  view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progress, recyclerView)
        return view
    }
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client[
            "https://api.themoviedb.org/3/movie/popular?language=en-US&page=1",
            params,
            object : JsonHttpResponseHandler()

            {
                override fun onFailure(
                    statusCode: Int,
                    headers: okhttp3.Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    throwable?.message?.let {
                        Log.e("FlixsterMoviesFragment", response)
                    }
                }

                override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                    // The wait for a response is over
                    progressBar.hide()

                    //Parse JSON into Models
                    val resultsJSON : String = json.jsonObject.get("results").toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<FlixsterMovie>>() {}.type
                    val models : List<FlixsterMovie> = gson.fromJson(resultsJSON, arrayMovieType)
                    recyclerView.adapter = FlixsterMoviesRecyclerViewAdapter(models, movieContext)

                    // Look for this in Logcat:
                    Log.d("FlixsterMoviesFragment", "response successful")
                }
            }

        ]
    }
}