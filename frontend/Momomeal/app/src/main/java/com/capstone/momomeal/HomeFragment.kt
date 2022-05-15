package com.capstone.momomeal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.databinding.FragmentHomeBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val TAG = "HomeFragment"
    private lateinit var mainActivity: MainActivity
    private val momomeal = MomomealService.momomealAPI
    private val chatInfoFrag = ChatInfoFragment()
    private val createChatFragment = CreateChatFragment()
    private val scFrag = SearchResultFragment()
    private val scCategoryFrag = SearchResultCategoryFragment()
    private val researchFragment = ResearchFragment()
    private var hasPoint = true
    private var hasRearch = false
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { Address ->
            if (Address.resultCode == Activity.RESULT_OK) {
                hasPoint = true
                Address.data?.let {
                    binding.fragmentHomeEditAddress.text = it.getStringExtra("data")
                }
            }
        }
    val chatroomList = arrayListOf<Chatroom>(
    )
    val chatAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }

    // onCreate 이후 화면을 구성하는 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("system","home Oncreate is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("system","home OncreateView is called")
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        mainActivity = (activity as MainActivity)

        binding.fragmentHomeEditAddress.setOnClickListener{
            startForResult.launch(Intent(requireActivity().application, MyAddressActivity::class.java))
        }
        binding.fabHome.setOnClickListener {
            createChatFragment.show(mainActivity.supportFragmentManager, createChatFragment.tag)
        }
        binding.fragmentHomeRecycler.adapter = chatAdapter
        chatAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                if(hasPoint){
                    val item = chatAdapter.getData(position)
                    chatInfoFrag.arguments = bundleOf(
                        "User" to mainActivity.myInfo,
                        "Chatroom" to item
                    )
                    chatInfoFrag.show(mainActivity.supportFragmentManager, chatInfoFrag.tag)
                }else{
                    Toast.makeText(requireContext(), "주소를 설정해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        })
        chatAdapter.replaceData(chatroomList)

        initSearch(binding.fragmentHomeSearchbar)
        initTable(binding.fragmentHomeCategoryTable)
        updateRecommendation()
        if(!hasRearch){
            researchFragment.show(mainActivity.supportFragmentManager, researchFragment.tag)
        }
        return retView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initSearch(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(hasPoint && query != null){
                    val bundle = bundleOf("SearchKeyword" to query)
                    scFrag.arguments = bundle
                    mainActivity.moveSearch(scFrag)
                }else{
                    Toast.makeText(requireContext(), "주소를 설정해주세요", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initTable(table: TableLayout){
        val row = table.childCount-1
        for(i in 0..row){
            val column: TableRow = table.getChildAt(i) as TableRow
            for(j in 0 until column.childCount){
                val tv : TextView = column.getChildAt(j) as TextView
                tv.setOnClickListener{
                    if(hasPoint){
                        val bundle = bundleOf("category" to tv.text.toString())
                        bundle.putString("EngCategory", it.tag.toString())
                        scCategoryFrag.arguments = bundle
                        mainActivity.moveSearch(scCategoryFrag)
                    }else{
                        Toast.makeText(requireContext(), "주소를 설정해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateRecommendation(){
        momomeal.getRecommendedChatroom(mainActivity.myInfo.idUser)
            .enqueue(object: Callback<List<Chatroom>>{
                override fun onResponse(
                    call: Call<List<Chatroom>>,
                    response: Response<List<Chatroom>>
                ) {
                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let{
                        chatAdapter.replaceData(ArrayList<Chatroom>(it))
                    }
                }

                override fun onFailure(call: Call<List<Chatroom>>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                }
            })
    }

    //프레그먼트의 hide show가 호출될 때, 불리는 함수
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            updateRecommendation()
        }
    }


    fun onclicker(s : String) {
        Log.d(TAG, s)
    }
}
