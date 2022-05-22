package com.capstone.momomeal.feature

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class ChatMessagingService : FirebaseMessagingService() {
    private val TAG = "ChatMessagingService"

    override fun onNewToken(token: String) {
        // 토큰이 갱신되면 처리해야 하는 작업들
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // FCM이 수신될때마다 실행되는 부분
        super.onMessageReceived(message)

        if (message.data.isNotEmpty()) {
            sendNotification(message)
        }
    }

    fun sendNotification (message: RemoteMessage) {

    }
}