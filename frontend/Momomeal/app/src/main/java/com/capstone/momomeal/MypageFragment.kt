package com.capstone.momomeal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.*
import com.capstone.momomeal.data.dto.UpdateUserInfoForm
import com.capstone.momomeal.data.dto.getUserInfoForm
import com.capstone.momomeal.data.dto.getUserResponse
import com.capstone.momomeal.databinding.FragmentMypageBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import com.capstone.momomeal.feature.adapter.ReviewAdapter
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {
    private val TAG = "MypageFragment"
    private val momomeal = MomomealService.momomealAPI
    val reviewAdapter: ReviewAdapter by lazy {
        ReviewAdapter(
            ArrayList<Review>()
        )
    }
    lateinit var pageInfo : User
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val photoUri: Uri? = result.data!!.data
                    if(photoUri != null){
                        val imageUri : Uri = photoUri
                        //imgae decoder.createBitmap.. In api > 30
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                        chageBitmap(bitmap)
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageInfo = (requireActivity() as MainActivity).myInfo

        Log.d(TAG, "cReate!!")

        val mainactivity = requireActivity() as MainActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val retView = super.onCreateView(inflater, container, savedInstanceState)

        binding.ivProfile.setOnClickListener{
            val OpenGalleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startForResult.launch(OpenGalleryIntent)
        }
        binding.framgentMypageReviewRecycler.adapter = reviewAdapter
        binding.etUserName.isEnabled = false
        binding.etUserName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    setName( binding.etUserName.text.toString())
                    binding.etUserName.isEnabled = false
                    v?.hideKeyboard()
                    return true
                }
                return false
            }
        })
        binding.tilUserName.setEndIconOnClickListener{
            val textInputEditText = binding.etUserName
            textInputEditText.isEnabled = true
            textInputEditText.setSelection(textInputEditText.length())
            textInputEditText.showSoftKeyboard()
        }
        updatePage(pageInfo.idUser)
        return retView
    }
    fun chageBitmap(bitmap: Bitmap){
        val newImage = encodeImage(bitmap)
        Log.d("mypage", newImage)
        momomeal.updateUserInfo(UpdateUserInfoForm(pageInfo.idUser, pageInfo.email, pageInfo.name, newImage))
            .enqueue(object: Callback<checkResponse>{
                override fun onResponse(
                    call: Call<checkResponse>,
                    response: Response<checkResponse>
                ) {
                    Log.d("retrofit",response.body().toString())
                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let{
                        if(it.check == 1){ pageInfo.profileImgUrl = newImage }
                        binding.ivProfile.setImageBitmap(bitmap)
                    }
                }

                override fun onFailure(call: Call<checkResponse>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                    showMSG("인터넷을 확인해주세요")
                }
            })
    }
    fun setName(newName : String){
        momomeal.updateUserInfo(UpdateUserInfoForm(pageInfo.idUser, pageInfo.email, newName, pageInfo.profileImgUrl))
            .enqueue(object: Callback<checkResponse>{
                override fun onResponse(
                    call: Call<checkResponse>,
                    response: Response<checkResponse>
                ) {
                    Log.d("retrofit",response.body().toString())
                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let{
                        if(it.check == 1){ pageInfo.name = newName }
                        binding.etUserName.setText(pageInfo.name)
                        binding.etUserName.isEnabled = false
                    }
                }

                override fun onFailure(call: Call<checkResponse>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                    showMSG("인터넷을 확인해주세요")
                }
            })

    }
    fun updatePage(id: Int){
        momomeal.getUserInfo(getUserInfoForm(id)).enqueue(object:Callback<getUserResponse>{
            override fun onResponse(
                call: Call<getUserResponse>,
                response: Response<getUserResponse>
            ) {
                if(response.isSuccessful.not()){
                    return
                }
                Log.d("retrofit", response?.body().toString())
                response.body()?.let{
                    binding.etUserName.setText(it.name)
                    if(it.img != ""){
                        binding.ivProfile.setImageBitmap(decodeImage(pageInfo.profileImgUrl))
                    }
                    binding.pbManner.progress = it.rate
                    binding.tvMannerPoint.text = it.rate.toString() + "점"
                    reviewAdapter.replaceData(it.reviewList)
                }
            }

            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.showSoftKeyboard() {
        if (this.requestFocus()) {
            Log.d("show","성공")
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            updatePage(pageInfo.idUser)
        }
    }
    fun showMSG(s: String){
        Toast.makeText(requireActivity().applicationContext, s, Toast.LENGTH_SHORT).show()
    }
    private fun encodeImage(bitmap: Bitmap): String {
        //크기를 줄인다 256 x 256 사이즈로
        val resized = Bitmap.createScaledBitmap(bitmap, 256, 256, true)
        val outStream = ByteArrayOutputStream()
        //크기는 그대로지만 퀄리티를 결정, 1-100, 100이 아닌경우 -> 디지털 풍화가 발생가능
        //out stream에 넣는 용도로 사용 중
        resized.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        val image = outStream.toByteArray()
        return Base64.encodeToString(image, 0)
    }
    private fun decodeImage(Base64_string: String): Bitmap {
        val bytePlainOrg = Base64.decode(Base64_string, 0)
        val inStream = ByteArrayInputStream(bytePlainOrg)
        return BitmapFactory.decodeStream(inStream)
    }
}
