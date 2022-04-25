package com.capstone.momomeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.momomeal.feature.Event
import com.capstone.momomeal.feature.LoginRepository
import com.capstone.momomeal.feature.User
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// 데이터의 변경사항을 알려주는 라이브 데이터를 가지는 뷰모델
class LoginViewModel : ViewModel() {

    // 변경가능한 Mutable 타입의 LiveData(구독가능 데이터)
    private val _loginEvent = MutableLiveData<Event<String>>()
    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user
    val loginEvent: LiveData<Event<String>>
        get() = _loginEvent

    //아래 3가지 속성은 유저가 변경하는 데이터이므로 양방향 데이터 바인딩을 위해 private가 아니게 설정함
    val _email = MutableLiveData<String>()
    val _password = MutableLiveData<String>()
    val _auto = MutableLiveData<Boolean>()

    // 초기값
    init{
        _email.value = ""
        _password.value = ""
        _auto.value = false
    }

    // setter method
    fun setEmail(email : String){
        _email.value = email
    }
    fun setPassword(password : String){
        _password.value = password
    }
    fun setAuto(Auto : Boolean){
        _auto.value = Auto
    }

    fun login(){
        viewModelScope.launch {
            val user = LoginRepository.login(_email.value.toString(), _password.value.toString())
            withContext(Main){
                if(user != null){
                    _user.value = user!!
                    _loginEvent.value = Event("Success")
                }else{
                    _loginEvent.value = Event("Fail")
                }
            }
        }
    }
    fun moveGreeting(){
        _loginEvent.value = Event("moveGreeting")
    }
}