package com.rishat.shamsutdinov.mqttclient.mqqtModule

data class WirellesDeviceSettings(
    var repeat: Int = 1,
    var light: Int = 1,
    var volume: Int = 100,
    var playback: String? = null
)