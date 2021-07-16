package com.example.sway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class Suggestions : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        var bottomNavigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.suggestions
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent1 = Intent(this,Home::class.java)
                    startActivity(intent1)
                    overridePendingTransition(0,0);

                }
                R.id.suggestions -> {

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
        var sugg1: Button =findViewById(R.id.sugg1_but)
        var sugg2: Button =findViewById(R.id.sugg2_but)
        var sugg3: Button =findViewById(R.id.sugg3_but)
        var create_sugg :Button =findViewById<Button>(R.id.create_sugg)
        var apply: Button =findViewById(R.id.apply)

        create_sugg.setOnClickListener(){
            val intent2 = Intent(this@Suggestions, CreatePlan::class.java)
            startActivity(intent2)

        }

        sugg1.setOnClickListener(){
            sugg("suggestion1","sugg1list")
            apply.setVisibility(View.VISIBLE)
            apply.setOnClickListener(){
                val userid = auth.currentUser.uid
                val items = HashMap<String, Any>()
                db.collection("users").document(userid).get().addOnSuccessListener { document ->
                    if (document != null) {
                        var bmi = document.getString("bmi").toString()
                        items.put("suggestion", "suggestion1")
                        items.put("sugglist","sugg1list")
                        items.put("bmi",bmi)
                        db.collection("suggestions").document(userid).set(items)
                                .addOnSuccessListener { void: Void? ->
                                    Toast.makeText(this@Suggestions, "Successfully Added :)", Toast.LENGTH_LONG).show()
                                }.addOnFailureListener { exception: java.lang.Exception ->
                                    Toast.makeText(this@Suggestions, exception.toString(), Toast.LENGTH_LONG).show()
                                }

                    }
                    else{
                        Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        sugg2.setOnClickListener(){
            sugg("suggestion2","sugg2list")
            apply.setVisibility(View.VISIBLE)
            apply.setOnClickListener(){
                val userid = auth.currentUser.uid
                val items = HashMap<String, Any>()
                db.collection("users").document(userid).get().addOnSuccessListener { document ->
                    if (document != null) {
                        var bmi = document.getString("bmi").toString()
                        items.put("suggestion", "suggestion2")
                        items.put("sugglist","sugg2list")
                        items.put("bmi",bmi)
                        db.collection("suggestions").document(userid).set(items)
                                .addOnSuccessListener { void: Void? ->
                                    Toast.makeText(this@Suggestions, "Successfully Added :)", Toast.LENGTH_LONG).show()
                                }.addOnFailureListener { exception: java.lang.Exception ->
                                    Toast.makeText(this@Suggestions, exception.toString(), Toast.LENGTH_LONG).show()
                                }

                    }
                    else{
                        Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        sugg3.setOnClickListener(){
            sugg("suggestion3","sugg3list")
            apply.setVisibility(View.VISIBLE)
            apply.setOnClickListener(){
                val userid = auth.currentUser.uid
                val items = HashMap<String, Any>()
                db.collection("users").document(userid).get().addOnSuccessListener { document ->
                    if (document != null) {
                        var bmi = document.getString("bmi").toString()
                        items.put("suggestion", "suggestion3")
                        items.put("sugglist","sugg3list")
                        items.put("bmi",bmi)
                        db.collection("suggestions").document(userid).set(items)
                                .addOnSuccessListener { void: Void? ->
                                    Toast.makeText(this@Suggestions, "Successfully Added :)", Toast.LENGTH_LONG).show()
                                }.addOnFailureListener { exception: java.lang.Exception ->
                                    Toast.makeText(this@Suggestions, exception.toString(), Toast.LENGTH_LONG).show()
                                }

                    }
                    else{
                        Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    fun sugg(sugg:String,sugglist:String) {
        val userid = auth.currentUser.uid
        var bmi=""

        db.collection("users").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists","DocumentSnapshot Data:${document.data}")
                bmi=document.getString("bmi").toString()
                var bmi2=bmi.toDouble()
                var data = ""
                //Log.d("TAG", "...........................................................................$bmi")
                var status=""
                var test: TextView = findViewById(R.id.test)
                if (bmi2 < 18.5) {
                    status = "underweight"
                }
                else if (bmi2 in 18.5..24.9) {
                    status = "normal"
                }
                else if (bmi2 in 25.0..29.9 ) {
                    status = "preobesity"
                }
                else if (bmi2 in 30.0..34.9 ) {
                    status = "obesity1"
                }
                else if (bmi2 in 35.0..35.9 ) {
                    status = "obesity2"
                }
                else if (bmi2 >= 40 ) {
                    status = "obesity3"
                }
                else{
                    Log.d("TAG", "NO data")
                }
                    val applicationsRef = db.collection(status)
                    val applicationIdRef = applicationsRef.document(sugg)
                    var list = listOf("MONDAY  ","TUESDAY  ","WEDNESDAY  ","THURSDAY  ","FRIDAY  ","SATURDAY  ","SUNDAY  ")
                    applicationIdRef.get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document!!.exists()) {
                                val us = document!![sugglist] as List<Map<String, Any>>
                                for(i in 0..6) {
                                    data += "\n" + list[i]
                                    data += "\n" + "Breakfast - " +us[i]["Breakfast"]
                                    data += "\n" + "Lunch - " +us[i]["Lunch"]
                                    data += "\n" + "Snacks - "+us[i]["Snacks"]
                                    data += "\n" + "Dinner - "+us[i]["Dinner"] +"\n"
                                }
                                test.setText(data)
                            }
                        }
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