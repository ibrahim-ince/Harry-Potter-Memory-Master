package com.ibo.matchgame

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class Card2x2Activity : AppCompatActivity() {
    var cardCount = 4 // yerdeki kart sayisi

    var database : FirebaseDatabase? = null
    var databaseRef : DatabaseReference? = null

    var chList = ArrayList<Characters>()
    var cardList = ArrayList<Characters>()
    var chToCardList = ArrayList<Characters>()
    var buttons = ArrayList<ImageButton>()
    var flippedViews = ArrayList<View>()
    var flippedCount = 0
    var muted = false
    lateinit var muteButton : ImageButton
    lateinit var timerView: TextView

    var card1 : Characters? = null
    var card2 : Characters? = null
    var cardTag = 0

    var mediaPlayer : MediaPlayer? = null
    var actionPlayer : MediaPlayer? = null

    lateinit var tV1 : TextView
    lateinit var tV2 : TextView
    lateinit var tV3 : TextView
    lateinit var scoreView : TextView
    lateinit var shuffleButton : Button

    lateinit var imgBytes : ByteArray
    lateinit var decodedImg : Bitmap
    lateinit var drwImg : Drawable

    var i = 0
    var j = 0
    var temp = 0.0

    private fun addCharacters(){
        shuffleButton = findViewById(R.id.shuffleButton)
        database = FirebaseDatabase.getInstance()
        databaseRef = database?.reference!!.child("chInf")
        i = 0
        j = 0
        while (i < 44){
            var cardRef = databaseRef?.child(i.toString())
            cardRef?.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    chList.add(Characters(snapshot.child("name").value.toString(), snapshot.child("houseName").value.toString(), snapshot.child("point").value.toString().toDouble(), snapshot.child("housePoint").value.toString().toDouble(), snapshot.child("imgSrc").value.toString()))
                    println("${j++}. ch ${snapshot.child("name").value.toString()} indirildi")
                    if (j == 43){
                        shuffleButton.text = "Kartları Karıştır"
                        shuffleButton.isEnabled = true
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            i++
        }
    }

    private fun addButtons(){
        buttons.add(findViewById(R.id.imageButton0))
        buttons.add(findViewById(R.id.imageButton1))
        buttons.add(findViewById(R.id.imageButton2))
        buttons.add(findViewById(R.id.imageButton3))
    }

    private fun selectCharacters(){
        i = 0
        while(i < cardCount/2) {
            j = (0..(chList.size)-1).random()
            // println("random sayi: ${j}")
            chToCardList.add(chList[j])
            chToCardList.add(chList[j])
            // println("chListten 2 tane eklendi: ${chList[j].name}")
            chList.removeAt(j)
            // println("silindi, kalan size: ${chList.size}")
            i++
        }
    }

    private fun shuffleCards(){
        i = 0
        while(i < cardCount) {
            j = (0..(chToCardList.size)-1).random()
            // println("random sayi: ${j}")
            cardList.add(chToCardList[j])
            // println("chCardListten eklendi: ${chToCardList[j].name}")
            chToCardList.removeAt(j)
            // println("silindi, kalan size: ${chToCardList.size}")
            i++
        }
    }

    private fun printCards(){
        i = 0
        while (i < cardList.size){
            println("${cardList[i].name} | ${cardList[i+1].name}")
            i += 2
        }
    }

    var timerStarted = false
    private lateinit var timer: CountDownTimer
    private fun startTimer(){
        timerView = findViewById(R.id.timerView)
        timer = object: CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerView.text = (timerView.text.toString().toInt() - 1).toString()
            }

            override fun onFinish() {
                if (!muted){
                    gg()
                }
                val intent = Intent(applicationContext, GameoverActivity::class.java)
                intent.putExtra("Score", findViewById<TextView>(R.id.scoreView).text.toString())
                intent.putExtra("Mode", 2)
                intent.putExtra("Win", false)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
        timerStarted = true
    }

    private fun playMusic(){
        mediaPlayer = MediaPlayer.create(this, R.raw.maintheme)
        mediaPlayer?.start()
    }

    private fun muteMusic(){
        mediaPlayer?.setVolume(0F,0F)
        muteButton.setBackgroundResource(R.drawable.muted)
        muted = true
    }

    private fun unmuteMusic(){
        mediaPlayer?.setVolume(1F,1F)
        muteButton.setBackgroundResource(R.drawable.unmuted)
        muted = false
    }

    private fun stopMusic(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun correct(){
        actionPlayer = MediaPlayer.create(this, R.raw.correct)
        actionPlayer?.start()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                actionPlayer?.stop()
                actionPlayer?.release()
                actionPlayer = null
            }, 1000)
    }

    private fun cong(){
        actionPlayer = MediaPlayer.create(this, R.raw.cong)
        actionPlayer?.start()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                actionPlayer?.stop()
                actionPlayer?.release()
                actionPlayer = null
            }, 6000)
    }

    private fun gg(){
        actionPlayer = MediaPlayer.create(this, R.raw.gg)
        actionPlayer?.start()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                actionPlayer?.stop()
                actionPlayer?.release()
                actionPlayer = null
            }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card2x2)

        playMusic()
        muteButton = findViewById(R.id.muteButton)
        muteButton.setOnClickListener{
            if (muted){
                unmuteMusic()
            }else{
                muteMusic()
            }
        }

        // buttons
        addButtons()

        i = 0
        while (i < cardCount){
            buttons[i].isClickable = false
            buttons[i].setBackgroundDrawable(null)
            i++
        }

        // tum characterlerin olusturulmasi ve chListesine eklenmesi(chList)
        addCharacters()

        shuffleButton = findViewById(R.id.shuffleButton)
        shuffleButton.isEnabled = false
        shuffleButton.text = "Veriler İndiriliyor"
        shuffleButton.setOnClickListener{
            // oynanacak cardlar icin chleri secme
            selectCharacters()

            // TEST secilen kartlar
            /*
            i = 0
            while (i<chToCardList.size){
                println(chToCardList[i].name)
                i++
            }
            println("chCardListSize: ${chToCardList.size}")
            TEST */

            // cardlari random dagitma
            shuffleCards()

            // cardlari koy
            i = 0
            while (i < cardCount){
                buttons[i].setClickable(true)
                buttons[i].setBackgroundResource(R.drawable.backside)
                i++
            }

            timerView = findViewById(R.id.timerView)
            timerView.text = "46"

            // timerin baslatilmasi
            startTimer()

            shuffleButton.isEnabled = false

            printCards()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timerStarted){
            timer.cancel()
        }
        stopMusic()
    }

    fun cardClick(view : View){
        tV1 = findViewById(R.id.textView1)
        tV2 = findViewById(R.id.textView2)
        tV3 = findViewById(R.id.textView3)
        scoreView = findViewById(R.id.scoreView)

        if(flippedCount == 0){
            view.isClickable = false

            cardTag = view.getTag().toString().toInt()
            card1 = cardList[cardTag]
            // base64ten img cevirimi
            imgBytes = Base64.decode(card1!!.imgsrc, Base64.DEFAULT)
            decodedImg = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
            drwImg = BitmapDrawable(decodedImg)
            view.setBackgroundDrawable(drwImg)

            flippedViews.add(view)
            flippedCount++
            tV1.text = card1!!.name
        }else if(flippedCount == 1){
            cardTag = view.getTag().toString().toInt()
            card2 = cardList[cardTag]
            // base64ten img cevirimi
            imgBytes = Base64.decode(card2!!.imgsrc, Base64.DEFAULT)
            decodedImg = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
            drwImg = BitmapDrawable(decodedImg)
            view.setBackgroundDrawable(drwImg)

            tV2.text = card2!!.name
            i = 0
            while (i < cardCount){
                buttons[i].isClickable = false
                i++
            }
            timerView = findViewById(R.id.timerView)
            if(card1!!.name.equals(card2!!.name)){
                tV3.text = "Dogru"

                if (!muted){
                    if(cardCount != 2){
                        correct()
                    }else{
                        cong()
                    }
                }

                temp = (2.0 * card1!!.point * card1!!.housePoint) * (timerView.text.toString().toInt() / 10.0)
                scoreView.text = (scoreView.text.toString().toInt() + temp).toInt().toString()
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        if(cardCount == 2){
                            val intent = Intent(applicationContext, GameoverActivity::class.java)
                            intent.putExtra("Score", scoreView.text.toString())
                            intent.putExtra("Mode", 2)
                            intent.putExtra("Win", true)
                            startActivity(intent)
                            finish()
                        }else{
                            tV1.text = "-"
                            tV2.text = "-"
                            tV3.text = "-"
                            view.setBackgroundDrawable(null)
                            flippedViews[0].setBackgroundDrawable(null)
                            buttons.remove(view as ImageButton)
                            buttons.remove(flippedViews[0] as ImageButton)
                            cardCount = cardCount - 2
                            flippedViews.clear()
                            flippedCount = 0
                            i = 0
                            while (i < cardCount){
                                buttons[i].isClickable = true
                                i++
                            }
                        }
                    }, 1000)
            }else{
                tV3.text = "Yanlis"

                if (card1!!.houseName.equals(card2!!.houseName)){
                    temp = ((card1!!.point + card2!!.point) / card1!!.housePoint) * ((45.0 - timerView.text.toString().toInt()) / 10.0)
                    scoreView.text = (scoreView.text.toString().toInt() - temp).toInt().toString()
                }else{
                    temp = (((card1!!.point + card2!!.point) / 2.0) * card1!!.housePoint * card2!!.housePoint) * ((45.0 - timerView.text.toString().toInt()) / 10.0)
                    scoreView.text = (scoreView.text.toString().toInt() - temp).toInt().toString()
                }

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        tV1.text = "-"
                        tV2.text = "-"
                        tV3.text = "-"
                        view.setBackgroundResource(R.drawable.backside)
                        flippedViews[0].setBackgroundResource(R.drawable.backside)
                        flippedViews.clear()
                        flippedCount = 0
                        i = 0
                        while (i < cardCount){
                            buttons[i].isClickable = true
                            i++
                        }
                    }, 1000)
            }
        }
    }
}