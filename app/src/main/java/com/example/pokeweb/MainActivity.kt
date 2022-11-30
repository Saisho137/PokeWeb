package com.example.pokeweb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.pokeweb.models.UserInfo
import com.example.pokeweb.models.UserInfoResponse
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
        val passTb = findViewById<EditText>(R.id.tbPassR)//EditText variable
        //Buttons Functions
        goRegister.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
        }
        //ApiRest
        goPokeCompanion.setOnClickListener{
            val email: String = emailTb.text.toString()//Text email String
            val pass: String = passTb.text.toString()//Text password String
            //Validations
            if( email.isEmpty() || pass.isEmpty() ){
                emailTb.error = "Email required"
                passTb.error = "Password required"
                return@setOnClickListener }
            //
            Log.i("TEST","content editText2: $email & $pass")
            //Object with typed content
            val userInfo = UserInfo(
                emailUser = email,
                userPassword = pass
            )
            //
            getConfirmUser(userInfo)
        }
    }

    private fun getConfirmUser(userData: UserInfo) {

        val call = service.confirmUser(userData)

        call.enqueue(
            object : Callback<UserInfoResponse>
            {
            override fun onResponse(call: Call<UserInfoResponse>,response: Response<UserInfoResponse>) {
                val confirm = response.body().confirmation
                Log.i("TEST","content Response: $confirm")
                if (confirm != null) {
                    startActivity(Intent(this@MainActivity,PokeCompanion::class.java))//go to PokeCompanion Section
                }
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("ERROR","DOESN'T WORK")
                call.cancel()
            }
        })
    }
}