package com.ibo.matchgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChangepwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepw)

        val changeButton = findViewById<Button>(R.id.changeButton)
        changeButton.text = "Bilgileriniz Kontrol Ediliyor!"
        changeButton.isEnabled = false

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database?.reference!!.child("userInf")
        val currentUserDB = currentUser?.let { it -> databaseRef?.child(it.uid) }
        var temp1 = ""
        var temp2 = ""
        var j = 0
        currentUserDB?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                temp1 = snapshot.child("ad").value.toString()
                temp2 = snapshot.child("email").value.toString()
                println("${j++}. ${snapshot.child("ad").value.toString()}")
                if (j == 1){
                    changeButton.text = "Güncelle"
                    changeButton.isEnabled = true
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        changeButton.setOnClickListener{
            val newPw = findViewById<EditText>(R.id.newpwEdit).text.toString()
            currentUser!!.updatePassword(newPw).addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUserDB?.removeValue()
                    currentUserDB?.child("ad")?.setValue(temp1)
                    currentUserDB?.child("email")?.setValue(temp2)
                    currentUserDB?.child("sifre")?.setValue(newPw)
                    Toast.makeText(this@ChangepwActivity, "Şifre Değiştirildi", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@ChangepwActivity, "Şifre Değiştirilemedi!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}