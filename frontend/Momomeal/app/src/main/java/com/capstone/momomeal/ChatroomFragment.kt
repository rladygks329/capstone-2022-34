package com.capstone.momomeal

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.ChatStatus
import com.capstone.momomeal.databinding.FragmentChatroomBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.feature.adapter.ChatRoomViewHolder
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatroomFragment : BaseFragment<FragmentChatroomBinding>(FragmentChatroomBinding::inflate) {
    private val TAG = "ChatroomFragment"

    val momomeal = MomomealService.momomealAPI
    val chatroomAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }

    private val fireDatabase = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retview = super.onCreateView(inflater, container, savedInstanceState)
        val mainactivity = requireActivity() as MainActivity
        chatroomAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(activity, ChatActivity::class.java)
                val chatroomInfo = chatroomAdapter.getData(position)

                val userArea = fireDatabase.child("chatroom")
                    .child(chatroomInfo.idChatroom.toString())
                    .child("users")
                userArea.get().addOnSuccessListener {
                    val a = it
                    if (it.hasChildren()) {
                    }
                }


                val myInfoLight = User_light((activity as MainActivity).myInfo)
                intent.putExtra("chatroominfo", chatroomInfo) // Chatroom information
                intent.putExtra("myinfo", myInfoLight)
                intent.putExtra("chatstatus", ChatStatus.ENTER)
                startActivity(intent)
            }
        })
        binding.fragmentChatroomToolbar.inflateMenu(R.menu.menu_chat_room)
        binding.fragmentChatroomRecycle.adapter = chatroomAdapter
        updateMyChatRoom()

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val mainactivity = requireActivity() as MainActivity
                //Log.d("onswipe",viewHolder.adapterPosition.toString())

                val deleted_chat_room = chatroomAdapter.getData(viewHolder.layoutPosition)
                chatroomAdapter.removeData(viewHolder.layoutPosition)

                // 서버에서 내 정보를 삭제합니다.
                momomeal.deleteChatroom(
                    mainactivity.myInfo.idUser, deleted_chat_room.idChatroom
                ).enqueue( object: Callback<HashMap<String, Int>>{
                    override fun onResponse(
                        call: Call<HashMap<String, Int>>,
                        response: Response<HashMap<String, Int>>
                    ) {
                        if(response.isSuccessful.not()){
                            return
                        }
                    }
                    override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                        Toast.makeText(requireContext(), "서버 오류", Toast.LENGTH_SHORT).show()
                    }
                })

                // Firebase 내의 채팅방에서 내 정보를 지웁니다.

                val userArea = fireDatabase.child("chatroom")
                    .child(deleted_chat_room.idChatroom.toString())
                    .child("users")
                userArea.child(mainactivity.myInfo.idUser.toString())
                    .removeValue().addOnSuccessListener {
                        userArea.get().addOnSuccessListener {
                            if (!it.hasChildren()) {
                                // 만약 혼자 남은 방에서 나가는 거라면, Firebase 내부에서 채팅방을 완전히 삭제합니다.
                                fireDatabase.child("chatroom")
                                    .child(deleted_chat_room.idChatroom.toString()).removeValue()
                            }
                        }
                }
            }
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                // actionState가 SWIPE 동작일 때 배경을 빨간색으로 칠하는 작업을 수행하도록 함
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = (itemView.bottom - itemView.top).toFloat()
                    val width = height / 4
                    val paint = Paint()
                    if (dX < 0) {  // 왼쪽으로 스와이프하는지 확인
                        // ViewHolder의 백그라운드에 깔아줄 사각형의 크기와 색상을 지정
                        paint.color = Color.parseColor("#ff0000")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, paint)

                        // 휴지통 아이콘과 표시될 위치를 지정하고 비트맵을 그려줌
                        // 비트맵 이미지는 Image Asset 기능으로 추가하고 drawable 폴더에 위치하도록 함
                        icon = BitmapFactory.decodeResource(activity?.applicationContext?.resources, R.drawable.ic_menu_delete)
                        val iconDst = RectF(itemView.right.toFloat() - 3 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, iconDst, null)

                    }
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.fragmentChatroomRecycle)

        return retview
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            updateMyChatRoom()
        }
    }

    fun updateMyChatRoom(){
        val mainActivity = requireActivity() as MainActivity

        momomeal.getEnteredChatroom(mainActivity.myInfo.idUser).enqueue(object: Callback<List<Chatroom>>{
            override fun onResponse(call: Call<List<Chatroom>>, response: Response<List<Chatroom>>) {
                //Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    chatroomAdapter.replaceData(ArrayList<Chatroom>(it))
                }
            }
            override fun onFailure(call: Call<List<Chatroom>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}
