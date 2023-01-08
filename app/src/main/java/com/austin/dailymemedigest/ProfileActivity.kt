package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var sharedUname = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedUname, Context.MODE_PRIVATE)

        var sharedFirst = "com.austin.dailymemedigest"
        var sharedF = getSharedPreferences(sharedFirst, Context.MODE_PRIVATE)
//        var firstName =  shared.getString(LoginActivity.SHARED, null)

        var sharedLast = "com.austin.dailymemedigest"
        var sharedL = getSharedPreferences(sharedLast, Context.MODE_PRIVATE)
//        var lastName =  shared.getString(LoginActivity.SHARED_ID, null)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarProfile)
        supportActionBar?.title="Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabLogOutProfile.setOnClickListener {
            var editor = shared?.edit()
            editor?.putString(LoginActivity.SHARED_USERNAME,null)
            editor?.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}