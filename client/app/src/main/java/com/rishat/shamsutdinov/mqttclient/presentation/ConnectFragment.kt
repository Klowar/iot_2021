package com.rishat.shamsutdinov.mqttclient.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.rishat.shamsutdinov.mqttclient.R
import com.rishat.shamsutdinov.mqttclient.Utils.*

class ConnectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_connect).setOnClickListener {
            val mqttCredentialsBundle = bundleOf(
                MQTT_SERVER_URI_KEY to MQTT_SERVER_URI,
                MQTT_CLIENT_ID_KEY to MQTT_CLIENT_ID,
                MQTT_USERNAME_KEY to MQTT_USERNAME,
                MQTT_PWD_KEY to MQTT_PWD_KEY
            )

            findNavController().navigate(
                R.id.action_ConnectFragment_to_ClientFragment,
                mqttCredentialsBundle
            )
        }
    }
}