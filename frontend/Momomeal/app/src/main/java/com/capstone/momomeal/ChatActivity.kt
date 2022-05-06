package com.capstone.momomeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.momomeal.databinding.LayoutChatHolderBinding
import com.capstone.momomeal.data.Chat
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.feature.adapter.ChatAdapter
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

/*****************************
 * File Name : ChatActivity.kt
 * Writer : 장진우
 ******************************/
class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"
    val url = "http://10.0.2.2:8080/"

    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    // ChatActivity에는 Navigation Drawer가 달려있음.
    // 이거는 반드시 DrawerLayout이 최상위가 되어야 하므로 Activity_chat을 Bind하지 않음.
    private lateinit var binding: LayoutChatHolderBinding
    private val chatList: ArrayList<Chat> = arrayListOf()
    val myInfoLight: User_light by lazy {
        intent.getParcelableExtra<User_light>("myinfo") as User_light
    }
    val chatroomInfo: Chatroom by lazy {
        intent.getParcelableExtra<Chatroom>("chatroominfo") as Chatroom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutChatHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ChatActivity Setting
        binding.activityChat.tvChatTitle.text = chatroomInfo.nameRoom

        // Chatting RecyclerView Setting
//        val chatAdapter = ChatAdapter(myInfoLight, chatList)
        binding.activityChat.rvChatArea.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.activityChat.rvChatArea.adapter = ChatAdapter(myInfoLight, chatList)
        binding.activityChat.rvChatArea.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // Listeners in Chat Activity
        binding.activityChat.btnMore.setOnClickListener {
            binding.root.openDrawer(GravityCompat.END)
            Toast.makeText(this,"More CLicked",Toast.LENGTH_SHORT).show()
        }
        binding.activityChat.btnChatBack.setOnClickListener {
            onBackPressed()
        }

        // Listeners in drawer
        binding.nvChatNavigation.
        binding.nvChatNavigation.btnCloseDrawer.setOnClickListener {
            binding.root.closeDrawers()
        }

//        initStomp()
    }

    override fun onBackPressed() {
        if (binding.root.isDrawerOpen(GravityCompat.END)) {
            binding.root.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }
    fun initStomp() {
        stompClient.lifecycle()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                    else -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                }
            }
        // 이 주소 기준으로 메시지를 받는다.
        stompClient.topic("/chat/enter").subscribe {

        }
    }

//    fun megSend(msg: String) {
//        var chat = Chat(userId, msg)
//        stompClient.send("/chat/room/" + roomId, chat.toString())
//    }

    fun disconnect() {
        stompClient.disconnect()
    }
}