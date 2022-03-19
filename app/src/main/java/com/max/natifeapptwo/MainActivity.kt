package com.max.natifeapptwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.max.natifeapptwo.presentation.userListPresentation.UserListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserListFragment.newInstance())
            .commit()
    }
}