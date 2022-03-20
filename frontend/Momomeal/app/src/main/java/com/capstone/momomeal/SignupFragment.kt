package com.capstone.momomeal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout


class SignupFragment : Fragment() {
    lateinit var registerActivity: RegisterActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_signup, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.fragment_signup_toolbar)
        val submit = view.findViewById<Button>(R.id.fragment_signup_submit)
        val checkAll = view.findViewById<CheckBox>(R.id.fragment_signup_checkAll)
        val checkArray = arrayOf<CheckBox>(
            view.findViewById<CheckBox>(R.id.fragment_signup_check1),
            view.findViewById<CheckBox>(R.id.fragment_signup_check2)
        )

        registerActivity.initToolbar(toolbar)
        checkAll.setOnClickListener{
            if (checkAll.isChecked()) {
                checkArray.forEach { checkBox -> checkBox.isChecked = true}
            }
        }
        submit.setOnClickListener{
            val email = view.findViewById<TextInputLayout>(R.id.fragment_signup_email).getEditText()?.getText().toString()
            val name = view.findViewById<TextInputLayout>(R.id.fragment_signup_name).getEditText()?.getText().toString()
            val password = view.findViewById<TextInputLayout>(R.id.fragment_signup_password).getEditText()?.getText().toString()
            val confirm = view.findViewById<TextInputLayout>(R.id.fragment_signup_confirm).getEditText()?.getText().toString()

            if(checkArray[0].isChecked && checkArray[1].isChecked){
                if(password.length > 0 && email.length>0 && name.length >0){
                    if(password == confirm){
                        Toast.makeText(registerActivity, "비밀번호가 일치함", Toast.LENGTH_SHORT).show()
                        //email, name password -> server
                    }else{
                        Toast.makeText(registerActivity, "비밀번호가 일치하지않음", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(registerActivity, "필수요소를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(registerActivity, "필수 약관에 동의해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerActivity = context as RegisterActivity
    }
}

