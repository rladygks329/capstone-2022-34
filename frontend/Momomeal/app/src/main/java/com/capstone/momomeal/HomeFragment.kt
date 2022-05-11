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
import com.capstone.momomeal.databinding.FragmentHomeBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import kotlin.math.log


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val TAG = "HomeFragment"
    private lateinit var mainActivity: MainActivity
    private val createChatFragment = CreateChatFragment()
    private val scFrag = SearchResultFragment()
    private val scCategoryFrag = SearchResultCategoryFragment()
    private val researchFragment = ResearchFragment()
    private var hasPoint = true
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
        //test
        Chatroom("Bhc 뿌링클 뿌개실분 ~ ", 123, Category.Chicken, 3, "국민대학교 정문", 3.3, 1.1, listOf(7, 49, 89)),
        Chatroom("밤 12시에 족발 먹을 사람 있니?", 128, Category.BoiledPork, 3, "서울대입구 4번출구", 3.9, 1.1, listOf(3, 29, 69)),
        Chatroom("분식 먹을 돼지만", 128, Category.Snackbar, 3, "먹자골목", 3.9, 1.1, listOf(3, 29, 69)),
        Chatroom("스타벅스 새로나온 케이크 먹자", 128, Category.CafeAndDesert, 3, "강남역 4번출구", 3.9, 1.1, listOf(3, 29, 69)),
        Chatroom("배고픈데 잠이 오니?", 128, Category.Pizza, 3, "마포대표 근처", 3.9, 1.1, listOf(3, 29, 69)),
        Chatroom("먹고 죽자", 128, Category.Korean, 3, "성북구 길음1동 삼부아파트", 3.9, 1.1, listOf(3, 29, 69)),
        Chatroom("중국집 시켜먹을 사람 컴", 128, Category.Chinese, 3, "인천 차이나타운", 3.9, 1.1, listOf(3, 29, 69))
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
                    val intent = Intent(activity, ChatActivity::class.java)
                    intent.putExtra("id", item.idChatroom)
                    startActivity(intent)
                }else{
                    Toast.makeText(requireContext(), "주소를 설정해주세요", Toast.LENGTH_SHORT).show()
                }

            }
        })
        chatAdapter.replaceData(chatroomList)

        initSearch(binding.fragmentHomeSearchbar)
        initTable(binding.fragmentHomeCategoryTable)
        return retView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initSearch(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(hasPoint && query != null){
                    val bundle = bundleOf("SearchKeyword" to query!!)
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

    //프레그먼트의 hide show가 호출될 때, 불리는 함수
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            //update data
        }
    }

    fun onclicker(s : String) {
        Log.d(TAG, s)
    }
}
