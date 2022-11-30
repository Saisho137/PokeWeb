package com.example.pokeweb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.pokeweb.models.UserInfo
import com.example.pokeweb.models.UserInfoResponse
import com.example.pokeweb.pokeApi.MyApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://e2df-2800-e2-280-a76-c9f-58e7-7165-ce5a.ngrok.io/api/PokeWeb/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: MyApiService = retrofit.create(MyApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //Buttons
        val goSignIn = findViewById<Button>(R.id.btnSignReg)
        val userRegister = findViewById<Button>(R.id.btnRegistReg)
        //EditText
        val emailTb = findViewById<EditText>(R.id.tbEmailReg)//EditText variable
        val passTb = findViewById<EditText>(R.id.tbPassReg)//EditText variable
        val passTbConfirm = findViewById<EditText>(R.id.tbPassConfirmReg)//EditText variable
        //Buttons Functions
        goSignIn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
        //ApiRest
        userRegister.setOnClickListener{
            val email: String = emailTb.text.toString()//Text email String
            val pass: String = passTb.text.toString()//Text password String
            val passConfirm: String = passTbConfirm.text.toString()//Text password 2 String
            //Validations
            if( email.isEmpty() || pass.isEmpty() ){
                emailTb.error = "Email required"
                passTb.error = "Password required"
                return@setOnClickListener }
            //
            if (pass != passConfirm){
                passTb.error = "Passwords Doesn't Match!"
                passTbConfirm.error = "Passwords Doesn't Match!"
                return@setOnClickListener }
            //
            Log.i("TEST","content editText2: $email & $pass")
            //Object with typed content
            val userInfo = UserInfo(
                emailUser = email,
                userPassword = pass
            )
            //
            registerUser(userInfo)
        }
    }//End of OnCreate

    private fun registerUser(userData: UserInfo) {

        val call = service.registerUser(userData)

        call.enqueue(
            object : Callback<UserInfoResponse>
            {
                override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                    val confirm = response.body().confirmation
                    Log.i("TEST","content Response: $confirm")
                    if (confirm != null) {
                        startActivity(Intent(this@Register,MainActivity::class.java))//go to PokeCompanion Section
                    }
                }
                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.e("ERROR","DOESN'T WORK")
                    call.cancel()
                }
            })
    }
}