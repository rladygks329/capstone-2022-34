package com.capstone.momomeal.feature.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.capstone.momomeal.R
import com.capstone.momomeal.databinding.ItemMyMsgBinding
import com.capstone.momomeal.databinding.ItemOtherMsgBinding
import com.capstone.momomeal.databinding.ItemOtherMsgFullBinding
import com.capstone.momomeal.data.Chat
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.data.membInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.lang.IllegalArgumentException

class ChatAdapter(
//    val context: Context,
    val myInfo: User_light,
    val chatroomId: Long
) : RecyclerView.Adapter<ChatViewHolder>(){
    private val msgList = ArrayList<Chat>()
    private var membMap =  HashMap<Int, membInfo>()

    fun setMemMap(newMemMap: HashMap<Int, membInfo>){
        membMap = newMemMap
        notifyDataSetChanged()
    }
    private val fireDB = FirebaseDatabase.getInstance().reference
        .child("chatroom").child(chatroomId.toString())
    init {
        getMsgList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return when (viewType) {
            OTHER_MSG_FULL -> ChatViewHolder.OtherMsgFullViewHolder(
                ItemOtherMsgFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            OTHER_MSG -> ChatViewHolder.OtherMsgViewHolder(
                ItemOtherMsgBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MY_MSG -> ChatViewHolder.MyMsgViewHolder(
                ItemMyMsgBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount(): Int = msgList.size

    // 채팅의 경우 RecyclerView에 들어가는 item의 타입이 3개이므로, 타입의 구별이 필요합니다.
    // 그 구별을 위한 함수입니다.
    override fun getItemViewType(position: Int): Int {
        if (msgList[position].uid == myInfo.idUser) {
            return MY_MSG
//        } else if (position == 0) {
//            return OTHER_MSG_FULL
        } else if (position == 0 || msgList[position - 1].uid != msgList[position].uid){
            return OTHER_MSG_FULL
        } else {
            return OTHER_MSG
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolder.OtherMsgFullViewHolder
            -> holder.bind(msgList[position], membMap.get(msgList[position].uid))
            is ChatViewHolder.OtherMsgViewHolder -> holder.bind(msgList[position])
            is ChatViewHolder.MyMsgViewHolder -> holder.bind(msgList[position])
        }
    }
    // 여기서부터는 전용 함수들
    // membMap 갱신용 함수
    fun updateMembers(map: HashMap<Int, membInfo>) {
        membMap = map
        notifyDataSetChanged()
    }
    fun addMember(id: Int, info: membInfo) {
        membMap.set(id, info)
        notifyDataSetChanged()
    }
    fun removeMembers(id: Int) = membMap.remove(id)

    // msgList 갱신용 함수
    fun addChat(chat: Chat) {
        msgList.add(chat)
//        notifyItemInserted(msgList.size)
    }

    // 이전까지의 채팅 기록을 뒤져서 가져오는
    fun getMsgList() {
        fireDB.child("chats").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (msg in snapshot.children) {
                    val tmp = msg.getValue<Chat>()
                    msgList.add(tmp!!)
                }
            }
        })
        notifyDataSetChanged()
    }

    //  메시지 타입 구별용 객체
    companion object {
        private const val OTHER_MSG_FULL = 0
        private const val OTHER_MSG = 1
        private const val MY_MSG = 2
    }
}

sealed class ChatViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class OtherMsgFullViewHolder(
        private val binding: ItemOtherMsgFullBinding
    ) : ChatViewHolder(binding) {
        fun bind(item: Chat, info: membInfo?) {
            //Log.d("OMF : ", info.toString())
            if (info != null) {
                binding.ivOtherProfile.setImageBitmap(info.bitmap)
                binding.tvOtherName.text = info.name
            } else {
                binding.ivOtherProfile.setImageResource(R.drawable.ic_basic_profile)
                binding.tvOtherName.text = "익명"
            }
            binding.tvOtherMsgFull.text = item.chatContent

        }
    }

    class OtherMsgViewHolder(
        val binding: ItemOtherMsgBinding
    ) : ChatViewHolder(binding) {
        fun bind(item: Chat) {
            binding.tvOtherMsg.text = item.chatContent
        }
    }

    class MyMsgViewHolder(
        val binding: ItemMyMsgBinding
    ): ChatViewHolder(binding) {
        fun bind(item: Chat) {
            binding.tvMyMsg.text = item.chatContent
        }
    }
}
