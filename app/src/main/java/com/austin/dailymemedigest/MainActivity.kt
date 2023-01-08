package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.nav_header_main.*
//import kotlinx.coroutines.DefaultExecutor.shared

class MainActivity : AppCompatActivity() {
    val fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        var sharedUname = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedUname, Context.MODE_PRIVATE)

//        var username =  shared.getString(LoginActivity.SHARED_USERNAME, null)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Daily Meme Digest"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        var drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name,
                R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        fragments.add(HomeFragment())
        fragments.add(SelfCreationFragment())
        fragments.add(LeaderboardFragment())
//        fragments.add(Settingannya kita)

        val adapter = MyViewPagerAdapter(this, fragments)

        viewPagerMain.adapter = adapter

        viewPagerMain.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    val itemId = bottomNav.menu.getItem(position).itemId
                    bottomNav.selectedItemId = itemId
                }
            }
        )

        bottomNav.setOnItemSelectedListener{
            if(it.itemId == R.id.ItemHomeBot){
                viewPagerMain.currentItem = 0
            } else if(it.itemId == R.id.ItemMyCreationBot){
                viewPagerMain.currentItem = 1
            } else if (it.itemId == R.id.ItemLeaderboardBot){
                viewPagerMain.currentItem = 2
            } else{
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                viewPagerMain.currentItem = 0
            }
            true
        }

        val navView: NavigationView = findViewById(R.id.navView)
        val headerView: View = navView.getHeaderView(0)
        val fab = headerView.findViewById<View>(R.id.fabLogOut) as? FloatingActionButton
        fab?.setOnClickListener {
            var editor = shared?.edit()
            editor?.putString(LoginActivity.SHARED_USERNAME,null)
            editor?.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}