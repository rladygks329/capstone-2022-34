package com.capstone.momomeal.feature

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ChatMessagingService : FirebaseMessagingService() {
    private val TAG = "ChatMessagingService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

//        if ()
    }

    fun sendNotification (
        message: RemoteMessage
    ) {

    }
}