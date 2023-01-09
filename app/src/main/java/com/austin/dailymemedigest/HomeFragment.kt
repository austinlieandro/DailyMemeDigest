package com.austin.dailymemedigest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_meme.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val memes:ArrayList<Meme> = ArrayList()

    fun UpdateList(){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.memeView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = lm
        recyclerView?.adapter = MemeAdapter(memes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        memes.clear()
        memeView?.adapter?.notifyDataSetChanged()

        var sharedId = "com.austin.dailymemedigest"
        var shared =this.activity!!
            .getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var userid =  shared.getString(LoginActivity.SHARED_ID, null)
//        val userid = intent.getStringExtra(MemeAdapter.IDMEME)
//        val userid = activity!!.intent.extras!!.getString(LoginActivity.SHARED_ID)

        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420079/api/get_meme.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("APIRESULT",it)
                Log.d("userid",userid.toString())
                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()){
                        val objPlay = data.getJSONObject(i)
                        val meme = Meme(objPlay.getInt("id")
                            ,objPlay.getString("url")
                            ,objPlay.getString("top_text")
                            ,objPlay.getString("bottom_text")
                            ,objPlay.getInt("users_id")
                            ,objPlay.getInt("num_like")
                        )
                        memes.add(meme)
                    }
                    UpdateList()
                    Log.d("cekisiarray",memes.toString())
                }
            },
            {
                Log.e("APIERROR",it.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                var map = HashMap<String, String>()
                map.set("userid", userid.toString())
                return map
            }
        }
        queue.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home, container, false)

        var fab : FloatingActionButton? = view?.findViewById(R.id.fabAdd)

        fab?.setOnClickListener{
            val intent = Intent(activity, AddMemeActivity::class.java)
            activity?.startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}