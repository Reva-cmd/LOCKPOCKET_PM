package com.uti.lockpocket_pm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val edtUsername = findViewById<EditText>(R.id.edtNewUsername)
        val edtPassword = findViewById<EditText>(R.id.edtNewPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

        }
    }
}
