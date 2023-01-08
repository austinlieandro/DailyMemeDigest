package com.austin.dailymemedigest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_meme.*

class AddMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meme)

        var sharedId = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var id =  shared.getString(LoginActivity.SHARED_ID, null)


        btnSubmitCreate.setOnClickListener {
            val q = Volley.newRequestQueue(it.context)
            val stringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, "https://ubaya.fun/native/160420079/api/add_meme.php",
                {
                    Log.d("cekparams", it)
                },
                {
                    Log.e("cekparams", it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String, String>()
                    map.set("toptext", txtTopText.text.toString())
                    map.set("bottext", txtBottomText.text.toString())
                    map.set("url", txtImageURL.text.toString())
                    map.set("userid", id.toString())
                    return map
                }
            }
            q.add(stringRequest)
            finish()
        }

        txtImageURL.addTextChangedListener {
            if (txtImageURL.text.toString() == ""){

            } else{
                Picasso.get().load(txtImageURL.text.toString()).into(imgMemeAdd)
            }

        }
        txtBottomText.addTextChangedListener {
            if (txtBottomText.text.toString() == ""){
                txtBottomTextAdd.setText("")
            }else{
                txtBottomTextAdd.setText(txtBottomText.text.toString())
            }
        }

        txtTopText.addTextChangedListener {
            if (txtTopText.text.toString() == ""){
                txtTopTextAdd.setText("")
            }else{
                txtTopTextAdd.setText(txtTopText.text.toString())
            }
        }
    }
}