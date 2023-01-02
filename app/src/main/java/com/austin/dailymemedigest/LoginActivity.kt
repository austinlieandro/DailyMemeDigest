package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    companion object {
        val SHARED_USERNAME = "SHARED_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username = ""

        //username already login
        var sharedUname = packageName
        var shared = getSharedPreferences(sharedUname, Context.MODE_PRIVATE)
        var name = shared.getString(SHARED_USERNAME, null)
        if (name != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSignIn.setOnClickListener {
            //volley for login
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160420079/api/login.php"

            var uname = txtUsername.text
            var pass = txtPassword.text
            val stringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {

                        val data = obj.getJSONArray("data")
                        val objData = data.getJSONObject(0)
                        username = objData.getString("username")
//
                        //update already username
                        var editor = shared.edit()
                        editor.putString(SHARED_USERNAME,username)
                        editor.apply()

                        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()

                        //go to main activity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Username or password is not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Log.e("apierror", it.message.toString())
                }) {

                override fun getParams(): MutableMap<String, String> {
                    return hashMapOf("username" to uname.toString(), "password" to pass.toString())
                }
            }
            q.add(stringRequest)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            btnCreateAccount.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}