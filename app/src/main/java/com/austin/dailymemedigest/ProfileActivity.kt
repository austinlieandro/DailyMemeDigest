package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_meme.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.meme_card_home.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var pack = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(pack, Context.MODE_PRIVATE)
        var id =  shared.getString(LoginActivity.SHARED_ID, null)
        var uname = shared.getString(LoginActivity.SHARED_USERNAME,null)
        var firstName =  shared.getString(LoginActivity.FIRST_NAME, null)
        var lastName =  shared.getString(LoginActivity.LAST_NAME, null)
        var regDate =  shared.getString(LoginActivity.REG_DATE, null)
        var URLavatar =  shared.getString(LoginActivity.URL_AVATAR, null)
        var privacy =  shared.getString(LoginActivity.PRIVACY_SETTING, null)

        var privacynow = ""

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarProfile)
        supportActionBar?.title="Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val outputFormat: DateFormat = SimpleDateFormat("MMM yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val date: Date = inputFormat.parse(regDate)
        val outputText: String = outputFormat.format(date)

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

        if (URLavatar!="null"){
            Picasso.get().load(URLavatar).into(imgAvatar)
        }

        txtActiveProfile.setText("Active since $outputText")
        txtUsernameProfile.setText(uname.toString())

        btnSaveProfile.setOnClickListener {
            if (txtFNameProfile.text.toString()!=""){
                if(checkBoxProfile.isChecked == true){
                    privacynow = "1"
                }else{
                    privacynow = "0"
                }
                val q = Volley.newRequestQueue(it.context)
                val stringRequest = object : StringRequest(
                    com.android.volley.Request.Method.POST, "https://ubaya.fun/native/160420079/api/update_profile.php",
                    {
                        var fNameUpdate = txtFNameProfile.text.toString()
                        var lNameUpdate = txtLNameProfile.text.toString()
                        var privacyUpdate = privacynow.toString()
                        var urlAvatarUpdate = "null"
                        Log.d("cekparams", it)
                        var editor = shared.edit()
                        editor.putString(LoginActivity.FIRST_NAME, txtFNameProfile.text.toString())
                        editor.putString(LoginActivity.LAST_NAME, txtLNameProfile.text.toString())
                        editor.putString(LoginActivity.PRIVACY_SETTING, privacynow.toString())
                        editor.putString(LoginActivity.URL_AVATAR, "null")
                        editor.apply()

                        if ((fNameUpdate=="null"||fNameUpdate=="")&&(lNameUpdate=="null" || lNameUpdate=="")){
                            txtFullNameProfile.setText("User")
                        }else if(fNameUpdate!="null"&&lNameUpdate=="null"){
                            txtFullNameProfile.setText(fNameUpdate)
                            txtFNameProfile.setText(fNameUpdate)
                        }else {
                            txtFullNameProfile.setText(fNameUpdate + " " + lNameUpdate.toString())
                            txtFNameProfile.setText(fNameUpdate)
                            txtLNameProfile.setText(lNameUpdate)
                        }

                        if(privacyUpdate=="1"){
                            checkBoxProfile.isChecked = true
                        }else if (privacyUpdate=="0"){
                            checkBoxProfile.isChecked = false
                        }

                        if (urlAvatarUpdate!="null"){
                            Picasso.get().load(urlAvatarUpdate).into(imgAvatar)
                        }

                        Toast.makeText(this, "Update profile success", Toast.LENGTH_SHORT).show()
                    },
                    {
                        Log.e("cekparams", it.message.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        var map = HashMap<String, String>()
                        map.set("fname", txtFNameProfile.text.toString())
                        map.set("lname", txtLNameProfile.text.toString())
                        map.set("url_avatar", "null")
                        map.set("privacy_setting", privacynow.toString())
                        map.set("userid", id.toString())
                        return map
                    }
                }
                q.add(stringRequest)
            }
            else{
                Toast.makeText(this, "First name can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }

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