package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.meme_card_home.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var sharedUname = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedUname, Context.MODE_PRIVATE)
        var uname = shared.getString(LoginActivity.SHARED_USERNAME,null)

        var sharedFirst = "com.austin.dailymemedigest"
        var sharedF = getSharedPreferences(sharedFirst, Context.MODE_PRIVATE)
        var firstName =  sharedF.getString(LoginActivity.FIRST_NAME, null)

        var sharedLast = "com.austin.dailymemedigest"
        var sharedL = getSharedPreferences(sharedLast, Context.MODE_PRIVATE)
        var lastName =  sharedL.getString(LoginActivity.LAST_NAME, null)

        var sharedDate = "com.austin.dailymemedigest"
        var sharedD = getSharedPreferences(sharedDate, Context.MODE_PRIVATE)
        var regDate =  sharedD.getString(LoginActivity.REG_DATE, null)

        var sharedURL = "com.austin.dailymemedigest"
        var sharedU = getSharedPreferences(sharedURL, Context.MODE_PRIVATE)
        var URLavatar =  sharedU.getString(LoginActivity.URL_AVATAR, null)

        var sharedPrivacy = "com.austin.dailymemedigest"
        var sharedP = getSharedPreferences(sharedPrivacy, Context.MODE_PRIVATE)
        var privacy =  sharedP.getString(LoginActivity.PRIVACY_SETTING, null)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarProfile)
        supportActionBar?.title="Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val outputFormat: DateFormat = SimpleDateFormat("MMM yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val date: Date = inputFormat.parse(regDate)
        val outputText: String = outputFormat.format(date)
//        val sdf = SimpleDateFormat("MMM yy")
//        val pDate = sdf.format(regDate)
        if (firstName=="null"&&lastName=="null"){
            txtFullNameProfile.setText("User")
        }else if(firstName!="null"&&lastName=="null"){
            txtFullNameProfile.setText(firstName.toString())
            txtFNameProfile.setText(firstName)
        }else {
            txtFullNameProfile.setText(firstName.toString() + " " + lastName.toString())
            txtFNameProfile.setText(firstName)
            txtLNameProfile.setText(lastName)
        }

        if(privacy=="1"){
            checkBoxProfile.isChecked = true
        }else if (privacy=="0"){
            checkBoxProfile.isChecked = false
        }
        txtActiveProfile.setText("Active since $outputText")
        txtUsernameProfile.setText(uname.toString())
        Picasso.get().load(URLavatar).into(imgAvatar)

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