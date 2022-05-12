package com.capstone.momomeal.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R
import com.capstone.momomeal.databinding.ViewChatRoomBinding
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import kotlin.collections.ArrayList


class ChatroomAdapter(
  val context: Context
) : RecyclerView.Adapter<chatViewHolder>()  {

    private var dataSet = ArrayList<Chatroom>()
    private lateinit var itemClickListener : OnItemClickListener

    fun replaceData( chatList: ArrayList<Chatroom>){
        dataSet = chatList
        notifyDataSetChanged()
    }

    fun getData(position: Int): Chatroom {
        return dataSet[position]
    }

    fun removeData(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(position: Int, chatroom: Chatroom) {
        dataSet[position] = chatroom
        notifyItemChanged(position)
    }

    fun swapData(fromPos: Int, toPos: Int) {
        val temp = dataSet[fromPos]
        dataSet[fromPos] = dataSet[toPos]
        dataSet[toPos] = temp
        notifyItemMoved(fromPos, toPos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
        val binding = ViewChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return chatViewHolder(binding)
    }
    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    //클릭 이벤트 인터페이스 만들기
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
    
}
class chatViewHolder(binding:  ViewChatRoomBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title: TextView = binding.viewChatRoomTitle
    private val description: TextView = binding.viewChatRoomDescription
    private val category_img: ImageView = binding.viewChatRoomImg

    fun bind(item: Chatroom) {
        title.text = item.nameRoom
        description.text = "수령지: " +  item.namePickupPlace //+ " * " + item.time...(not impl)
        when(item.category){
            Category.Chicken -> category_img.setImageResource(R.drawable.ic_category_chicken)
            Category.Pizza -> category_img.setImageResource(R.drawable.ic_category_pizza)
            Category.Korean -> category_img.setImageResource(R.drawable.ic_category_korean)
            Category.Chinese -> category_img.setImageResource(R.drawable.ic_category_chinese)
            Category.Japanese -> category_img.setImageResource(R.drawable.ic_category_japanese)
            Category.Western -> category_img.setImageResource(R.drawable.ic_category_western)
            Category.Snackbar -> category_img.setImageResource(R.drawable.ic_category_snackbar)
            Category.MidnightSnack -> category_img.setImageResource(R.drawable.ic_category_midnightsnack)
            Category.BoiledPork -> category_img.setImageResource(R.drawable.ic_category_boiledpork)
            Category.CafeAndDesert -> category_img.setImageResource(R.drawable.ic_category_cafe_desert)
            Category.Fastfood -> category_img.setImageResource(R.drawable.ic_category_fastfood)
        }
    }
}
