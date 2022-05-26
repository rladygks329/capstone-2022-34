package com.capstone.momomeal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.momomeal.feature.Event
import com.capstone.momomeal.feature.LoginRepository
import com.capstone.momomeal.data.User
import com.capstone.momomeal.data.dto.LoginResponse
import com.capstone.momomeal.data.fakeLoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// 데이터의 변경사항을 알려주는 라이브 데이터를 가지는 뷰모델
class LoginViewModel : ViewModel() {

    private val loginRepo = LoginRepository()
    // 변경가능한 Mutable 타입의 LiveData(구독가능 데이터)
    private val _loginEvent = MutableLiveData<Event<String>>()
    private val _user = MutableLiveData<LoginResponse>()


    val user: LiveData<LoginResponse>
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
        //만약 실행이 안된다면 아래 함수를 주석처리하고, _user.postValue(fakeuser) 이런식으로 사용
        //_user.postValue(fakeLoginResponse[0])
        viewModelScope.launch {
            loginRepo.login(email, password).enqueue(object :Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    //Log.d("retrofit",response.body().toString())
                    if(response.isSuccessful.not()){
                        _loginEvent.postValue(Event("Fail"))
                        return
                    }
                    response.body()?.let{
                        _user.postValue(it)
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    //Log.e("retrofit", t.toString())
                    _loginEvent.postValue(Event("Fail"))
                }
            })
        }
    }
    fun moveGreeting(){
        _loginEvent.value = Event("moveGreeting")
    }
}