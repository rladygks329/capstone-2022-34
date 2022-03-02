package com.example.momomeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// 기획상으로는 이 화면에서 정렬되어있는 채팅방을 볼 수 있음.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}