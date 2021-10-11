package com.example.curriculumapp.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.curriculumapp.R
import com.example.curriculumapp.adapters.VocabAdapter
import com.example.curriculumapp.helpers.showMsg
import com.example.curriculumapp.models.Days
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lectures.*
import kotlinx.android.synthetic.main.activity_vocab.*

class Vocab : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    lateinit var adapter: VocabAdapter

    var prg: ProgressDialog? = null

    var cards_titles = mutableListOf<Days>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocab)

        prg = ProgressDialog(this)
        prg?.setMessage("Loading...")

        fetchVocab()
        setUp()

    }

    private fun setUp(){
        adapter = VocabAdapter(this, cards_titles, dbRef)
        vocab.layoutManager = GridLayoutManager(this, 2)
        vocab.adapter = adapter

    }

    private fun fetchVocab(){
        prg?.show()
        database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("Links")

        val listner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    showMsg(this@Vocab, "Server not responding")
                }
                prg?.dismiss()
                cards_titles.clear()
//                cards_titles.add(Days(snapshot.children.key.toString()))
                for (snap in snapshot.children) {
                    cards_titles.add(Days(snap.key.toString()))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showMsg(this@Vocab, "Failed to fetch data")
            }

        }

        dbRef.addValueEventListener(listner)
    }
}