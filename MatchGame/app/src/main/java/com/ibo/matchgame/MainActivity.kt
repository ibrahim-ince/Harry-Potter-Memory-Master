package com.ibo.matchgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var database : FirebaseDatabase? = null
    var databaseRef : DatabaseReference? = null
    var currentUser : FirebaseUser? = null
    lateinit var userV : TextView
    lateinit var lButton : Button
    lateinit var eButton : Button
    lateinit var cButton : Button
    lateinit var s2x2 : Button
    lateinit var s4x4 : Button
    lateinit var s6x6 : Button
    lateinit var s2x2Multi : Button
    lateinit var s4x4Multi : Button
    lateinit var s6x6Multi : Button

    /*
    private fun uploadChs(){
        database = FirebaseDatabase.getInstance()
        databaseRef = database?.reference!!.child("chInf")

        var chList = ArrayList<Characters>()
        chList.add(Characters("Albus Dumbledore", "Gryffindor", 20.0, 2.0, "asd"))
        chList.add(Characters("Rubeus Hagrid", "Gryffindor", 12.0, 2.0, "asd"))
        chList.add(Characters("Minerva McGonagall", "Gryffindor", 13.0, 2.0, "asd"))
        chList.add(Characters("Arthur Weasley", "Gryffindor", 10.0, 2.0, "asd"))
        chList.add(Characters("Sirius Black", "Gryffindor", 18.0, 2.0, "asd"))
        chList.add(Characters("Lily Potter", "Gryffindor", 12.0, 2.0, "asd"))
        chList.add(Characters("Remus Lupin", "Gryffindor", 10.0, 2.0, "asd"))
        chList.add(Characters("Peter Pettigrew", "Gryffindor", 5.0, 2.0, "asd"))
        chList.add(Characters("Harry Potter", "Gryffindor", 10.0, 2.0, "asd"))
        chList.add(Characters("Ron Weasley", "Gryffindor", 8.0, 2.0, "asd"))
        chList.add(Characters("Hermione Granger", "Gryffindor", 10.0, 2.0, "asd"))
        chList.add(Characters("Tom Riddle", "Slytherin", 20.0, 2.0, "asd"))
        chList.add(Characters("Horace Slughorn", "Slytherin", 12.0, 2.0, "asd"))
        chList.add(Characters("Bellatrix Lestrange", "Slytherin", 13.0, 2.0, "asd"))
        chList.add(Characters("Narcissa Malfoy", "Slytherin", 10.0, 2.0, "asd"))
        chList.add(Characters("Andromeda Tonks", "Slytherin", 16.0, 2.0, "asd"))
        chList.add(Characters("Lucius Malfoy", "Slytherin", 12.0, 2.0, "asd"))
        chList.add(Characters("Evan Rosier", "Slytherin", 10.0, 2.0, "asd"))
        chList.add(Characters("Draco Malfoy", "Slytherin", 5.0, 2.0, "asd"))
        chList.add(Characters("Dolores Umbridge", "Slytherin", 10.0, 2.0, "asd"))
        chList.add(Characters("Severus Snape", "Slytherin", 18.0, 2.0, "asd"))
        chList.add(Characters("Leta Lestrange", "Slytherin", 10.0, 2.0, "asd"))
        chList.add(Characters("Rowena Ravenclaw", "Ravenclaw", 20.0, 1.0, "asd"))
        chList.add(Characters("Luna Lovegood", "Ravenclaw", 9.0, 1.0, "asd"))
        chList.add(Characters("Gilderoy Lockhart", "Ravenclaw", 13.0, 1.0, "asd"))
        chList.add(Characters("Filius Flitwick", "Ravenclaw", 10.0, 1.0, "asd"))
        chList.add(Characters("Cho Chang", "Ravenclaw", 11.0, 1.0, "asd"))
        chList.add(Characters("Sybill Trelawney", "Ravenclaw", 14.0, 1.0, "asd"))
        chList.add(Characters("Marcus Belby", "Ravenclaw", 10.0, 1.0, "asd"))
        chList.add(Characters("Myrtle Warren", "Ravenclaw", 5.0, 1.0, "asd"))
        chList.add(Characters("Padma Patil", "Ravenclaw", 10.0, 1.0, "asd"))
        chList.add(Characters("Quirinus Quirrell", "Ravenclaw", 15.0, 1.0, "asd"))
        chList.add(Characters("Garrick Ollivander", "Ravenclaw", 15.0, 1.0, "asd"))
        chList.add(Characters("Helga Hufflepuff", "Hufflepuff", 20.0, 1.0, "asd"))
        chList.add(Characters("Cedric Diggory", "Hufflepuff", 18.0, 1.0, "asd"))
        chList.add(Characters("Nymphadora Tonks", "Hufflepuff", 14.0, 1.0, "asd"))
        chList.add(Characters("Pomona Sprout", "Hufflepuff", 10.0, 1.0, "asd"))
        chList.add(Characters("Newt Scamander", "Hufflepuff", 18.0, 1.0, "asd"))
        chList.add(Characters("Fat Friar", "Hufflepuff", 12.0, 1.0, "asd"))
        chList.add(Characters("Hannah Abbott", "Hufflepuff", 10.0, 1.0, "asd"))
        chList.add(Characters("Ernest Macmillan", "Hufflepuff", 5.0, 1.0, "asd"))
        chList.add(Characters("Leanne", "Hufflepuff", 10.0, 1.0, "asd"))
        chList.add(Characters("Silvanus Kettleburn", "Hufflepuff", 12.0, 1.0, "asd"))
        chList.add(Characters("Ted Lupin", "Hufflepuff", 10.0, 1.0, "asd"))


        var i = 0
        while(i < 44){
            var de = databaseRef?.child(i.toString())
            de?.child("name")?.setValue(chList[i].name)
            de?.child("houseName")?.setValue(chList[i].houseName)
            de?.child("point")?.setValue(chList[i].point.toString())
            de?.child("housePoint")?.setValue(chList[i].housePoint.toString())
            de?.child("imgSrc")?.setValue(chList[i].imgsrc)
            i++
        }
    }
*/

    private fun getInf(){
        userV = findViewById<TextView>(R.id.userView)
        lButton = findViewById<Button>(R.id.lButton)
        eButton = findViewById<Button>(R.id.eButton)
        cButton = findViewById<Button>(R.id.cButton)
        s2x2 = findViewById<Button>(R.id.start2x2)
        s4x4 = findViewById<Button>(R.id.start4x4)
        s6x6 = findViewById<Button>(R.id.start6x6)
        s2x2Multi = findViewById<Button>(R.id.start2x2Multi)
        s4x4Multi = findViewById<Button>(R.id.start4x4Multi)
        s6x6Multi = findViewById<Button>(R.id.start6x6Multi)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        if (currentUser != null){
            database = FirebaseDatabase.getInstance()
            databaseRef = database?.reference!!.child("userInf")
            lButton.isEnabled = false
            eButton.isEnabled = true
            cButton.isEnabled = true
            s2x2.isEnabled = true
            s4x4.isEnabled = true
            s6x6.isEnabled = true
            s2x2Multi.isEnabled = true
            s4x4Multi.isEnabled = true
            s6x6Multi.isEnabled = true
            var userRef = databaseRef?.child(currentUser?.uid!!)
            userRef?.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userV.text = snapshot.child("ad").value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }else{
            lButton.isEnabled = true
            eButton.isEnabled = false
            cButton.isEnabled = false
            s2x2.isEnabled = false
            s4x4.isEnabled = false
            s6x6.isEnabled = false
            s2x2Multi.isEnabled = false
            s4x4Multi.isEnabled = false
            s6x6Multi.isEnabled = false
            userV.text = "Oynamak İçin Lütfen \nGiriş Yapınız."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //uploadChs()

        userV = findViewById(R.id.userView)
        lButton = findViewById(R.id.lButton)
        eButton = findViewById(R.id.eButton)
        cButton = findViewById(R.id.cButton)
        s2x2 = findViewById(R.id.start2x2)
        s4x4 = findViewById(R.id.start4x4)
        s6x6 = findViewById(R.id.start6x6)
        s2x2Multi = findViewById(R.id.start2x2Multi)
        s4x4Multi = findViewById(R.id.start4x4Multi)
        s6x6Multi = findViewById(R.id.start6x6Multi)

        userV.text = "-------"
        lButton.isEnabled = false
        eButton.isEnabled = false
        cButton.isEnabled = false
        s2x2.isEnabled = false
        s4x4.isEnabled = false
        s6x6.isEnabled = false
        s2x2Multi.isEnabled = false
        s4x4Multi.isEnabled = false
        s6x6Multi.isEnabled = false

        getInf()

        auth = FirebaseAuth.getInstance()

        lButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        eButton.setOnClickListener{
            auth.signOut()
            Toast.makeText(this@MainActivity, "Çıkış Yapıldı!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cButton.setOnClickListener{
            startActivity(Intent(this, ChangepwActivity::class.java))
        }

        s2x2.setOnClickListener{
            startActivity(Intent(this, Card2x2Activity::class.java))
        }
        s4x4.setOnClickListener{
            startActivity(Intent(this, Card4x4Activity::class.java))
        }
        s6x6.setOnClickListener{
            startActivity(Intent(this, Card6x6Activity::class.java))
        }

        s2x2Multi.setOnClickListener{
            startActivity(Intent(this, Card2x2MultiActivity::class.java))
        }
        s4x4Multi.setOnClickListener{
            startActivity(Intent(this, Card4x4MultiActivity::class.java))
        }
        s6x6Multi.setOnClickListener{
            startActivity(Intent(this, Card6x6MultiActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        getInf()
    }
}