package com.capstone.momomeal

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.databinding.FragmentResearchBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResearchFragment : BaseDialogFragment<FragmentResearchBinding>(FragmentResearchBinding::inflate) {
    private val TAG = "ResearchFragment"
    private var count = 0;
    private val momomeal = MomomealService.momomealAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        initTable(binding.fragmentResearchTable)
        binding.fragmentResearchSubmit.setOnClickListener{
            submit()
        }
        return retView
    }

    private fun initTable(table: TableLayout){
        for(i in 0 until table.childCount){
            val column: TableRow = table.getChildAt(i) as TableRow
            for(j in 0 until column.childCount){
                val toggleBtn : ToggleButton = column.getChildAt(j) as ToggleButton
                toggleBtn.setOnClickListener{
                    if(toggleBtn.isChecked){
                        count++
                    }else{
                        count--
                    }
                }
            }
        }
    }
    private fun getCategory (table:TableLayout): String{
        var result = ""
        for(i in 0 until table.childCount){
            val column: TableRow = table.getChildAt(i) as TableRow
            for(j in 0 until column.childCount){
                val toggleBtn : ToggleButton = column.getChildAt(j) as ToggleButton
                if(toggleBtn.isChecked){
                    result += toggleBtn.tag
                    result += ","
                }
            }
        }

        return result
    }
    private fun submit(){
        //Log.d("research",count.toString())
        if(count < 3 ){
            showMSG("3개 이상의 항목을 선택해주세요")
            return
        }
        if(count > 5){
            showMSG("5개 이하로 선택해주세요")
            return
        }
        val query = getCategory(binding.fragmentResearchTable).dropLast(1)
        momomeal.sendResearch((requireActivity()as MainActivity).myInfo.idUser, query)
            .enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    dismiss()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    showMSG("인터넷을 확인해보세요")
                }
            })
    }
    private fun showMSG(s: String){
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

}
