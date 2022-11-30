package com.example.pokeweb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.pokeweb.models.Companions
import com.example.pokeweb.models.CompanionsResponse
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
        .baseUrl("https://b321-2800-e2-280-a76-2840-f00a-32b5-5052.ngrok.io/api/PokeWeb/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: MyApiService = retrofit.create(MyApiService::class.java)

    //Global Id Pokemon List
    val id = IdCompanionDTO(this)

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
            //Objects with typed content
            val userInfo = UserInfo(
                emailUser = email,
                userPassword = pass
            )
            val userId = Companions(
                emailUser = email
            )
            //
            getConfirmUser(userInfo,userId)
        }
    }

     fun getIdCompanion(userData: Companions) {

        val call = service.requestCompanion(userData)

        call.enqueue(
            object : Callback<CompanionsResponse>
            {
                override fun onResponse(call: Call<CompanionsResponse>,response: Response<CompanionsResponse>) {
                    val confirm = response.body().idCompanion
                    if (confirm != null) {
                        id.idList.addAll(confirm)
                        Log.i("TEST SET ID","content Get Id Confirm: $confirm")
                        Log.i("TEST SET ID","Global Before Start Activity: ${id.idList}")
                        startActivity(Intent(this@MainActivity,PokeCompanion::class.java))//go to PokeCompanion Section
                    }else  {
                        Log.e("TEST ID","NOT RESPONSE: $confirm")
                    }
                }
                override fun onFailure(call: Call<CompanionsResponse>, t: Throwable) {
                    Log.e("ERROR ID","DOESN'T WORK")
                    call.cancel()
                }
            }
        )
    }

     private fun getConfirmUser(userData: UserInfo, userId: Companions) {

        val call = service.confirmUser(userData)

        call.enqueue(
            object : Callback<UserInfoResponse>
            {
            override fun onResponse(call: Call<UserInfoResponse>,response: Response<UserInfoResponse>) {
                val confirm = response.body().confirmation
                Log.i("TEST","content Login Response: $confirm")
                if (confirm != null && confirm == true) {
                    getIdCompanion(userId)
                }else{
                    Log.e("ERROR","SOMETHING WENT WRONG")
                }
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("ERROR","DOESN'T WORK")
                call.cancel()
            }
        })
    }
}