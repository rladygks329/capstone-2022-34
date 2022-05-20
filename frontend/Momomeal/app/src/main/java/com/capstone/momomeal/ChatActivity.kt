package com.capstone.momomeal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.*
import com.capstone.momomeal.databinding.LayoutChatHolderBinding
import com.capstone.momomeal.feature.adapter.ChatAdapter
import com.capstone.momomeal.feature.adapter.ChatMemberAdapter
import com.google.firebase.database.*
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
    private var isKeyboardOpen = false

    // For API
    val momomeal = MomomealService.momomealAPI
    private val fireDatabase = FirebaseDatabase.getInstance().reference

    // ChatActivity에는 Navigation Drawer가 달려있음.
    // 이거는 반드시 DrawerLayout이 최상위가 되어야 하므로 Activity_chat을 Bind하지 않음.
    private lateinit var binding: LayoutChatHolderBinding // activityChatHolderBinding이 아님
    private lateinit var memberList: ArrayList<User_light>
    private lateinit var memberMap: HashMap<Int, membInfo> // 멤버의 id로 나머지 정보를 찾는 HashMap
    private lateinit var imm : InputMethodManager

    // intent로 받는 데이터들
//    private val chatList: ArrayList<Chat> = arrayListOf()
    private val isNewChat: Boolean by lazy {
        intent.getBooleanExtra("isNewChat", false)
    }
    private val myInfoLight: User_light by lazy {
        intent.getParcelableExtra<User_light>("myinfo") as User_light
    }
    private val chatroomInfo: Chatroom by lazy {
        intent.getParcelableExtra<Chatroom>("chatroominfo") as Chatroom
    }

    // Adapters
    private val chatAdapter : ChatAdapter by lazy {
        ChatAdapter(myInfoLight, memberMap, chatroomInfo.idChatroom)
    }
    private val chatMemberAdapter : ChatMemberAdapter by lazy {
        ChatMemberAdapter(memberMap, memberList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutChatHolderBinding.inflate(layoutInflater)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // 빈 리스트를 미리 할당
        memberList = arrayListOf<User_light>()
        memberMap = hashMapOf<Int, membInfo>()

        // 키보드 설정을 위한 뷰 설정정
       setupView()

        // Chatting RecyclerView Setting
//        val chatAdapter = ChatAdapter(myInfoLight, chatList)
        //binding.activityChat.rvChatArea.layoutManager =
        //    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.activityChat.rvChatArea.apply {
            adapter = chatAdapter
            addOnLayoutChangeListener(onLayoutChangeListener)
            viewTreeObserver.addOnScrollChangedListener {
                if (isScrollable() && !isKeyboardOpen) {
                    setStackFromEnd()
                    removeOnLayoutChangeListener(onLayoutChangeListener)
                }
            }
        }
        binding.activityChat.rvChatArea.scrollToPosition(chatAdapter.itemCount - 1)


        // drawer`s Member information RecyclerView Setting
        binding.nvChatNavigation.rvMemberList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.nvChatNavigation.rvMemberList.adapter = chatMemberAdapter

        // 채팅방의 정보를 체크합니다.
        val chatmodel = ChatModel()

        if (isNewChat) {
            // 새로운 채팅방을 만듭니다.
            chatmodel.users.put(myInfoLight.idUser.toString(), true)
//            chatmodel.chats.put(myInfoLight.idUser.toString(), Chat(myInfoLight.idUser, "AAA"))
            val child : Map<String, ChatModel> = mapOf(chatroomInfo.idChatroom.toString() to chatmodel)
            fireDatabase.child("chatroom").updateChildren(child)
        } else {
            fireDatabase.child("chatroom")
                .child(chatroomInfo.idChatroom.toString())
                .child("chats").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatAdapter.notifyItemInserted(chatAdapter.itemCount - 1)
                        binding.activityChat.rvChatArea.scrollToPosition(chatAdapter.itemCount - 1)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("채팅 기록 가져오기 실패")
                    }
                })
            fireDatabase.child("chatroom")
                .child(chatroomInfo.idChatroom.toString())
                .child("users").addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                        TODO("채팅방에 누군가 입장함. ")
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                        TODO("Not yet implemented")
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
//                        TODO("채팅방에 누군가 나감")
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                        TODO("Not yet implemented")
                    }

                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
                    }
                })
        }
        setContentView(binding.root)
        // 멤버들의 정보를 받아옵니다.
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
                    response.body().let {
                        if (it == null) {
                            Toast.makeText(applicationContext,"Chatmember Failed ",Toast.LENGTH_SHORT).show()
                        } else {
                            memberList = it
                        }
                    }
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
                    var tmpBitmap : Bitmap?
                    var basic: Drawable?
                    for (item in memberList) {
                        if (item.profileImgUrl == null) {
                            basic = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_basic_profile)
                            tmpBitmap = basic!!.toBitmap()
                            memberMap.put(item.idUser, membInfo(item.name, tmpBitmap))
                        } else {
                            decodeBytes = Base64.decode(item.profileImgUrl, Base64.DEFAULT)
                            tmpBitmap = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)
                            memberMap.put(item.idUser, membInfo(item.name, tmpBitmap))
                        }
                    }
                }
                override fun onFailure(call: Call<ArrayList<User_light>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext, "네트워크 문제로 유저 정보를 가져오지 못했습니다.", Toast.LENGTH_LONG
                    ).show()
                }
            })

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
            if (tmpstr != "") {
                val msg = Chat(myInfoLight.idUser, tmpstr)
                chatAdapter.addChat(msg)
                fireDatabase.child("chatroom")
                    .child(chatroomInfo.idChatroom.toString()).child("chats").push().setValue(msg)
            }
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

    // RecyclerView의 키보드를 적당하게 위로 올리는 데 필요한 함수들입니다.
    private fun setupView() { // 키보드 Open/Close 체크
        binding.activityChat.clChatContainer.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.activityChat.clChatContainer.getWindowVisibleDisplayFrame(rect)
            val rootViewHeight = binding.activityChat.clChatContainer.rootView.height
            val heightDiff = rootViewHeight - rect.height()
            isKeyboardOpen = heightDiff > rootViewHeight * 0.25 // true == 키보드 올라감
        }
    }

    fun RecyclerView.isScrollable(): Boolean {
        return canScrollVertically(1) || canScrollVertically(-1)
    }

    fun RecyclerView.setStackFromEnd() {
        (layoutManager as? LinearLayoutManager)?.stackFromEnd = true
    }

    private val onLayoutChangeListener = View.OnLayoutChangeListener {
            _, _, _, _, bottom, _, _, _, oldBottom ->
        if (bottom < oldBottom) {
            binding.activityChat.rvChatArea.scrollBy(0, oldBottom - bottom)
        } // 스크롤 유지를 위해 추가
    }


}