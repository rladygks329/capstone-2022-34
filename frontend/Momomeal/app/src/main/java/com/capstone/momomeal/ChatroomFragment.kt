package com.capstone.momomeal

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.dto.MyChatRoomDTO
import com.capstone.momomeal.databinding.FragmentChatroomBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.feature.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatroomFragment : BaseFragment<FragmentChatroomBinding>(FragmentChatroomBinding::inflate) {
    private val TAG = "ChatroomFragment"

    val chatAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }
    val chatroomList = arrayListOf<Chatroom>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "OnCreateView Popout!!!!")
        val retview = super.onCreateView(inflater, container, savedInstanceState)

        chatAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = chatAdapter.getData(position)
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("id", item.idChatroom)
                startActivity(intent)
            }
        })
        with(binding){
            fragmentChatroomToolbar.inflateMenu(R.menu.menu_chat_room)
            fragmentChatroomRecycle.adapter = chatAdapter
        }
        updateMyChatRoom()
        chatAdapter.replaceData(chatroomList)

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos: Int = viewHolder.adapterPosition
                val toPos: Int = target.adapterPosition
                chatAdapter.swapData(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                chatAdapter.removeData(viewHolder.layoutPosition)
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
        val momomeal = MomomealService.momomealAPI
        val mainActivity = requireActivity() as MainActivity

        momomeal.getEnteredChatroom(mainActivity.user.idUser).enqueue(object: Callback<List<MyChatRoomDTO>>{
            override fun onResponse(call: Call<List<MyChatRoomDTO>>, response: Response<List<MyChatRoomDTO>>) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    chatroomList.clear()
                    //body가 있다면 그안에는 bestSellerDto가 들어있을것
                    it.forEach{ mychat->
                       chatroomList.add(mychat.toChatroom())
                    }
                    chatAdapter.replaceData(chatroomList)
                }
            }
            override fun onFailure(call: Call<List<MyChatRoomDTO>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}