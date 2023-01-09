package com.austin.dailymemedigest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_card.view.*
import kotlinx.android.synthetic.main.meme_card_home.view.*

class CommentAdapter (val comments:ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    class CommentViewHolder(val v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.comment_card, parent, false)
        return CommentViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.v.txtUnameComment.text = comments[position].username
        holder.v.txtCommentContent.text = comments[position].comment
        holder.v.txtCommentDate.text = comments[position].datecomment
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}