package com.austin.dailymemedigest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.leaderboard_layout.view.*
import kotlinx.android.synthetic.main.meme_card_home.view.*

class LeaderboardAdapter (val leaderboards:ArrayList<Leaderboard>) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    class LeaderboardViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.leaderboard_layout, parent, false)
        return LeaderboardAdapter.LeaderboardViewHolder(v)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val urlavatar = leaderboards[position].avatar
        holder.v.txtNameLeaderboard.text = leaderboards[position].username
        holder.v.txtLikeCountLeaderboard.text = leaderboards[position].numlike.toString() + " Likes"
        if (urlavatar!="null"){
            Picasso.get().load(urlavatar).into(holder.v.imgProfileLeaderboard)
        }
    }

    override fun getItemCount(): Int {
        return leaderboards.size
    }
}