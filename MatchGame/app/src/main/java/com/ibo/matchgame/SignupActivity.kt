package com.ibo.matchgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var auth :FirebaseAuth
    var database :FirebaseDatabase? = null
    var databaseRef :DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // databaselere erişim
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // ağaç seçimi
        databaseRef = database?.reference!!.child("userInf")

        val sButton = findViewById<Button>(R.id.sButton)
        sButton.setOnClickListener{
            val ad = findViewById<EditText>(R.id.adEdit).text.toString()
            val email = findViewById<EditText>(R.id.emailEdit2).text.toString()
            val sifre = findViewById<EditText>(R.id.pwEdit2).text.toString()

            // input kontrolü
            if (TextUtils.isEmpty(ad)){
                findViewById<EditText>(R.id.adEdit).error = "Adınızı giriniz."
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)){
                findViewById<EditText>(R.id.emailEdit2).error = "Emailinizi giriniz."
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(sifre)){
                findViewById<EditText>(R.id.pwEdit2).error = "Şifrenizi giriniz."
                return@setOnClickListener
            }
            // kayıt etme
            auth.createUserWithEmailAndPassword(email, sifre)
                .addOnCompleteListener(this){ // kayıt başarılı ise yapılacak işlemler
                    if(it.isSuccessful){
                        var curUser = auth.currentUser
                        var curUserDB = curUser?.let { it1 -> databaseRef?.child(it1.uid) }
                        curUserDB?.child("ad")?.setValue(ad)
                        curUserDB?.child("email")?.setValue(email)
                        curUserDB?.child("sifre")?.setValue(sifre)
                        Toast.makeText(this, "Kayıt İşlemi Başarılı, Giriş Yapıldı!", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Kayıt İşlemi Yapılamadı!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}