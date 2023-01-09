package com.austin.dailymemedigest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_meme.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbarDetail)
        supportActionBar?.title="Meme Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var sharedId = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var id =  shared.getString(LoginActivity.SHARED_ID, null)

        val memeid = intent.getStringExtra(MemeAdapter.IDMEME)

        btnSendComment.setOnClickListener {
            if (txtAddComment.text.toString()!=""){
                val q = Volley.newRequestQueue(it.context)
                val stringRequest = object : StringRequest(
                    com.android.volley.Request.Method.POST, "https://ubaya.fun/native/160420079/api/add_comment.php",
                    {
                        Log.d("cekparams", it)
                    },
                    {
                        Log.e("cekparams", it.message.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        var map = HashMap<String, String>()
                        map.set("comment", txtAddComment.text.toString())
                        map.set("userid", id.toString())
                        map.set("memeid", memeid.toString())
                        return map
                    }
                }
                q.add(stringRequest)
                txtAddComment.text?.clear()
            }
            else{
                Toast.makeText(this, "Please fill all input!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}