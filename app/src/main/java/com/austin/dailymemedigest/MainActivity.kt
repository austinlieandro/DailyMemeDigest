package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.nav_header_main.*
//import kotlinx.coroutines.DefaultExecutor.shared

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        var sharedUname = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedUname, Context.MODE_PRIVATE)
        var uname =  shared.getString(LoginActivity.SHARED_USERNAME, null)

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

        navView.setNavigationItemSelectedListener(this)

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

        val unameheader = headerView.findViewById<View>(R.id.txtUsernameHeader) as? TextView
        unameheader?.setText(uname)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.ItemHomeDrawer->{
                changeFragment(HomeFragment())
            }
            R.id.ItemMyCreationDrawer->{
                changeFragment(SelfCreationFragment())
            }
            R.id.ItemLeaderboardDrawer->{
                changeFragment(LeaderboardFragment())
            }
            R.id.ItemSettingsDrawer->{
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                viewPagerMain.currentItem = 0
            }
        }
        return true
    }

    fun changeFragment(frag:Fragment){
        val fragment=supportFragmentManager.beginTransaction()
        fragment.replace(R.id.viewPagerMain,frag).commit()
    }
}