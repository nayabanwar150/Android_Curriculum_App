package com.example.curriculumapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.curriculumapp.R
import com.example.curriculumapp.adapters.DasboardAdapter
import com.example.curriculumapp.helpers.showMsg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import android.os.Looper




class Dashboard : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: DasboardAdapter
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        firebaseAuth = FirebaseAuth.getInstance()

        setups()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    private fun setups(){
        setUpDrawerLayout()
        setUpRecyclerView()
    }


    fun setUpRecyclerView(){
        adapter = DasboardAdapter(this)
        dashboard_list.layoutManager = GridLayoutManager(this, 2)
        dashboard_list.adapter = adapter
    }

    fun setUpDrawerLayout(){
        setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            firebaseAuth.signOut()
            showMsg(this,"Singout successfully")
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            drawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}