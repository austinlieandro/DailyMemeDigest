package com.austin.dailymemedigest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meme_card_home.view.*
import kotlinx.android.synthetic.main.meme_card_selfown.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MemeOwnAdapter (val memes:ArrayList<Meme>) : RecyclerView.Adapter<MemeOwnAdapter.MemeOwnViewHolder>() {
    class MemeOwnViewHolder(val v: View): RecyclerView.ViewHolder(v)
    companion object{
        val IDMEME = "IDMEME"
        val TOPTEXT = "TOPTEXT"
        val BOTTEXT = "BOTTEXT"
        val URL = "URL"
        val LIKE = "LIKE"
        val COMMENT = "COMMENT"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeOwnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.meme_card_selfown, parent, false)
        return MemeOwnAdapter.MemeOwnViewHolder(v)
    }

    override fun onBindViewHolder(holder: MemeOwnViewHolder, position: Int) {
        val urlmeme = memes[position].url
        holder.v.txtTopOwn.text = memes[position].top_text
        holder.v.txtBottomOwn.text = memes[position].bottom_text
        holder.v.txtLikeCountOwn.text = memes[position].numlike.toString() + " Likes"
        holder.v.txtCommentCountOwn.text = memes[position].comcount.toString() + " Comments"
        val outputFormat: DateFormat = SimpleDateFormat("d MMMM y k:mm", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd k:mm", Locale.US)

        val date: Date = inputFormat.parse(memes[position].date)
        val outputText: String = outputFormat.format(date)
        holder.v.txtDateCreatedOwn.text = outputText
        Picasso.get().load(urlmeme).into(holder.v.imgMemeOwn)

        holder.v.btnCommentOwn.setOnClickListener {
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