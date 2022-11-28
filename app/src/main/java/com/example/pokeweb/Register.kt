package com.example.pokeweb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val goSignIn = findViewById<Button>(R.id.btnSignR)

        goSignIn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}