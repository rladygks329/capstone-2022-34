package com.capstone.momomeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.capstone.momomeal.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

// 기획상으로는 이 화면에서 정렬되어있는 채팅방을 볼 수 있음.

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mainBnv: BottomNavigationView by lazy {
        findViewById(R.id.bnv_main)
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bottomNavigationView : BottomNavigationView = findViewById(R.id.bnv_main)

        NavigationUI.setupWithNavController(bottomNavigationView, findNavController(R.id.fr_main_navi_host))
        // BottomNavigationView의 OnClickListener
//        mainBnv.setOnItemSelectedListener {
//
//
//        }
//        binding.

    }

    public fun changeFragment(fragment: Fragment) {
        Log.d(TAG, "changeFragment Activated")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main_full_container, fragment)
            .commit()
    }
}