package com.capstone.momomeal

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.*
import com.capstone.momomeal.databinding.LayoutChatHolderBinding
import com.capstone.momomeal.feature.adapter.ChatAdapter
import com.capstone.momomeal.feature.adapter.ChatMemberAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    val momomeal = MomomealService.momomealAPI

    // ChatActivity에는 Navigation Drawer가 달려있음.
    // 이거는 반드시 DrawerLayout이 최상위가 되어야 하므로 Activity_chat을 Bind하지 않음.
    private lateinit var binding: LayoutChatHolderBinding // activityChatHolderBinding이 아님
    private lateinit var memberMap: HashMap<Int, membInfo> // 멤버의 id로 나머지 정보를 찾는 HashMap

    // intent로 받는 데이터들
    private val chatList: ArrayList<Chat> = arrayListOf()
    val myInfoLight: User_light by lazy {
        intent.getParcelableExtra<User_light>("myinfo") as User_light
    }
    private lateinit var memberList: ArrayList<User_light>
    val chatroomInfo: Chatroom by lazy {
        intent.getParcelableExtra<Chatroom>("chatroominfo") as Chatroom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutChatHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        momomeal.getEnteredChatInfo(chatroomInfo.idChatroom)
            .enqueue(object: Callback<ArrayList<User_light>> {
                override fun onResponse(
                    call: Call<ArrayList<User_light>>,
                    response: Response<ArrayList<User_light>>
                ) {
                    Log.d("$TAG|get!", response.body().toString())
                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let {
                        memberList = response.body()!!
                    }
                }

                override fun onFailure(call: Call<ArrayList<User_light>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext, "네트워크 문제로 유저 정보를 가져오지 못했습니다.", Toast.LENGTH_LONG
                    ).show()
                }
            })

        // 내 정보는 삭제합니다.
        for (idx in memberList.indices) {
            if (memberList[idx].idUser == myInfoLight.idUser) {
                memberList.removeAt(idx)
                break
            }
        }
        // ID 순으로 정렬
        memberList.sortWith(compareBy<User_light> {it.idUser})

        // 비트맵과 이름을 가진 HashMap으로 변환
        var decodeBytes : ByteArray
        var tmpBitmap : Bitmap
        for (item in memberList) {
            decodeBytes = Base64.decode(item.profileImgUrl, Base64.DEFAULT)
            tmpBitmap = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)
            memberMap.put(item.idUser, membInfo(item.name, tmpBitmap))
        }

        // ChatActivity Setting
        binding.activityChat.tvChatTitle.text = chatroomInfo.nameRoom

        // Chatting RecyclerView Setting
//        val chatAdapter = ChatAdapter(myInfoLight, chatList)
        binding.activityChat.rvChatArea.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.activityChat.rvChatArea.adapter = ChatAdapter(myInfoLight, memberMap, chatList)
        binding.activityChat.rvChatArea.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // Listeners in Chat Activity
        binding.activityChat.btnChatBack.setOnClickListener {
            onBackPressed()
        }
        binding.activityChat.btnMore.setOnClickListener {
            binding.root.openDrawer(GravityCompat.END)
            Toast.makeText(this,"More CLicked",Toast.LENGTH_SHORT).show()
        }
        binding.activityChat.btnChatSend.setOnClickListener {
            val tmpstr = binding.activityChat.etChatContent.text.toString()
            if (tmpstr != "") {
                sendChat(tmpstr)
            }
        }
        // Drawer Setiing
        binding.nvChatNavigation.tvDeliverText.text = chatroomInfo.nameStore
        binding.nvChatNavigation.tvReceiveText.text = chatroomInfo.namePickupPlace
        // 이후 지도 세팅으로 넘어감

        // drawer`s Member information RecyclerView Setting
        binding.nvChatNavigation.rvMemberList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.nvChatNavigation.rvMemberList.adapter = ChatMemberAdapter(memberMap, memberList)
        binding.nvChatNavigation.rvMemberList.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // Listeners in drawer
        binding.nvChatNavigation.btnCloseDrawer.setOnClickListener {
            binding.root.closeDrawers()
        }

        initStomp()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
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
                        Log.d(TAG, "Stomp Connection Closed")
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.d(TAG, "Stomp Connection ERROR")
                        Log.e(TAG, "Stomp Connection ERROR : " + it.exception.toString())
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                        Log.d(TAG, "Stomp Connection Opened")
                    }
                    else -> {
                        Log.d(TAG, "ELSE : " + it.message)
                    }
                }
            }
        // 이 주소 기준으로 메시지를 받는다.
        stompClient.topic("/chat/enter").subscribe {
            it -> Log.d("Connection Success", it.payload)
        }
    }

    fun sendChat(msg: String) {
        var chat = Chat(myInfoLight.idUser, msg)
        stompClient.send("/chat/room/" + chatroomInfo.idChatroom, chat.toString())
    }

    fun disconnect() {
        stompClient.disconnect()
    }
}