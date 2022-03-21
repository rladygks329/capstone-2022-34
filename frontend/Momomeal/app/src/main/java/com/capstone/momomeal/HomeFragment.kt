package com.capstone.momomeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.momomeal.databinding.FragmentHomeBinding
import com.capstone.momomeal.feature.BaseFragment
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.feature.Category
import com.capstone.momomeal.feature.Chatroom
import com.capstone.momomeal.feature.ChatroomAdapter


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private val createChatFragment = CreateChatFragment()
    val chatroomList = arrayListOf<Chatroom>(
        //test
        Chatroom("Bhc 뿌링클 뿌개실분 ~ ", 123, Category.Chicken, 3, "국민대학교 정문", 3.3, listOf(7,49,89)),
        Chatroom("밤 12시에 족발 먹을 사람 있니?", 128, Category.BoiledPork, 3, "서울대입구 4번출구", 3.9, listOf(3,29,69)),
        Chatroom("분식 먹을 돼지만", 128, Category.Snackbar, 3, "먹자골목", 3.9, listOf(3,29,69)),
        Chatroom("스타벅스 새로나온 케이크 먹자", 128, Category.CafeAndDesert, 3, "강남역 4번출구", 3.9, listOf(3,29,69)),
        Chatroom("배고픈데 잠이 오니?", 128, Category.Pizza, 3, "마포대표 근처", 3.9, listOf(3,29,69)),
        Chatroom("먹고 죽자", 128, Category.Korean, 3, "성북구 길음1동 삼부아파트", 3.9, listOf(3,29,69)),
        Chatroom("중국집 시켜먹을 사람 컴", 128, Category.Chinese, 3, "인천 차이나타운", 3.9, listOf(3,29,69))
    )
    
    // onCreate 이후 화면을 구성하는 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        
        val recycle = retView.findViewById<RecyclerView>(R.id.fragment_home_recycler)
        val chatroomadapter = ChatroomAdapter(requireContext(), chatroomList)
        recycle.adapter = chatroomadapter

        mainActivity = (activity as MainActivity)
        val transaction = mainActivity
            .supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main_full_container, createChatFragment)
        binding.fabHome.setOnClickListener {
//            mainActivity.changeFragment(createChatFragment)
            transaction.commit()
        }
        return retView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

