package com.capstone.momomeal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.momomeal.data.User
import com.capstone.momomeal.databinding.FragmentMypageBinding
import com.capstone.momomeal.feature.BaseFragment


class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {
    private val TAG = "MypageFragment"
    lateinit var pageInfo : User
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val photoUri: Uri? = result.data!!.data
                    if(photoUri != null){
                        val imageUri : Uri = photoUri!!
                        //imgae decoder.createBitmap.. In api > 30
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                        //비트맵을 받아서 크기를 줄이고, 용량을 줄인다.
                        //Bitmap.createScaledBitmap(img, 256, 256, true)
                        //resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
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

        // 프로필 이미지 처리. 앵간하면 메인액티비티가 처리해도 될거같음.
//        val decodeByte = Base64.decode(mainactivity.myInfo.profileImgUrl, Base64.DEFAULT)
//        val bitmapImg = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.size)

//        binding.ivProfile.setImageBitmap(bitmapImg)
//        binding.etUserName.setText(mainactivity.myInfo.name)
//        binding.pbManner.progress = mainactivity.myInfo.totalRate

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
        binding.etUserName.setText(pageInfo.name)
        binding.etUserName.isEnabled = false
        binding.etUserName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
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
        return retView
    }
    fun chageBitmap(bitmap: Bitmap){
        binding.ivProfile.setImageBitmap(bitmap)
    }
    fun setName(s : String){

    }
    fun updateUser(){

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
            updateUser()
        }
    }
}
