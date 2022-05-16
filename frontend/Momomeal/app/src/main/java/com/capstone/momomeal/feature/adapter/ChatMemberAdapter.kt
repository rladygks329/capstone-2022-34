package com.capstone.momomeal.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.data.membInfo
import com.capstone.momomeal.databinding.ItemChatMemberBinding

class ChatMemberAdapter(
    val membMap: HashMap<Int, membInfo>,
    val membList: ArrayList<User_light>
) : RecyclerView.Adapter<ChatMemberViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMemberViewHolder {
        val binding = ItemChatMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChatMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMemberViewHolder, position: Int) {
        holder.bind(membList[position], membMap.get(membList[position].idUser))
    }

    override fun getItemCount(): Int = membList.size
}

class ChatMemberViewHolder(
    private val binding: ItemChatMemberBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User_light, info: membInfo?) {
        if (info != null) {
            binding.ivMemberProfile1.setImageBitmap(info.bitmap)
            binding.tvMemberName1.text = info.name
        } else {
            binding.ivMemberProfile1.setImageResource(R.drawable.ic_basic_prifile)
            binding.tvMemberName1.text = "익명"
        }
    }
}