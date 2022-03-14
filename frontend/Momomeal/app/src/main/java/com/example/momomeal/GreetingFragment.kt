package com.example.momomeal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment

class GreetingFragment : Fragment() {
    lateinit var registerActivity: RegisterActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_greeting, container, false)

        paint(view.findViewById<TextView>(R.id.fragment_greeting_txt))
        view.findViewById<Button>(R.id.fragment_greeting_email).setOnClickListener{
            registerActivity.changeFragment(SignupFragment())
        }
        view.findViewById<TextView>(R.id.fragment_greeting_back).setOnClickListener{
            registerActivity.finish()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerActivity = context as RegisterActivity
    }
    private fun paint(view: TextView){
        val text = "혼자 해결하던 식사\n이제는 다 같이 먹자!"
        val orange = ContextCompat.getColor(registerActivity, R.color.orange_deep)
        val spannable = text.toSpannable()
        spannable.setSpan(
            ForegroundColorSpan(orange),
            14,
            19,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.text = spannable
    }
}
