package com.austin.dailymemedigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    val fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
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
                viewPagerMain.currentItem = 3
            }
            true
        }
    }
}