package com.example.curriculumapp.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.curriculumapp.R
import com.example.curriculumapp.adapters.LecturesAdapter
import com.example.curriculumapp.helpers.showMsg
import com.example.curriculumapp.models.Days
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lectures.*
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_dashboard.*


class Lectures : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    lateinit var lecAdapter: LecturesAdapter

    var prg: ProgressDialog? = null

    var cards_titles = mutableListOf<Days>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectures)

        prg = ProgressDialog(this)
        prg?.setMessage("Loading...")

        lecturesSetups()
    }



    private fun lecturesSetups(){
        getLectures()
        setUpLectureList()
    }


    private fun setUpLectureList(){
        lecAdapter = LecturesAdapter(this, cards_titles, dbRef)
        lectures.layoutManager = GridLayoutManager(this, 2)
        lectures.adapter = lecAdapter

    }

    private fun getLectures(){
        prg?.show()
        database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("Links")

        val listner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    showMsg(this@Lectures, "Server not responding")
                }

                prg?.dismiss()
                cards_titles.clear()

                for (snap in snapshot.children) {
                    cards_titles.add(Days(snap.key.toString()))
                }
                lecAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showMsg(this@Lectures, "Failed to fetch data")
            }

        }

        dbRef!!.addValueEventListener(listner)
    }
}