package com.capstone.momomeal.feature.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R
import com.capstone.momomeal.feature.Category
import com.capstone.momomeal.feature.Chatroom

class ChatroomAdapter
    (val context: Context, val chatList: ArrayList<Chatroom>, val deletable :Int = View.GONE
) : RecyclerView.Adapter<ChatroomAdapter.ViewHolder>()  {

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
        private val check: CheckBox = view.findViewById(R.id.view_chat_room_check)

        fun bind(item: Chatroom) {
            title.text = item.nameRoom
            description.text = "수령지: " +  item.namePickupPlace //+ " * " + item.time...(not impl)
            check.visibility = deletable
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
