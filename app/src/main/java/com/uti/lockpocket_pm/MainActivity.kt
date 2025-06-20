package com.uti.lockpocket_pm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.app.ActivityOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageView>(R.id.lg)
        button.setOnClickListener {
            val intent = Intent(this, login2Activity::class.java)

            val intent = Intent(this, login2Activity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            startActivity(intent, options.toBundle())

        }
