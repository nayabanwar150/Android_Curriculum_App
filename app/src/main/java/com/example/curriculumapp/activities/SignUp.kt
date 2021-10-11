package com.example.curriculumapp.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.curriculumapp.R
import com.example.curriculumapp.helpers.showMsg
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    var prg: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        prg = ProgressDialog(this)
        prg?.setMessage("Loading...")

        firebaseAuth = FirebaseAuth.getInstance()

        btn_signUp.setOnClickListener {
            signUpUser()
        }

        link_login.setOnClickListener {
            Intent(this, Login::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun signUpUser() {

        val email: String = signUp_email.text.toString()
        val password: String = signUp_Password.text.toString()
        val confirmPassword: String = signUp_Confirm_Password.text.toString()

        if (email.isEmpty()){
            showMsg(this, "Email can't be empty")
            return
        }
        else if (password.isEmpty() && confirmPassword.isEmpty()){
            showMsg(this, "Password can't be empty")
            return
        }
        else if(password != confirmPassword){
            showMsg(this, "Confirm password didn't match")
            return
        }
        else {
            prg?.show()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    if (it.isSuccessful) {
                        prg?.dismiss()
                        showMsg(this, "Registered! Succesfully...")
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