package com.example.sway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //startActivity(intent)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        var bottomNavigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {

                }
                R.id.suggestions -> {
                    val intent1 = Intent(this,Suggestions::class.java)
                    startActivity(intent1)
                    overridePendingTransition(0,0);
                }
                R.id.bmichart -> {
                    val intent2 = Intent(this,Bmichart::class.java)
                    startActivity(intent2)
                    overridePendingTransition(0,0);
                }
                R.id.profile -> {
                    val intent3 = Intent(this,Profile::class.java)
                    startActivity(intent3)
                    overridePendingTransition(0,0);
                }
            }
            true
        }

    }

    override fun onStart() {
        super.onStart()
        home()
    }
    fun home(){
        val userid = auth.currentUser.uid
        var bmi:String="abc"
        var sugg=""
        var sugglist=""
        db.collection("suggestions").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                sugg = document.getString("suggestion").toString()
                sugglist = document.getString("sugglist").toString()
                bmi = document.getString("bmi").toString()
                Log.d("TAG", "......................................................$bmi")
                    if (bmi != "null") {
                        var bmi2 = bmi.toDouble()
                        var data = ""
                        var status = ""
                        var test: TextView = findViewById(R.id.mysugg)
                        if (bmi2 < 18.5) {
                            status = "underweight"
                        } else if (bmi2 in 18.5..24.9) {
                            status = "normal"
                        } else if (bmi2 in 25.0..29.9) {
                            status = "preobesity"
                        } else if (bmi2 in 30.0..34.9) {
                            status = "obesity1"
                        } else if (bmi2 in 35.0..35.9) {
                            status = "obesity2"
                        } else if (bmi2 >= 40) {
                            status = "obesity3"
                        } else {
                            Log.d("TAG", "NO data")
                        }
                        if(sugg == "ownplan"){
                            Log.d("TAG","THis is meeee")
                            status="ownplan"
                            sugg=userid
                            sugglist="sugglist"
                        }
                        val applicationsRef = db.collection(status)
                        val applicationIdRef = applicationsRef.document(sugg)
                        var list = listOf("MONDAY  ", "TUESDAY  ", "WEDNESDAY  ", "THURSDAY  ", "FRIDAY  ", "SATURDAY  ", "SUNDAY  ")
                        applicationIdRef.get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                            if (task.isSuccessful) {
                                val document = task.result
                                if (document!!.exists()) {
                                    val us = document!![sugglist] as List<Map<String, Any>>
                                    for (i in 0..6) {
                                        data += "\n" + list[i]
                                        data += "\n" + "Breakfast - " + us[i]["Breakfast"]
                                        data += "\n" + "Lunch - " + us[i]["Lunch"]
                                        data += "\n" + "Snacks - " + us[i]["Snacks"]
                                        data += "\n" + "Dinner - " + us[i]["Dinner"] + "\n"
                                    }
                                    Log.d("TAG", data)
                                    test.setText(data)
                                }

                            }
                        }
                    } else {
                        var nothing: TextView = findViewById(R.id.nothing)
                        nothing.setVisibility(View.VISIBLE)
                    }

            }
            else{

                Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
            }
        }  .addOnFailureListener { exception ->
            Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()

        }

    }
}