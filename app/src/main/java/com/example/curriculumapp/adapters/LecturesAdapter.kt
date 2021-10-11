package com.example.curriculumapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.R
import com.example.curriculumapp.models.Days
import com.google.firebase.database.DatabaseReference

class LecturesAdapter(val context: Context, private val titles: List<Days>,private val dbRef: DatabaseReference): RecyclerView.Adapter<LecturesAdapter.LecturesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LecturesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.play_card, parent, false)
        return LecturesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LecturesViewHolder, position: Int) {
        holder.video_title.text = titles[position].title
        holder.itemView.setOnClickListener{

            dbRef.child(titles[position].title).get().addOnSuccessListener {
                val vv = it.child("grammar").toString()

                it.children.forEach {
                    Log.d("link", it.child("grammar").value.toString())
                    val intent = Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(it.child("grammar").value.toString()))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    context.startActivity(intent)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class LecturesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val video_title: TextView = itemView.findViewById(R.id.video_title)
    }
}