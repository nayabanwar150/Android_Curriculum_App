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
import java.util.zip.Inflater

class PracticeAdapter(val context: Context, val titles: List<Days>, val dbRef: DatabaseReference): RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.play_card, parent, false)
        return PracticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.title.text = titles[position].title
        holder.itemView.setOnClickListener{

            dbRef.child(titles[position].title).get().addOnSuccessListener {
                val vv = it.child("grammar").toString()

                it.children.forEach {
                    Log.d("link", it.child("practice").value.toString())
                    val intent = Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(it.child("practice").value.toString()))
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

    inner class PracticeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.video_title)
    }
}