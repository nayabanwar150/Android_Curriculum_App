package com.example.curriculumapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.curriculumapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_welcome.*
import java.lang.Exception

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val user = FirebaseAuth.getInstance()

        if(user.currentUser != null){
            redirect("Dashboard")
        }

        btn_start.setOnClickListener {
            redirect("Login")
        }
    }

    private fun redirect(option: String){
        when(option){
            "Login" -> {
                startActivity(Intent(this, Login::class.java))
            }
            "Dashboard" -> {
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            }
            else -> throw Exception("No path exist")
        }

    }
}