package com.capstone.momomeal

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.Rate
import com.capstone.momomeal.data.checkResponse
import com.capstone.momomeal.data.dto.CreateReviewForm
import com.capstone.momomeal.databinding.FragmentReviewBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReviewFragment : BaseDialogFragment<FragmentReviewBinding>(FragmentReviewBinding::inflate) {
    private val TAG = "CreateReviewFragment"

    private var user_id: Int = -1
    private var name: String = ""
    private var rate = Rate.LIKE
    private val momomeal = MomomealService.momomealAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        val goodBtn = binding.fragmentReviewGood
        val badBtn = binding.fragmentReviewBad

        user_id = requireArguments().getInt("user_id")
        name = requireArguments().getString("name").toString()
        binding.fragmentReviewDescription.text = name + "님의 매너는 어땠나요?"
        binding.fragmentReviewGood.setOnClickListener{
            if(goodBtn.isChecked){
                badBtn.isChecked = false
                colorOrageGray(goodBtn, badBtn)
                rate = Rate.LIKE
            }
        }
        binding.fragmentReviewBad.setOnClickListener{
            if(badBtn.isChecked){
                goodBtn.isChecked = false
                colorOrageGray(badBtn, goodBtn)
                rate = Rate.BAD
            }
        }
        binding.fragmentReviewSubmit.setOnClickListener{
            val reviewStr = binding.fragmentReviewReviewText.text.toString()
            if(reviewStr.isEmpty()){
                showMSG("텍스트를 입력해주세요")
            }else {
                momomeal.addReview(CreateReviewForm(user_id, reviewStr, rate))
                    .enqueue(object : Callback<checkResponse> {
                        override fun onResponse(
                            call: Call<checkResponse>,
                            response: Response<checkResponse>
                        ) {
                            if (response.isSuccessful.not()) {
                                return
                            }
                            response.body()?.let {
                                if (it.check == 0) {
                                    showMSG("서버 전송실패")
                                }
                            }
                            dismiss()
                        }

                        override fun onFailure(call: Call<checkResponse>, t: Throwable) {
                            Log.e("retrofit", t.toString())
                            showMSG("인터넷을 확인해주세요")
                        }
                    })
            }
        }
        //Log.d("crreview",rate.toString())
        binding.fragmetReviewBack.setOnClickListener{
            dismiss()
        }
        return retView
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val profile = requireActivity().supportFragmentManager
            .findFragmentById(R.id.activity_chat) as ProfileFragment
        profile.updatePage(user_id)
    }

    override fun onResume() {
        super.onResume()
        //풀스크린보다 작게 크기를 설정한다.
        val dialogWidth = resources.displayMetrics.widthPixels * 0.9
        val dialogHeight = resources.displayMetrics.heightPixels * 0.9
        dialog?.window?.setLayout(dialogWidth.toInt(), dialogHeight.toInt())
        //초기화 진행
        binding.fragmentReviewGood.isChecked = true
        binding.fragmentReviewBad.isChecked = false
        colorOrageGray(binding.fragmentReviewGood, binding.fragmentReviewBad)
        binding.fragmentReviewReviewText.text.clear()
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    fun colorOrageGray(v1:View, v2:View){
        //v1 - 오렌지, v2 - 회색 으로 색깔 변경
        val orangeBg = DrawableCompat.wrap(v1.background)
        DrawableCompat.setTint(orangeBg.mutate(), ContextCompat.getColor(requireActivity(), R.color.orange_light))
        v1.background = orangeBg

        val grayBg =  DrawableCompat.wrap(v2.background)
        DrawableCompat.setTint(grayBg.mutate(), ContextCompat.getColor(requireActivity(), R.color.gray4))
        v2.background = grayBg
    }
    private fun showMSG(s: String){
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }
}