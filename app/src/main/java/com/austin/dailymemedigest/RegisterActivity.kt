package com.austin.dailymemedigest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnSignUp.setOnClickListener {
            //volley for register
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160420079/api/register.php"

            var uname = txtUnameSignUp.text.toString()
            var pass = txtPassSignUp.text.toString()
            var repass = txtRepeatPass.text.toString()
//            Toast.makeText(this, "Halo", Toast.LENGTH_SHORT).show()

                if(pass == repass){
                        val stringRequest = object : StringRequest(
                            Method.POST,
                            url,
                            Response.Listener {
                                Log.d("apiresult", it)
                                val obj = JSONObject(it)
                                if (obj.getString("result") == "success") {
                                    Log.d("Regis sukses","Sukses")
                                    Toast.makeText(this, "Create account success", Toast.LENGTH_SHORT).show()

                                    //go to main activity
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this,"Create account failed",Toast.LENGTH_SHORT).show()
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
                }
                else{
                    Toast.makeText(this, "haiya $uname $pass $repass", Toast.LENGTH_SHORT).show()
                }
            }
            }
    }