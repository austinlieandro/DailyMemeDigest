package com.austin.dailymemedigest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_saved.*
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SavedFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val memes:ArrayList<Meme> = ArrayList()

    fun UpdateList(userid:String){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.savedView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = lm
        recyclerView?.adapter = MemeAdapter(memes, userid)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        memes.clear()
        savedView?.adapter?.notifyDataSetChanged()

        var sharedId = "com.austin.dailymemedigest"
        var shared =this.activity!!
            .getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var userid =  shared.getString(LoginActivity.SHARED_ID, null)

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
                    val liked = obj.getJSONArray("lk")
                    var likec =""
                    for (i in 0 until data.length()){
                        val objPlay = data.getJSONObject(i)
                        for (a in 0 until liked.length()){
                            val objLike = liked.getJSONObject(a)
                            if (objLike.getString("meme_id") == objPlay.getInt("id").toString()){
                                likec = objLike.getString("meme_id")
                                break
                            }else{
                                likec="0"
                            }
                        }
                        val meme = Meme(objPlay.getInt("id")
                            ,objPlay.getString("url")
                            ,objPlay.getString("top_text")
                            ,objPlay.getString("bottom_text")
                            ,objPlay.getInt("users_id")
                            ,objPlay.getInt("num_like")
                            ,objPlay.getString("date_create")
                            ,objPlay.getInt("num_count")
                            ,likec
                        )
                        memes.add(meme)
                    }
                    UpdateList(userid.toString())
                    Log.d("cekisiarray",memes.toString())
                    Log.d("cekisiarraylike",liked.toString())
                }
            },
            {
                Log.e("APIERROR",it.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                var map = HashMap<String, String>()
                map.set("userid", userid.toString())
                map.set("cmd", "1")
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
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}