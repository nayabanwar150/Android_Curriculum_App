package com.example.curriculumapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.curriculumapp.R
import com.example.curriculumapp.activities.*
import com.example.curriculumapp.helpers.showMsg

class DasboardAdapter(val context: Context): Adapter<DasboardAdapter.DasboardListViewHolder>() {

    val card_icons = arrayOf(
        R.drawable.ic_lectures,
        R.drawable.ic_vocab,
        R.drawable.ic_practice,
        R.drawable.ic_notes
    )

    val card_titles = arrayListOf<String>(
        "Lectures",
        "Vocab",
        "Practice",
        "Notes"
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DasboardListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.dashboard_item, parent, false)
        return DasboardListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DasboardListViewHolder, position: Int) {
        holder.list_icon.setImageDrawable(context.getDrawable(card_icons[position]))
        holder.list_title.text = card_titles[position]
        holder.itemView.setOnClickListener{
            when (card_titles[position]) {
                "Lectures" -> Intent(context, Lectures::class.java)
                "Vocab" -> Intent(context, Vocab::class.java)
                "Practice" -> Intent(context, Practice::class.java)
                "Notes" -> Intent(context, Dashboard::class.java)
                else -> Intent(context, Dashboard::class.java)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return card_icons.size
    }

    inner class DasboardListViewHolder(itemView: View): ViewHolder(itemView){
        var list_icon: ImageView = itemView.findViewById(R.id.d_icon)
        var list_title: TextView = itemView.findViewById(R.id.d_title)
    }
}