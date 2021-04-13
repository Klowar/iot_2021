package com.rishat.shamsutdinov.mqttclient.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.gson.Gson
import com.rishat.shamsutdinov.mqttclient.MusicService
import com.rishat.shamsutdinov.mqttclient.R
import com.rishat.shamsutdinov.mqttclient.Utils.*
import com.rishat.shamsutdinov.mqttclient.data.MusicFromNet
import com.rishat.shamsutdinov.mqttclient.data.MusicItem
import com.rishat.shamsutdinov.mqttclient.mqqtModule.MQTTClient
import com.rishat.shamsutdinov.mqttclient.mqqtModule.WirellesDeviceSettings
import org.eclipse.paho.client.mqttv3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientFragment : Fragment() {
    private lateinit var mqttClient: MQTTClient
    private var index = 0
    private val deviceSettings = WirellesDeviceSettings()
    private var songs: MutableList<MusicFromNet> = mutableListOf()
    private val gson = Gson()
    private lateinit var settings  : String
    private val nowPlaying = MutableLiveData("")
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://ec2-3-131-162-71.us-east-2.compute.amazonaws.com:9000")
            .build()
            .create(MusicService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlaying.observe(this, {
            view?.findViewById<TextView>(R.id.current_track)?.text = it
        })
        retrofit.getSongs().enqueue(object : Callback<MutableList<MusicFromNet>> {
            override fun onResponse(call: Call<MutableList<MusicFromNet>>, response: Response<MutableList<MusicFromNet>>) {
                songs = response.body() as MutableList<MusicFromNet>
            }

            override fun onFailure(call: Call<MutableList<MusicFromNet>>, t: Throwable) {
                Log.d(this.javaClass.name, t.message.toString())
            }

        })
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mqttClient.isConnected()) {
                    // Disconnect from MQTT Broker
                    mqttClient.disconnect(object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            Log.d(this.javaClass.name, "Disconnected")

                            Toast.makeText(
                                    context,
                                    "MQTT Disconnection success",
                                    Toast.LENGTH_SHORT
                            ).show()

                            // Disconnection success, come back to Connect Fragment
                            findNavController().navigateUp()
                        }

                        override fun onFailure(
                                asyncActionToken: IMqttToken?,
                                exception: Throwable?
                        ) {
                            Log.d(this.javaClass.name, "Failed to disconnect")
                        }
                    })
                } else {
                    Log.d(this.javaClass.name, "Impossible to disconnect, no server connected")
                }
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get arguments passed by ConnectFragment
        val serverURI = arguments?.getString(MQTT_SERVER_URI_KEY)
        val clientId = arguments?.getString(MQTT_CLIENT_ID_KEY)
        val username = arguments?.getString(MQTT_USERNAME_KEY)
        val pwd = arguments?.getString(MQTT_PWD_KEY)
        val topic = arguments?.getString(MQTT_TOPIC_KEY)
        settings = "$topic/settings"

        // Check if passed arguments are valid
        if (serverURI != null &&
                clientId != null &&
                username != null &&
                pwd != null
        ) {
            // Open MQTT Broker communication
            mqttClient = MQTTClient(context, serverURI, clientId)

            // Connect and login to MQTT Broker
            mqttClient.connect(username,
                    pwd,
                    object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            Log.d(this.javaClass.name, "Connection success")
                            mqttClient.setBufferOpts()
                            Toast.makeText(context, "MQTT Connection success", Toast.LENGTH_SHORT)
                                    .show()
                        }

                        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                            Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")

                            Toast.makeText(
                                    context,
                                    "MQTT Connection fails: ${exception.toString()}",
                                    Toast.LENGTH_SHORT
                            ).show()

                            // Come back to Connect Fragment
                            findNavController().navigate(R.id.action_ClientFragment_to_ConnectFragment)
                        }
                    },
                    object : MqttCallback {
                        override fun messageArrived(topic: String?, message: MqttMessage?) {
                            val msg = "Receive message: ${message.toString()} from topic: $topic"
                            Log.d(this.javaClass.name, msg)

                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }

                        override fun connectionLost(cause: Throwable?) {
                            Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
                        }

                        override fun deliveryComplete(token: IMqttDeliveryToken?) {
                            Log.d(this.javaClass.name, "Delivery complete")
                        }
                    })
        } else {
            // Arguments are not valid, come back to Connect Fragment
            findNavController().navigate(R.id.action_ClientFragment_to_ConnectFragment)
        }

        view.findViewById<Button>(R.id.button_disconnect).setOnClickListener {
            if (mqttClient.isConnected()) {
                // Disconnect from MQTT Broker
                mqttClient.disconnect(object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d(this.javaClass.name, "Disconnected")

                        Toast.makeText(context, "MQTT Disconnection success", Toast.LENGTH_SHORT)
                                .show()

                        // Disconnection success, come back to Connect Fragment
                        findNavController().navigate(R.id.action_ClientFragment_to_ConnectFragment)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d(this.javaClass.name, "Failed to disconnect")
                    }
                })
            } else {
                Log.d(this.javaClass.name, "Impossible to disconnect, no server connected")
            }
        }

        view.findViewById<ImageButton>(R.id.play).setOnClickListener {
            if (deviceSettings.playBack.isNullOrBlank()) {
                if (index >= 0 && index < songs.size) {
                    val data = MusicItem(songs[index].url)
                    if (topic != null) {
                        publish(topic, gson.toJson(data))
                    }
                }
            } else {
                deviceSettings.playBack = "play"
                publish(settings, gson.toJson(deviceSettings))
            }
            nowPlaying.postValue(songs[index].name)
            view.findViewById<ImageButton>(R.id.pause).visibility = View.VISIBLE
            it.visibility = View.INVISIBLE
        }

        view.findViewById<ImageButton>(R.id.pause).setOnClickListener {
            deviceSettings.playBack = "stop"
            publish(settings, gson.toJson(deviceSettings))
            view.findViewById<ImageButton>(R.id.play).visibility = View.VISIBLE
            it.visibility = View.INVISIBLE
            nowPlaying.postValue("")
        }

        view.findViewById<ImageButton>(R.id.next).setOnClickListener {
            val tempIndex = index + 1
            if (tempIndex < songs.size) {
                index = tempIndex
            } else {
                index = 0
            }
            val data = MusicItem(songs[index].url)
            nowPlaying.postValue(songs[index].name)
            if (topic != null) {
                publish(topic, gson.toJson(data))
            }
        }
        view.findViewById<ImageButton>(R.id.previous).setOnClickListener {
            val tempIndex = index - 1
            if (tempIndex >= 0) {
                index = tempIndex
            } else {
                index = songs.size - 1
            }
            val data = MusicItem(songs[index].url)
            nowPlaying.postValue(songs[index].name)
            if (topic != null) {
                publish(topic, gson.toJson(data))
            }
        }
        view.findViewById<SwitchMaterial>(R.id.isRepeat).setOnCheckedChangeListener { _, isChecked ->
            deviceSettings.repeat = if (isChecked) 1 else 0
            publish(settings, gson.toJson(deviceSettings))
        }
        view.findViewById<SwitchMaterial>(R.id.light).setOnCheckedChangeListener { _, isChecked ->
            deviceSettings.light = if (isChecked) 1 else 0
            publish(settings, gson.toJson(deviceSettings))
        }
        view.findViewById<SeekBar>(R.id.seekbar)
                .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                    ) {
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        deviceSettings.volume = seekBar?.progress ?: 100
                        publish(settings, gson.toJson(deviceSettings))
                    }

                })
    }

    private fun publish(topic: String, message: String) {
        if (mqttClient.isConnected()) {
            mqttClient.publish(topic,
                    message,
                    1,
                    false,
                    object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            val msg = "Publish message: $message to topic: $topic"
                            Log.d(this.javaClass.name, msg)
                        }

                        override fun onFailure(
                                asyncActionToken: IMqttToken?,
                                exception: Throwable?
                        ) {
                            Log.d(this.javaClass.name, "Failed to publish message to topic")
                        }
                    })
        } else {
            Log.d(this.javaClass.name, "Impossible to publish, no server connected")
        }
    }

    private fun procedeLink(link: String): String {
        return link.split('/').last()
    }
}