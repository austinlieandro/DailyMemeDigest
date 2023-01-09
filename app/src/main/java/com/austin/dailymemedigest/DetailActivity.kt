package com.austin.dailymemedigest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_meme.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.meme_card_home.view.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    var comments:ArrayList<Comment> = ArrayList()
    var memeid = ""

    fun updateList(){
        val lm:LinearLayoutManager = LinearLayoutManager(this)
        recyclerComment.setHasFixedSize(true)
        recyclerComment.layoutManager = lm
        lm.stackFromEnd=true
        recyclerComment.adapter = CommentAdapter(comments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbarDetail)
        supportActionBar?.title="Meme Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var sharedId = "com.austin.dailymemedigest"
        var shared = getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var id =  shared.getString(LoginActivity.SHARED_ID, null)

        memeid = intent.getStringExtra(MemeAdapter.IDMEME).toString()
        val urlmeme=intent.getStringExtra(MemeAdapter.URL).toString()
        val toptext=intent.getStringExtra(MemeAdapter.TOPTEXT).toString()
        val bottext=intent.getStringExtra(MemeAdapter.BOTTEXT).toString()
        val like=intent.getStringExtra(MemeAdapter.LIKE).toString()

        Picasso.get().load(urlmeme).into(imgMemeDetail)
        txtBottomDetail.setText(bottext)
        txtTopDetail.setText(toptext)
        txtLikeDetail.setText("$like Likes")


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
                onResume()
//                txtAddComment.setText("")
            }
            else{
                Toast.makeText(this, "Please fill all input!", Toast.LENGTH_SHORT).show()
            }
//            recyclerComment?.adapter?.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        recyclercoba()
        comments.clear()
//        txtAddComment.setText("")
    }

    fun recyclercoba(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160420079/api/get_comment.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("APIRESULT",it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val data = obj.getJSONArray("data")

                    for(i in 0 until data.length()){
                        val comObj = data.getJSONObject(i)
                        val commentall = Comment(
                            comObj.getString("username"),
                            comObj.getInt("memes_id"),
                            comObj.getString("comment"),
                            comObj.getString("date")
                        )
                        comments.add(commentall)
                    }
                    updateList()
                    Log.d("cekisiarray",comments.toString())
                }
            },
            {
                Log.e("APIERROR",it.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                var map = HashMap<String, String>()
                map.set("memeid", memeid.toString())
                return map
            }
        }
        queue.add(stringRequest)
    }
}