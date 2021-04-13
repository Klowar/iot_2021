package com.rishat.shamsutdinov.mqttclient

import com.rishat.shamsutdinov.mqttclient.data.MusicFromNet
import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/api?from=0&amount=10")
    fun getSongs() : Call<MutableList<MusicFromNet>>
}