package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.dto.SearchChatRoomDTO
import com.capstone.momomeal.databinding.FragmentSearchResultBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::inflate) {

    private val TAG = "SearchResultFragment"
    private val momomeal = MomomealService.momomealAPI
    val chatlist = arrayListOf<Chatroom>()
    val chatAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        var SearchKeyword = arguments?.getString("SearchKeyword")
        getSearchedChatRoom(SearchKeyword!!)

        binding.fragmentSearchResultRecycle.adapter = chatAdapter
        chatAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = chatAdapter.getData(position)
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("id", item.idChatroom)
                startActivity(intent)
            }
        })
        binding.fragmentSearchResultBack.setOnClickListener{
            (requireActivity() as MainActivity).comebackHome()
        }
        binding.fragmentSearchResultSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    getSearchedChatRoom(query!!)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return retView
    }
    private fun getSearchedChatRoom(s: String){
        momomeal.getSearchChatroom(s).enqueue(object: Callback<List<SearchChatRoomDTO>>{
            override fun onResponse(
                call: Call<List<SearchChatRoomDTO>>,
                response: Response<List<SearchChatRoomDTO>>
            ) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
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
