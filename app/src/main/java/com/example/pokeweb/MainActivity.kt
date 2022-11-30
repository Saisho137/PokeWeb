package com.example.pokeweb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.pokeweb.models.UserInfo
import com.example.pokeweb.pokeApi.MyApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://e2df-2800-e2-280-a76-c9f-58e7-7165-ce5a.ngrok.io/api/PokeWeb/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: MyApiService = retrofit.create(MyApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Buttons
        val goRegister = findViewById<Button>(R.id.btnRegistR)
        val goPokeCompanion = findViewById<Button>(R.id.btnSignR)
        //EditText
        val emailTb = findViewById<EditText>(R.id.tbEmailR)//EditText variable
        val email = emailTb.text.toString().trim()//Text email String
        val passTb = findViewById<EditText>(R.id.tbPassR)//EditText variable
        val pass = passTb.text.toString().trim()//Text password String
        //Buttons Functions
        goRegister.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
        }
        goPokeCompanion.setOnClickListener{
            //Validations
            /*if(email.isEmpty()){
                emailTb.error = "Email required"
                return@setOnClickListener}
            if(pass.isEmpty()){
                passTb.error = "Password required"
                return@setOnClickListener }*/
            //
            val userInfo = UserInfo(
                emailUser = email,
                userPassword = pass
            ) //Object with typed content
            //
            getConfirmUser(userInfo)//Returns boolean
            //
            startActivity(Intent(this,PokeCompanion::class.java))//go to PokeCompanion Section
        }//ApiRest
    }

    private fun getConfirmUser(userData: UserInfo) {

        val call = service.confirmUser(userData)

        call.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>,response: Response<UserInfo>) {
                val confirm = response.body()
                Log.i("TEST","content: $confirm")
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("ERROR","DOESN'T WORK")
                call.cancel()
            }
        })

    }
}