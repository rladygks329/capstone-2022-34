package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.dto.SearchChatRoomDTO
import com.capstone.momomeal.databinding.FragmentSearchResultCategoryBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultCategoryFragment : BaseFragment<FragmentSearchResultCategoryBinding>(
    FragmentSearchResultCategoryBinding::inflate) {

    private val TAG = "SearchResultCategoryFragment"
    val momomeal = MomomealService.momomealAPI
    val chatlist = arrayListOf<Chatroom>()
    var selectedCategory = ""
    val chatAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }
    val mainActivity: MainActivity by lazy {
       requireActivity() as MainActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)

        selectedCategory = arguments?.getString("EngCategory")!!

        binding.fragmentSearchResultCategoryTitle.text = arguments?.getString("category")
        binding.fragmentSearchResultCategoryRecycle.adapter = chatAdapter
        binding.fragmentSearchResultCategoryBack.setOnClickListener{ mainActivity.comebackHome() }
        binding.fragmentSearchResultCategoryTimechip.setOnClickListener { getCategoryChatRoom("time") }
        binding.fragmentSearchResultCategoryDistancechip.setOnClickListener { getCategoryChatRoom("distance") }

        chatAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = chatAdapter.getData(position)
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("id", item.idChatroom)
                startActivity(intent)
            }
        })
        //초기 값을 가져온다
        getCategoryChatRoom("time")
        return retView
    }
    private fun getCategoryChatRoom( s: String){
        momomeal.getCategoryChatroom( selectedCategory, mainActivity.user.idUser, s)
            .enqueue( object : Callback<List<SearchChatRoomDTO>> {
            override fun onResponse(
                call: Call<List<SearchChatRoomDTO>>,
                response: Response<List<SearchChatRoomDTO>>
            ) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){ return }
                response.body()?.let{
                    chatlist.clear()
                    it.forEach{ SearchChatRoom-> chatlist.add(SearchChatRoom.toChatroom()) }
                    chatAdapter.replaceData(chatlist)
                }
            }

            override fun onFailure(call: Call<List<SearchChatRoomDTO>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}
