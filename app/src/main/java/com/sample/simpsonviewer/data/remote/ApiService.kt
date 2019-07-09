package com.sample.simpsonviewer.data.remote

import com.sample.simpsonviewer.model.BaseModel

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?format=json")
    fun getCharacterViewer(@Query("q") query: String): Single<BaseModel>

}
