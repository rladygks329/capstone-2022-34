package com.capstone.momomeal.feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.data.membInfo
import com.capstone.momomeal.databinding.ItemChatMemberBinding

class ChatMemberAdapter() : RecyclerView.Adapter<ChatMemberViewHolder>(){
    var membMap: HashMap<Int, membInfo> = HashMap<Int, membInfo>()
    var membList: ArrayList<User_light> = ArrayList<User_light>()
    private lateinit var itemClickListener : ChatMemberAdapter.OnItemClickListener

    fun setData(newMap :HashMap<Int, membInfo>, newList: ArrayList<User_light> ){
        membMap = newMap
        membList = newList
        notifyDataSetChanged()
    }

    fun getUserID(position: Int): Int {
        return membList[position].idUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMemberViewHolder {
        val binding = ItemChatMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChatMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMemberViewHolder, position: Int) {
        holder.bind(membList[position], membMap.get(membList[position].idUser))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = membList.size

    //클릭 이벤트 인터페이스 만들기
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}

class ChatMemberViewHolder(
    private val binding: ItemChatMemberBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User_light, info: membInfo?) {
        if (info != null) {
            binding.ivMemberProfile1.setImageBitmap(info.bitmap)
            binding.tvMemberName1.text = info.name
        } else {
            binding.ivMemberProfile1.setImageResource(R.drawable.ic_basic_profile)
            binding.tvMemberName1.text = "익명"
        }
    }
}