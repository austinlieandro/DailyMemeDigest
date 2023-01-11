package com.austin.dailymemedigest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_self_creation.*
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LeaderboardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    val leaderboards:ArrayList<Leaderboard> = ArrayList()

    fun UpdateList(){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.leaderboardView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = lm
        recyclerView?.adapter = LeaderboardAdapter(leaderboards)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onResume() {
        super.onResume()
        leaderboards.clear()
        leaderboardView?.adapter?.notifyDataSetChanged()

        var sharedId = "com.austin.dailymemedigest"
        var shared =this.activity!!
            .getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var userid =  shared.getString(LoginActivity.SHARED_ID, null)

        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420079/api/leaderboard.php"
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
                        var fullname =""
                        var nameakhir = ""
                        if (objPlay.getString("name")=="null"){
                            nameakhir="User"
                        }else{
                            fullname=objPlay.getString("name")
                            if (objPlay.getInt("privacy_setting")==1){
                                nameakhir = censor(fullname)
                            }else{
                                nameakhir=fullname
                            }
                        }

                        val lead = Leaderboard(nameakhir
                            ,objPlay.getInt("sum_like")
                            ,objPlay.getString("url_avatar")
                        )
                        leaderboards.add(lead)
                    }
                    UpdateList()
                    Log.d("cekisiarray",leaderboards.toString())
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

    fun censor(name:String): String {
        var count = 1
        var hasil=""
        for (ch in name.iterator()){
            if(count>3){
                if(ch == ' '){
                    hasil+=" "
                }else{
                    hasil+="*"
                }
            }else{
                hasil+=ch
            }
            count++
        }
        return hasil
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}