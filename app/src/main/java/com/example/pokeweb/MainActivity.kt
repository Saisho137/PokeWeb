package com.example.pokeweb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goRegister = findViewById<Button>(R.id.btnRegistR)
        val goPokeCompanion = findViewById<Button>(R.id.btnSignR)

        goRegister.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
        }
        goPokeCompanion.setOnClickListener{
            startActivity(Intent(this,PokeCompanion::class.java))
        }
    }
}