package com.capstone.momomeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.feature.Category
import com.capstone.momomeal.feature.Chatroom
import com.capstone.momomeal.feature.ChatroomAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recycle = view.findViewById<RecyclerView>(R.id.fragment_home_recycler)
        val chatroomadapter = ChatroomAdapter(requireContext(), chatroomList)
        recycle.adapter = chatroomadapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}