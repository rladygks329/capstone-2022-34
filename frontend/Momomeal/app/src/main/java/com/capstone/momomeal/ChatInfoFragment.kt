package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User
import com.capstone.momomeal.databinding.FragmentChatInfoBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import com.capstone.momomeal.feature.adapter.ChatroomAdapter

class ChatInfoFragment : BaseDialogFragment<FragmentChatInfoBinding>(FragmentChatInfoBinding::inflate) {

    private val TAG = "ChatInfoFragment"
    val chatAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }
    val user: User by lazy{
        arguments?.getParcelable<User>("User")!!
    }
    val chatroom: Chatroom by lazy{
        arguments?.getParcelable<Chatroom>("Chatroom")!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        binding.framgentChatInfoExit.setOnClickListener{
            dismiss()
        }
        binding.framgentChatInfoTitle.text = chatroom.nameRoom
        binding.framgentChatInfoCategory.text = chatroom.category?.KoreanName
        binding.framgentChatInfoMax.text = chatroom.maxCapacity.toString()
        binding.framgentChatInfoStore.text = chatroom.nameStore
        binding.framgentChatInfoPickup.text = chatroom.namePickupPlace
        binding.fragmentChatInfoEnter.setOnClickListener{
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra("myinfo", user.trans_User_light())
            intent.putExtra("chatroominfo", chatroom)
            startActivity(intent)
            dismiss()
        }

        return retView
    }
}
