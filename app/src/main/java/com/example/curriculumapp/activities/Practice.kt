package com.example.curriculumapp.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.curriculumapp.R
import com.example.curriculumapp.adapters.LecturesAdapter
import com.example.curriculumapp.adapters.PracticeAdapter
import com.example.curriculumapp.helpers.showMsg
import com.example.curriculumapp.models.Days
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lectures.*
import kotlinx.android.synthetic.main.activity_practice.*

class Practice : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    lateinit var pracAdapter: PracticeAdapter

    var prg: ProgressDialog? = null

    var cards_titles = mutableListOf<Days>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        prg = ProgressDialog(this)
        prg?.setMessage("Loading...")

        pracSetups()
    }

    private fun pracSetups(){
        dbSetups()
        adapterSetups()
    }

    private fun dbSetups(){
        prg?.show()
        database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("Links")

        val listner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    showMsg(this@Practice, "Server not responding")
                }
                prg?.dismiss()
                cards_titles.clear()
                for (snap in snapshot.children) {
                    cards_titles.add(Days(snap.key.toString()))
                }
                pracAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showMsg(this@Practice, "Failed to fetch data")
            }

        }

        dbRef!!.addValueEventListener(listner)
    }

    private fun adapterSetups(){
        pracAdapter = PracticeAdapter(this,cards_titles, dbRef)
        practice.layoutManager = GridLayoutManager(this, 2)
        practice.adapter = pracAdapter
    }
}