package com.capstone.momomeal

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.Review
import com.capstone.momomeal.data.dto.getUserInfoForm
import com.capstone.momomeal.data.dto.getUserResponse
import com.capstone.momomeal.databinding.FragmentProfileBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.feature.adapter.ReviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    private var user_id: Int = -1
    val reviewDialogFragment = CreateReviewFragment()
    val momomeal = MomomealService.momomealAPI
    val reviewAdapter: ReviewAdapter by lazy {
        ReviewAdapter(ArrayList<Review>())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retview = super.onCreateView(inflater, container, savedInstanceState)
        user_id = requireArguments().getInt("user_id")
        binding.fragmentProfileReviewRecycler.adapter = reviewAdapter
        binding.btnProfileBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
        binding.btnOtherReview.setOnClickListener {
            reviewDialogFragment.arguments = bundleOf(
                "user_id" to user_id,
                "name" to binding.tvProfileUsername.text.toString()
            )
            reviewDialogFragment.show(
                requireActivity().supportFragmentManager,
                reviewDialogFragment.tag
            )
        }
        updatePage(user_id)
        return retview
    }

    fun updatePage(id: Int){
        momomeal.getUserInfo(getUserInfoForm(id)).enqueue(object: Callback<getUserResponse> {
            override fun onResponse(
                call: Call<getUserResponse>,
                response: Response<getUserResponse>
            ) {
                if(response.isSuccessful.not()){
                    return
                }
                //Log.d("retrofit", response.body().toString())
                response.body()?.let{
                    binding.tvProfileUsername.text = it.name
                    if(it.img != ""){
                        binding.ivProfile.setImageBitmap(decodeImage(it.img))
                    }
                    binding.pbOtherManner.progress = it.rate
                    binding.tvMannerPoint.text = it.rate.toString() + "점"
                    //Log.d("retrofit", it.reviewList.toString())
                    reviewAdapter.replaceData(it.reviewList)
                }
            }
            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
                //Log.e("retrofit", t.toString())
            }
        })
    }
    private fun decodeImage(Base64_string: String): Bitmap {
        val bytePlainOrg = Base64.decode(Base64_string, 0)
        val inStream = ByteArrayInputStream(bytePlainOrg)
        return BitmapFactory.decodeStream(inStream)
    }
}