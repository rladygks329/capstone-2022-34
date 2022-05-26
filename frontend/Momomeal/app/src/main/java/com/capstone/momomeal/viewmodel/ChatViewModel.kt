//package com.capstone.momomeal.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import com.capstone.momomeal.data.Chat
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import ua.naiksoftware.stomp.Stomp
//import ua.naiksoftware.stomp.dto.LifecycleEvent
//
//class ChatViewModel(val roomId: Int, val userId: Int) : ViewModel() {
//    private val TAG = "ChatViewModel"
//    val url = "http://example.com:8080/"
//    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
//
//    fun initStomp() {
//        stompClient.lifecycle()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//            when (it.type) {
//                LifecycleEvent.Type.OPENED -> {
//                    Log.d(TAG, "Stomp Connection Opened")
//                }
//                LifecycleEvent.Type.CLOSED -> {
//                    Log.d(TAG, "Stomp Connection Opened")
//                }
//                LifecycleEvent.Type.ERROR -> {
//                    Log.d(TAG, "Stomp Connection Opened")
//                }
//                LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
//                    Log.d(TAG, "Stomp Connection Opened")
//                }
//                else -> {
//                    Log.d(TAG, "Stomp Connection Opened")
//                }
//            }
//        }
//        // 이 주소 기준으로 메시지를 받는다.
//        stompClient.topic("/chat/enter").subscribe()
//    }
//
//    fun megSend(msg: String) {
//        var chat = Chat(userId, msg)
//        stompClient.send("/chat/room/" + roomId, chat.toString())
//    }
//
//    fun disconnect() {
//        stompClient.disconnect()
//    }
//
//}
