package com.example.curriculumapp.helpers

import android.app.ProgressDialog
import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.curriculumapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*


fun showMsg(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
