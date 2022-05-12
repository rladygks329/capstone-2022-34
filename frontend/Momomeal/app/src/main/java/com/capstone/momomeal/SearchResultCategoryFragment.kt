package com.capstone.momomeal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.databinding.FragmentSearchResultCategoryBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultCategoryFragment : BaseFragment<FragmentSearchResultCategoryBinding>(
    FragmentSearchResultCategoryBinding::inflate) {

    private val TAG = "SearchResultCategoryFragment"

    val momomeal = MomomealService.momomealAPI
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
                val chatInfoFrag = ChatInfoFragment()
                val item = chatAdapter.getData(position)
                chatInfoFrag.arguments = bundleOf(
                    "User" to mainActivity.myInfo!!,
                    "Chatroom" to item
                )
                chatInfoFrag.show(mainActivity.supportFragmentManager, chatInfoFrag.tag)
            }
        })
        //초기 값을 가져온다
        getCategoryChatRoom("time")
        return retView
    }
    private fun getCategoryChatRoom( s: String){
        momomeal.getCategoryChatroom( selectedCategory, mainActivity.myInfo.idUser, s)
            .enqueue( object : Callback<List<Chatroom>> {
            override fun onResponse(
                call: Call<List<Chatroom>>,
                response: Response<List<Chatroom>>
            ) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){ return }
                response.body()?.let{
                    chatAdapter.replaceData(ArrayList<Chatroom>(it))
                }
            }

            override fun onFailure(call: Call<List<Chatroom>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}
