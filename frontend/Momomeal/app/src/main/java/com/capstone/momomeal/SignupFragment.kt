package com.capstone.momomeal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.LoginResponse
import com.capstone.momomeal.data.dto.RegisterForm
import com.capstone.momomeal.databinding.FragmentSignupBinding
import com.capstone.momomeal.feature.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        val checkAll = binding.fragmentSignupCheckAll
        val checkArray = arrayOf<CheckBox>(
            binding.fragmentSignupCheck1, binding.fragmentSignupCheck2
        )

        binding.fragmentSignupBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
        checkAll.setOnClickListener{
            if (checkAll.isChecked()) {
                checkArray.forEach { checkBox -> checkBox.isChecked = true}
            }
        }
        binding.fragmentSignupSubmit.setOnClickListener{
            if(checkRegisterForm()) register()
        }
        return retView
    }
    private fun checkRegisterForm(): Boolean {
        with(binding){
            val email = fragmentSignupEmail.getEditText()?.getText().toString()
            val name = fragmentSignupName.getEditText()?.getText().toString()
            val password = fragmentSignupPassword.getEditText()?.getText().toString()
            val confirm = fragmentSignupConfirm.getEditText()?.getText().toString()

            if( !(fragmentSignupCheck1.isChecked && fragmentSignupCheck2.isChecked)){
                Toast.makeText(requireContext(), "필수 약관에 동의해주세요", Toast.LENGTH_SHORT).show()
                return false
            }

            if(password.isEmpty() || email.isEmpty() || name.isEmpty()){
                Toast.makeText(requireContext(), "필수요소를 입력해주세요", Toast.LENGTH_SHORT).show()
                return false
            }
            if(password != confirm){
                Toast.makeText(requireContext(), "비밀번호가 일치하지않음", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
    private fun register(){
        val momomeal = MomomealService.momomealAPI
        val email = binding.fragmentSignupEmail.getEditText()?.getText().toString()
        val pwd = binding.fragmentSignupPassword.getEditText()?.getText().toString()
        val name = binding.fragmentSignupName.getEditText()?.getText().toString()

        momomeal.register(RegisterForm(email, pwd, name))
            .enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    if(it.check == 1){
                        Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack("LoginFragment", POP_BACK_STACK_INCLUSIVE)
                    }else{
                        Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}

