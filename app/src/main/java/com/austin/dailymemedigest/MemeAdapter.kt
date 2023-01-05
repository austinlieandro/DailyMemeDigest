package com.austin.dailymemedigest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.meme_card_home.view.*
import com.squareup.picasso.Picasso

class MemeAdapter (val memes:ArrayList<Meme>) : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {
    class MemeViewHolder(val v:View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.meme_card_home, parent, false)
        return MemeViewHolder(v)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val url = memes[position].url
        holder.v.txtTopHome.text = memes[position].top_text
        holder.v.txtBottomHome.text = memes[position].bottom_text
        holder.v.txtLikeCountHome.text = memes[position].numlike.toString() + " Likes"
        Picasso.get().load(url).into(holder.v.imgMemeHome)

        holder.v.btnLikeHome.setOnClickListener() {
            val queue = Volley.newRequestQueue(it.context)
            val url = "https://ubaya.fun/native/160420079/api/set_likes.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("CheckParam",it)
                    memes[position].numlike++
                    var newLikes = memes[position].numlike
                    holder.v.txtLikeCountHome.text = "$newLikes Likes"
                },
                {
                    Log.e("paramserror",it.message.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    var params = HashMap<String, String>()
//                    params["id"] = memes[position].id.toString()
                    params.set("id", memes[holder.adapterPosition].id.toString())
                    return params
                }
            }
            queue.add(stringRequest)
        }
    }

    override fun getItemCount(): Int {
        return memes.size
    }
}