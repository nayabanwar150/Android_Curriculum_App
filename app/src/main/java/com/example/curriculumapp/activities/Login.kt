package com.example.curriculumapp.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.curriculumapp.R
import com.example.curriculumapp.helpers.showMsg
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    var prg: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prg = ProgressDialog(this)
        prg?.setMessage("Loading...")

        firebaseAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener{
            loginUser()
        }

        link_singUp.setOnClickListener {
            Intent(this, SignUp::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun loginUser(){
        val email: String = login_email.text.toString()
        val password: String = login_Password.text.toString()

        if (email.isEmpty()){
            showMsg(this, "Email can't be empty")
            return
        }
        else if (password.isEmpty()){
            showMsg(this, "Password can't be empty")
            return
        }
        else {
            prg?.show()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        prg?.dismiss()
                        showMsg(this, "Login Succesfully!")
                        startActivity(Intent(this, Dashboard::class.java))
                        finish()
                    } else {
                        prg?.dismiss()
                        showMsg(this, "Please! try again")
                    }
                }
        }
    }

}