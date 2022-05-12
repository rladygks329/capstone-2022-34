package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User
import com.capstone.momomeal.databinding.FragmentChatInfoBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatInfoFragment : BaseDialogFragment<FragmentChatInfoBinding>(FragmentChatInfoBinding::inflate) {

    private val TAG = "ChatInfoFragment"
    val momomeal = MomomealService.momomealAPI
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
            enterChat()
        }

        return retView
    }
    private fun enterChat(){
        momomeal.enterChatroom(user.idUser, chatroom.idChatroom)
            .enqueue(object:Callback<HashMap<String, Int>>{
            override fun onResponse(
                call: Call<HashMap<String, Int>>,
                response: Response<HashMap<String, Int>>
            ) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    val intent = Intent(activity, ChatActivity::class.java)
                    intent.putExtra("myinfo", user.trans_User_light())
                    intent.putExtra("chatroominfo", chatroom)
                    startActivity(intent)
                    dismiss()
                }
            }

            override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}
