package com.denis.sumpic

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.denis.sumpic.databinding.ActivityMainBinding
import com.denis.sumpic.utils.APP_ACTIVITY
import com.denis.sumpic.utils.REQUEST_CODE
import com.denis.sumpic.view.FragmentsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.pager.adapter =FragmentsAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabLayout,binding.pager){
            tab,position -> when(position){
                0 -> tab.text = "Лента"
                1 -> tab.text = "Избранное"
            }
        }.attach()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE)
//        {
//            if (grantResults.size>5 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
//            {
////                saveImage()
//            }
//        } else Toast.makeText(this,"Please provide your required permissions", Toast.LENGTH_SHORT).show()
//    }
}