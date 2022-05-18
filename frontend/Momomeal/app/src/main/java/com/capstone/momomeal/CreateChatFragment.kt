package com.capstone.momomeal

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.dto.CreateChatForm
import com.capstone.momomeal.databinding.FragmentCreateChatBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import com.capstone.momomeal.feature.BaseFragment
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.HashMap

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

class CreateChatFragment : BaseDialogFragment<FragmentCreateChatBinding>(FragmentCreateChatBinding::inflate) {
    private val TAG = "CreateChatFragment"
    private val momomeal = MomomealService.momomealAPI
    private val fireDatabase = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        // Spinner Setting
        setupCategorySpinner()
//        binding.spnChatCategory.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }

        setupMaxCapacitySpinner()
//        binding.spnChatMaxCapacity.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
        binding.btnBack.setOnClickListener { dismiss() }
        binding.btnChatCraeteConfirm.setOnClickListener { submit() }

        return retView
    }

    private fun setupCategorySpinner() {
        val categoryItem = resources.getStringArray(R.array.spn_categoty_array)
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryItem)
        binding.spnChatCategory.setAdapter(categoryAdapter)
    }

    private fun setupMaxCapacitySpinner() {
        val maxCapacityItem = resources.getStringArray(R.array.spn_max_capacity_array)
        val maxCapacityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, maxCapacityItem)
        binding.spnChatMaxCapacity.setAdapter(maxCapacityAdapter)
    }
    private fun submit(){
        val user = (activity as MainActivity).myInfo
        val title = binding.etCreateChatTitle.text.toString()
        val hostID = user.idUser
        val maxCapacity = binding.spnChatMaxCapacity.text.toString().dropLast(1).toInt()
        val storeName = binding.etChatDeliverPlace.text.toString()
        val pickupPlace = binding.etChatLocation.text.toString()
        var kor_category = binding.spnChatCategory.text.toString()
        var category = Category.Chicken
        when(kor_category){
            Category.Chicken.KoreanName -> category = Category.Chicken
            Category.Pizza.KoreanName -> category = Category.Pizza
            Category.Korean.KoreanName -> category = Category.Korean
            Category.Chinese.KoreanName -> category = Category.Chinese
            Category.Japanese.KoreanName -> category = Category.Japanese
            Category.Western.KoreanName -> category = Category.Western
            Category.Snackbar.KoreanName -> category = Category.Snackbar
            Category.MidnightSnack.KoreanName -> category = Category.MidnightSnack
            Category.BoiledPork.KoreanName -> category = Category.BoiledPork
            Category.CafeAndDesert.KoreanName -> category = Category.CafeAndDesert
            Category.Fastfood.KoreanName -> category = Category.Fastfood
        }
        momomeal.makeChatroom(
            CreateChatForm(title, hostID, category, storeName, maxCapacity, pickupPlace, user.x, user.y)
        ).enqueue(object : Callback<Chatroom>{
            override fun onResponse(
                call: Call<Chatroom>,
                response: Response<Chatroom>
            ) {
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    val intent = Intent(activity, ChatActivity::class.java)
                    val isNewChat = true
                    intent.putExtra("myinfo", user.trans_User_light())
                    intent.putExtra("chatroominfo", it)
                    intent.putExtra("isNewChat", isNewChat)
                    startActivity(intent)
                    dismiss()
                }
            }

            override fun onFailure(call: Call<Chatroom>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.etCreateChatTitle.setText("")
        binding.spnChatMaxCapacity.setText("")
        binding.etChatDeliverPlace.setText("")
        binding.etChatLocation.setText("")
        binding.spnChatCategory.setText("")
    }
}