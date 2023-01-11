package com.austin.dailymemedigest

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.meme_card_home.view.*
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MemeAdapter (val memes:ArrayList<Meme>, val userid:String, val context: Context) : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {
    class MemeViewHolder(val v:View):RecyclerView.ViewHolder(v)

    companion object{
        val IDMEME = "IDMEME"
        val TOPTEXT = "TOPTEXT"
        val BOTTEXT = "BOTTEXT"
        val URL = "URL"
        val LIKE = "LIKE"
        val COMMENT = "COMMENT"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.meme_card_home, parent, false)
        return MemeViewHolder(v)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {

        if (memes[position].user_id.toString()==userid){
            holder.v.btnLikeHome.isActivated = false;
            holder.v.btnLikeHome.setImageResource(R.drawable.greyheart)

        }else{
            holder.v.btnLikeHome.isActivated = true;
            if (memes[position].terlike=="0"){
                holder.v.btnLikeHome.setImageResource(R.drawable.greyheart)
            }else{
                holder.v.btnLikeHome.setImageResource(R.drawable.redheart)
            }
        }

        if (memes[position].tersave=="0"){
            holder.v.btnSave.setImageResource(R.drawable.saveoutline)
        }else{
            holder.v.btnSave.setImageResource(R.drawable.savefill)
        }

        val urlmeme = memes[position].url
        holder.v.txtTopHome.text = memes[position].top_text
        holder.v.txtBottomHome.text = memes[position].bottom_text
        holder.v.txtLikeCountHome.text = memes[position].numlike.toString() + " Likes"
        holder.v.txtCommentCountHome.text = memes[position].comcount.toString() + " Comments"
        Picasso.get().load(urlmeme).into(holder.v.imgMemeHome)

        holder.v.btnSave.setOnClickListener {
            val queue = Volley.newRequestQueue(it.context)
            val url = "https://ubaya.fun/native/160420079/api/set_save.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("CheckParam",it)
                    val obj = JSONObject(it)
                    if(obj.getString("msgcode")=="1"){
                        holder.v.btnSave.setImageResource(R.drawable.savefill)
                        memes[position].tersave= memes[position].id.toString()
                    }else if(obj.getString("msgcode")=="0"){
                        holder.v.btnSave.setImageResource(R.drawable.saveoutline)
                        memes[position].tersave= "0"
                    }
                },
                {
                    Log.e("paramserror",it.message.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    var params = HashMap<String, String>()
                    params.set("userid", userid)
                    params.set("memeid", memes[holder.adapterPosition].id.toString())
                    return params
                }
            }
            queue.add(stringRequest)
        }

        holder.v.btnLikeHome.setOnClickListener() {
            if (!holder.v.btnLikeHome.isActivated){
                val builder = AlertDialog.Builder(context)
                builder.setMessage("You can't like your own meme!!")
                builder.setNegativeButton("OK", null)
                builder.create().show()
            }else{
                val queue = Volley.newRequestQueue(it.context)
                val url = "https://ubaya.fun/native/160420079/api/set_likes.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("CheckParam",it)
                        val obj = JSONObject(it)
                        var numlike = memes[position].numlike
                        if(obj.getString("msgcode")=="1"){
                            holder.v.btnLikeHome.setImageResource(R.drawable.redheart)
                            numlike++
                            memes[position].numlike++
                            holder.v.txtLikeCountHome.text = "$numlike Likes"
                            memes[position].terlike= memes[position].id.toString()
                        }else if(obj.getString("msgcode")=="0"){
                            holder.v.btnLikeHome.setImageResource(R.drawable.greyheart)
                            numlike--
                            memes[position].numlike--
                            holder.v.txtLikeCountHome.text = "$numlike Likes"
                            memes[position].terlike= "0"
                        }
                    },
                    {
                        Log.e("paramserror",it.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        var params = HashMap<String, String>()
                        params.set("userid", userid)
                        params.set("memeid", memes[holder.adapterPosition].id.toString())
                        return params
                    }
                }
                queue.add(stringRequest)
            }
        }

        holder.v.imgMemeHome.setOnClickListener {
            val context=holder.v.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IDMEME, memes[position].id.toString())
            intent.putExtra(URL,  memes[holder.adapterPosition].url)
            intent.putExtra(TOPTEXT,  memes[holder.adapterPosition].top_text)
            intent.putExtra(BOTTEXT,  memes[holder.adapterPosition].bottom_text)
            intent.putExtra(LIKE,  memes[holder.adapterPosition].numlike.toString())
            intent.putExtra(COMMENT,  memes[holder.adapterPosition].comcount.toString())
            context.startActivity(intent)
        }

        holder.v.btnCommentHome.setOnClickListener {
            val context=holder.v.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IDMEME, memes[position].id.toString())
            intent.putExtra(URL,  memes[holder.adapterPosition].url)
            intent.putExtra(TOPTEXT,  memes[holder.adapterPosition].top_text)
            intent.putExtra(BOTTEXT,  memes[holder.adapterPosition].bottom_text)
            intent.putExtra(LIKE,  memes[holder.adapterPosition].numlike.toString())
            intent.putExtra(COMMENT,  memes[holder.adapterPosition].comcount.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return memes.size
    }
}