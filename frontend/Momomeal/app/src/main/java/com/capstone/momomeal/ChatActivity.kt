package com.capstone.momomeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.capstone.momomeal.databinding.ActivityChatBinding
import com.capstone.momomeal.databinding.ActivityMainBinding
import com.capstone.momomeal.databinding.DrawerChatInfoBinding
import com.capstone.momomeal.databinding.LayoutChatHolderBinding

/*****************************
 * File Name : ChatActivity.kt
 * Writer : 장진우
 ******************************/
class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"

    // ChatActivity에는 Navigation Drawer가 달려있음.
    // 이거는 반드시 DrawerLayout이 최상위가 되어야 하므로 Activity_chat을 Bind하지 않음.
    private lateinit var binding: LayoutChatHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutChatHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listeners in Chat Activity
        binding.activityChat.btnMore.setOnClickListener {
            binding.root.openDrawer(GravityCompat.END)
            Toast.makeText(this,"More CLicked",Toast.LENGTH_SHORT).show()
        }
        binding.activityChat.btnChatBack.setOnClickListener {
            onBackPressed()
        }

        // Listeners in drawer
        binding.nvChatNavigation.btnCloseDrawer.setOnClickListener {
            binding.root.closeDrawers()
        }
    }

    override fun onBackPressed() {
        if (binding.root.isDrawerOpen(GravityCompat.END)) {
            binding.root.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }

    fun
}