package com.rishat.shamsutdinov.mqttclient.data

data class MusicItem(
        val music: String
)

data class MusicFromNet(
        val id: Int = 0,
        val url: String = "",
        val name: String = ""
)