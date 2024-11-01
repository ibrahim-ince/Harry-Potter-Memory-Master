package com.ibo.matchgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sButton = findViewById<Button>(R.id.signupButton)
        sButton.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()

        val lButton = findViewById<Button>(R.id.changeButton)
        lButton.setOnClickListener{
            val email = findViewById<EditText>(R.id.emailEdit2).text.toString()
            val sifre = findViewById<EditText>(R.id.pwEdit2).text.toString()
            if (TextUtils.isEmpty(email)){
                findViewById<EditText>(R.id.emailEdit2).error = "Emailinizi giriniz."
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(sifre)){
                findViewById<EditText>(R.id.pwEdit2).error = "Şifrenizi giriniz."
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, sifre)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Giriş Başarılı", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Giriş Yapılamadı, Bilgileriniz Kontrol Ediniz!", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}