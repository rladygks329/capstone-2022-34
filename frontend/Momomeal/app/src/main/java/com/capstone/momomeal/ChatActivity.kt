package com.capstone.momomeal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.*
import com.capstone.momomeal.databinding.LayoutChatHolderBinding
import com.capstone.momomeal.feature.adapter.ChatAdapter
import com.capstone.momomeal.feature.adapter.ChatMemberAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*****************************
 * File Name : ChatActivity.kt
 * Writer : 장진우
 ******************************/


class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"

    // For API
    val momomeal = MomomealService.momomealAPI
    private val fireDatabase = FirebaseDatabase.getInstance()

    // ChatActivity에는 Navigation Drawer가 달려있음.
    // 이거는 반드시 DrawerLayout이 최상위가 되어야 하므로 Activity_chat을 Bind하지 않음.
    private lateinit var binding: LayoutChatHolderBinding // activityChatHolderBinding이 아님
    private lateinit var memberList: ArrayList<User_light>
    private lateinit var memberMap: HashMap<Int, membInfo> // 멤버의 id로 나머지 정보를 찾는 HashMap
    private lateinit var imm : InputMethodManager

    // intent로 받는 데이터들
    private val chatList: ArrayList<Chat> = arrayListOf()
    private val myInfoLight: User_light by lazy {
        intent.getParcelableExtra<User_light>("myinfo") as User_light
    }
    private val chatroomInfo: Chatroom by lazy {
        intent.getParcelableExtra<Chatroom>("chatroominfo") as Chatroom
    }

    // Adapters
    private val chatAdapter : ChatAdapter by lazy {
        ChatAdapter(myInfoLight, memberMap, chatList)
    }
    private val chatMemberAdapter : ChatMemberAdapter by lazy {
        ChatMemberAdapter(memberMap, memberList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutChatHolderBinding.inflate(layoutInflater)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager


        // Chatting RecyclerView Setting
//        val chatAdapter = ChatAdapter(myInfoLight, chatList)
        binding.activityChat.rvChatArea.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.activityChat.rvChatArea.adapter = chatAdapter

        // drawer`s Member information RecyclerView Setting
        binding.nvChatNavigation.rvMemberList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.nvChatNavigation.rvMemberList.adapter = chatMemberAdapter



        // ChatActivity Setting
        binding.activityChat.tvChatTitle.text = chatroomInfo.nameRoom

        // Listeners in Chat Activity
        binding.activityChat.btnChatBack.setOnClickListener {
            onBackPressed()
        }
        binding.activityChat.btnMore.setOnClickListener {
            binding.root.openDrawer(GravityCompat.END)
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            Toast.makeText(this,"More CLicked",Toast.LENGTH_SHORT).show()
        }
        binding.activityChat.btnChatSend.setOnClickListener {
            Log.d(TAG, "Send Clickevent")
            val tmpstr = binding.activityChat.etChatContent.text.toString()
            binding.activityChat.etChatContent.text.clear()
//            if (tmpstr != "") {
//            }
        }

        // Drawer Setiing
        binding.nvChatNavigation.tvDeliverText.text = chatroomInfo.nameStore
        binding.nvChatNavigation.tvReceiveText.text = chatroomInfo.namePickupPlace
        // 이후 지도 세팅으로 넘어감


        // Listeners in drawer
        binding.nvChatNavigation.btnCloseDrawer.setOnClickListener {
            binding.root.closeDrawers()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (binding.root.isDrawerOpen(GravityCompat.END)) {
            binding.root.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }

}