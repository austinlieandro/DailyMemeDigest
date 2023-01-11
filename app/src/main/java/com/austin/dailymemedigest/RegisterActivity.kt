package com.austin.dailymemedigest

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnSignUp.setOnClickListener {
            if(txtUnameSignUp.text.toString()!=""&&txtPassSignUp.text.toString()!=""&&txtRepeatPass.text.toString()!=""){
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.fun/native/160420079/api/register.php"

                var uname = txtUnameSignUp.text.toString()
                var pass = txtPassSignUp.text.toString()
                var repass = txtRepeatPass.text.toString()

                if(pass == repass){
                    val stringRequest = object : StringRequest(
                        Method.POST,
                        url,
                        Response.Listener {
                            Log.d("apiresult", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "success") {
                                Toast.makeText(this, "Create account success", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Create account failed, username taken!",
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
                }else{
                    Toast.makeText(this, "Password and repeated password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please fill all input!", Toast.LENGTH_SHORT).show()
            }

        }

        btnBackRegister.setOnClickListener {
            finish()
        }
    }
}