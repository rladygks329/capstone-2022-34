package com.capstone.momomeal

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.lifecycle.ViewModelProvider
import com.capstone.momomeal.databinding.FragmentLoginBinding
import com.capstone.momomeal.design.LinearGradientSpan
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.feature.EventObserver
import com.capstone.momomeal.viewmodel.LoginViewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    lateinit var loginViewModel : LoginViewModel
    private val auto : SharedPreferences by lazy { requireActivity().getSharedPreferences("autoLogin", MODE_PRIVATE)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        //init
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        loginViewModel.setEmail(auto.getString("email","")!!)
        loginViewModel.setPassword(auto.getString("password","")!!)
        loginViewModel.setAuto(auto.getBoolean("active", false))

        paintGradient(binding.fragmentLoginAppname)
        return retView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewlifecycleOwner가 oncreate에서 만들어지므로 onViewCreated에서 observer를 달아준다.
        loginViewModel.loginEvent.observe(viewLifecycleOwner, EventObserver<String>{
            when(it){
                "Success" -> startMain()
                "Fail" -> Toast.makeText(requireActivity().applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                "moveGreeting" -> moveGreeting()
            }
        })
        if(auto.getBoolean("active", false)){
            loginViewModel.login()
        }
    }
    private fun startMain(){
        val autoLoginEdit = auto.edit()
        autoLoginEdit.putString("email", loginViewModel._email.value)
        autoLoginEdit.putString("password", loginViewModel._password.value)
        autoLoginEdit.putBoolean("active", loginViewModel._auto.value == true)
        autoLoginEdit.commit()
        startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().finish()
    }
    private fun moveGreeting(){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_login_fragment_container, GreetingFragment())
            .addToBackStack(null)
            .commit()
    }
    private fun paintGradient(view: TextView){
        val text = "모두모아먹자!"
        val orange = ContextCompat.getColor(requireActivity(), R.color.orange_deep)
        val yellow = ContextCompat.getColor(requireActivity(), R.color.yellow)
        val green = ContextCompat.getColor(requireActivity(), R.color.green)
        val spannable = text.toSpannable()
        spannable[0..text.length-1] = LinearGradientSpan(text, text, orange, yellow)
        spannable.setSpan(
            ForegroundColorSpan(green),
            text.length-1, // start
            text.length, // end
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.text = spannable
    }
}