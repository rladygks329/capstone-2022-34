package com.capstone.momomeal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.momomeal.databinding.FragmentLoginBinding
import com.capstone.momomeal.design.LinearGradientSpan
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.feature.EventObserver
import com.capstone.momomeal.data.User
import com.capstone.momomeal.viewmodel.LoginViewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val TAG = "LoginFragment"
    private val auto : SharedPreferences by lazy { requireActivity().getSharedPreferences("autoLogin", MODE_PRIVATE)}
    lateinit var loginViewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        //init
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        if(auto.getBoolean("active", false)){
            loginViewModel.email = auto.getString("email", "")!!
            loginViewModel.password = auto.getString("password", "")!!
            loginViewModel.auto = auto.getBoolean("active", false)
        }
        binding.fragmentLoginKakao.setOnClickListener{
            val profileFrag = ProfileFragment()
            profileFrag.arguments = bundleOf("user_id" to 3)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_login_fragment_container, profileFrag)
                .addToBackStack(TAG)
                .commit()
        }
        paintGradient(binding.fragmentLoginAppname)
        return retView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewlifecycleOwner가 oncreate에서 만들어지므로 onViewCreated에서 observer를 달아준다.
        loginViewModel.loginEvent.observe(viewLifecycleOwner, EventObserver<String>{
            view?.hideKeyboard()
            when(it){
                "Fail" ->showMSG("로그인 실패")
                "moveGreeting" -> moveGreeting()
            }
        })
        loginViewModel.user.observe(viewLifecycleOwner, Observer {
            view?.hideKeyboard()
            if(it.member != null){
                startMain(it.member.toUser(), it.recommend)
            }else{
                showMSG("이메일이나 비밀번호를 확인해주세요")
            }
        })
        if(auto.getBoolean("active", false)){
            loginViewModel.login()
        }
    }
    private fun startMain(user: User, recommend: String){
        val autoLoginEdit = auto.edit()
        autoLoginEdit.putString("email", loginViewModel.email)
        autoLoginEdit.putString("password", loginViewModel.password)
        autoLoginEdit.putBoolean("active", loginViewModel.auto)
        autoLoginEdit.commit()
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("user", user)
        intent.putExtra("recommend", recommend == "yes")
        startActivity(intent)
        requireActivity().finish()
    }
    private fun moveGreeting(){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_login_fragment_container, GreetingFragment())
            .addToBackStack(TAG)
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
    fun showMSG(s: String){
        Toast.makeText(requireActivity().applicationContext, s, Toast.LENGTH_SHORT).show()
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}