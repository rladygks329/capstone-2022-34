package com.capstone.momomeal.feature

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R

class ChatroomAdapter(val context: Context, val chatList: ArrayList<Chatroom>) : RecyclerView.Adapter<ChatroomAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_chat_room,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = chatList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false);
        holder.bind(chatList[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val title: TextView = view.findViewById(R.id.view_chat_room_title)
        private val description: TextView = view.findViewById(R.id.view_chat_room_description)
        private val category_img: ImageView = view.findViewById(R.id.view_chat_room_img)

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
}
