package com.example.sway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Feedback : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        var save: Button = findViewById<Button>(R.id.save_button)
        save.setOnClickListener {
            sendFeedback()
        }

    }
    fun sendFeedback(){
        val userid = auth.currentUser.uid
        val items = HashMap<String, Any>()
        var feedback: EditText = findViewById<EditText>(R.id.feedback)
        var feedback1 =feedback.text.toString()
        items.put("feedback", feedback1)
        db.collection("feedback").document(userid).set(items)
            .addOnSuccessListener { void: Void? ->
                Toast.makeText(this@Feedback, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                val intent2 = Intent(this@Feedback, Profile::class.java)
                startActivity(intent2)
            }.addOnFailureListener { exception: java.lang.Exception ->
                Toast.makeText(this@Feedback, exception.toString(), Toast.LENGTH_LONG).show()
            }

    }
}