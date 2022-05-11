package com.capstone.momomeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.momomeal.feature.Event
import com.capstone.momomeal.feature.LoginRepository
import com.capstone.momomeal.data.User
import kotlinx.coroutines.launch


// 데이터의 변경사항을 알려주는 라이브 데이터를 가지는 뷰모델
class LoginViewModel : ViewModel() {

    private val loginRepo = LoginRepository()
    // 변경가능한 Mutable 타입의 LiveData(구독가능 데이터)
    private val _loginEvent = MutableLiveData<Event<String>>()
    private val _user = MutableLiveData<User>()


    val user: LiveData<User>
        get() = _user
    val loginEvent: LiveData<Event<String>>
        get() = _loginEvent

    val _email = MutableLiveData<String>()

    // 아래 3가지 속성은 유저가 변경하는 데이터이므로 양방향 데이터 바인딩을 위해 private가 아니게 설정함
    // 유저 인풋에 따라 변경되는 이벤트가 없으므로 livedata로 선언하지 않았음
    var email: String = ""
    var password: String = ""
    var auto : Boolean = false

    fun login(){
        viewModelScope.launch {
            val user = loginRepo.login(email, password)
            if(user == null){
                _loginEvent.value = Event("Fail")
            }else{
                _user.value = user!!
            }
        }
    }
    fun moveGreeting(){
        _loginEvent.value = Event("moveGreeting")
    }
}