package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import com.capstone.momomeal.design.LinearGradientSpan

import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity()  {
    private lateinit var email_prompt : TextInputLayout
    private lateinit var password_prompt : TextInputLayout
    private lateinit var autoLogin : CheckBox
    private lateinit var submit : Button
    private lateinit var kakao : Button
    private lateinit var signup : TextView
    private lateinit var appname:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //init
        appname = findViewById(R.id.activity_login_appname)
        email_prompt = findViewById(R.id.activity_login_email)
        password_prompt = findViewById(R.id.activity_login_password)
        autoLogin = findViewById(R.id.activity_login_auto)
        submit = findViewById(R.id.activity_login_submit)
        kakao = findViewById(R.id.activity_login_kakao)
        signup = findViewById(R.id.activity_login_signUp)
        paintGradient(appname)
        submit.setOnClickListener{
            if(logIn()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                val msg = Toast.makeText(this.applicationContext, "로그인 실패", Toast.LENGTH_SHORT)
                msg.show()
            }
        }
        signup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //auto-login
        val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val email = auto.getString("email", null)
        val password = auto.getString("password", null)
        val active = auto.getBoolean("active", true)
        if(active){
            email_prompt.getEditText()?.setText(email)
            password_prompt.getEditText()?.setText(password)
            submit.callOnClick()
        }
    }

    fun logIn(): Boolean {
        var email : String = email_prompt.getEditText()?.getText().toString()
        var password : String = password_prompt.getEditText()?.getText().toString()
        if(email.length == 0 || password.length == 0){
            return false
        }
        if (autoLogin.isChecked) {
            // 자동 로그인 데이터 저장
            val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
            val autoLoginEdit = auto.edit()
            autoLoginEdit.putString("email", email)
            autoLoginEdit.putString("password", password)
            autoLoginEdit.putBoolean("active", autoLogin.isChecked)
            autoLoginEdit.commit()
        }
        return email == "user1234" && password == "1234"
    }
    private fun paintGradient(view: TextView){
        val text = "모두모아먹자!"
        val orange = ContextCompat.getColor(this, R.color.orange_deep)
        val yellow = ContextCompat.getColor(this, R.color.yellow_)
        val green = ContextCompat.getColor(this, R.color.green)
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