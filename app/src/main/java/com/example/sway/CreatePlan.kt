package com.example.sway

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class CreatePlan : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_plan)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        getInfo()
        var save: Button =findViewById(R.id.save_but)
        save.setOnClickListener(){
            updateInfo()
        }


    }
    fun getInfo(){
        val userid = auth.currentUser.uid
        var b1: EditText =findViewById(R.id.breakfast_1)
        var l1: EditText =findViewById(R.id.lunch_1)
        var s1: EditText =findViewById(R.id.snacks_1)
        var d1: EditText =findViewById(R.id.dinner_1)

        var b2: EditText =findViewById(R.id.breakfast_2)
        var l2: EditText =findViewById(R.id.lunch_2)
        var s2: EditText =findViewById(R.id.snacks_2)
        var d2: EditText =findViewById(R.id.dinner_2)

        var b3: EditText =findViewById(R.id.breakfast_3)
        var l3: EditText =findViewById(R.id.lunch_3)
        var s3: EditText =findViewById(R.id.snacks_3)
        var d3: EditText =findViewById(R.id.dinner_3)

        var b4: EditText =findViewById(R.id.breakfast_4)
        var l4: EditText =findViewById(R.id.lunch_4)
        var s4: EditText =findViewById(R.id.snacks_4)
        var d4: EditText =findViewById(R.id.dinner_4)

        var b5: EditText =findViewById(R.id.breakfast_5)
        var l5: EditText =findViewById(R.id.lunch_5)
        var s5: EditText =findViewById(R.id.snacks_5)
        var d5: EditText =findViewById(R.id.dinner_5)

        var b6: EditText =findViewById(R.id.breakfast_6)
        var l6: EditText =findViewById(R.id.lunch_6)
        var s6: EditText =findViewById(R.id.snacks_6)
        var d6: EditText =findViewById(R.id.dinner_6)

        var b7: EditText =findViewById(R.id.breakfast_7)
        var l7: EditText =findViewById(R.id.lunch_7)
        var s7: EditText =findViewById(R.id.snacks_7)
        var d7: EditText =findViewById(R.id.dinner_7)

//        var b1 :String; var b2 :String; var b3 :String; var b4 :String;var b5 :String; var b6 :String;var b7 :String;
//        var l1 :String; var l2 :String; var l3 :String; var l4 :String;var l5 :String; var l6 :String;var l7 :String;
//        var s1 :String; var s2 :String; var s3 :String; var s4 :String;var s5 :String; var s6 :String;var s7 :String;
//        var d1 :String; var d2 :String; var d3 :String; var d4 :String;var d5 :String; var d6 :String;var d7 :String;

        var b =arrayOf("","","","","","","") ;var l=arrayOf("","","","","","","");var s=arrayOf("","","","","","","");var d=arrayOf("","","","","","","")

        val applicationsRef = db.collection("ownplan")
        val applicationIdRef = applicationsRef.document(userid)
        applicationIdRef.get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val us = document!!["sugglist"] as List<Map<String, Any>>
                    for (i in 0..6) {
                        b[i]=  us[i]["Breakfast"].toString()
                        l[i] =  us[i]["Lunch"].toString()
                        s[i] =  us[i]["Snacks"].toString()
                        d[i] =  us[i]["Dinner"].toString()
                    }
                    b1.setText(b[0]);b2.setText(b[1]);b3.setText(b[2]);b4.setText(b[3]);b5.setText(b[4]);b6.setText(b[5]);b7.setText(b[6])
                    l1.setText(l[0]);l2.setText(l[1]);l3.setText(l[2]);l4.setText(l[3]);l5.setText(l[4]);l6.setText(l[5]);l7.setText(l[6])
                    s1.setText(s[0]);s2.setText(s[1]);s3.setText(s[2]);s4.setText(s[3]);s5.setText(s[4]);s6.setText(s[5]);s7.setText(s[6])
                    d1.setText(d[0]);d2.setText(d[1]);d3.setText(d[2]);d4.setText(d[3]);d5.setText(d[4]);d6.setText(d[5]);d7.setText(d[6])

                }

            }
        }
    }
    fun updateInfo(){
        val userid = auth.currentUser.uid
        var b1: EditText =findViewById(R.id.breakfast_1)
        var l1: EditText =findViewById(R.id.lunch_1)
        var s1: EditText =findViewById(R.id.snacks_1)
        var d1: EditText =findViewById(R.id.dinner_1)

        var b2: EditText =findViewById(R.id.breakfast_2)
        var l2: EditText =findViewById(R.id.lunch_2)
        var s2: EditText =findViewById(R.id.snacks_2)
        var d2: EditText =findViewById(R.id.dinner_2)

        var b3: EditText =findViewById(R.id.breakfast_3)
        var l3: EditText =findViewById(R.id.lunch_3)
        var s3: EditText =findViewById(R.id.snacks_3)
        var d3: EditText =findViewById(R.id.dinner_3)

        var b4: EditText =findViewById(R.id.breakfast_4)
        var l4: EditText =findViewById(R.id.lunch_4)
        var s4: EditText =findViewById(R.id.snacks_4)
        var d4: EditText =findViewById(R.id.dinner_4)

        var b5: EditText =findViewById(R.id.breakfast_5)
        var l5: EditText =findViewById(R.id.lunch_5)
        var s5: EditText =findViewById(R.id.snacks_5)
        var d5: EditText =findViewById(R.id.dinner_5)

        var b6: EditText =findViewById(R.id.breakfast_6)
        var l6: EditText =findViewById(R.id.lunch_6)
        var s6: EditText =findViewById(R.id.snacks_6)
        var d6: EditText =findViewById(R.id.dinner_6)

        var b7: EditText =findViewById(R.id.breakfast_7)
        var l7: EditText =findViewById(R.id.lunch_7)
        var s7: EditText =findViewById(R.id.snacks_7)
        var d7: EditText =findViewById(R.id.dinner_7)

        var b11 =b1.text.toString();var b12 =b2.text.toString();var b13 =b3.text.toString();var b14=b4.text.toString();var b15 =b5.text.toString();var b16 =b6.text.toString();var b17 =b7.text.toString()
        var l11 =l1.text.toString();var l12 =l2.text.toString();var l13 =l3.text.toString();var l14=l4.text.toString();var l15 =l5.text.toString();var l16 =l6.text.toString();var l17 =l7.text.toString()
        var s11 =s1.text.toString();var s12 =s2.text.toString();var s13 =s3.text.toString();var s14=s4.text.toString();var s15 =s5.text.toString();var s16 =s6.text.toString();var s17 =s7.text.toString()
        var d11 =d1.text.toString();var d12 =d2.text.toString();var d13 =d3.text.toString();var d14=d4.text.toString();var d15 =d5.text.toString();var d16 =d6.text.toString();var d17 =d7.text.toString()

        val map1 = mapOf("Breakfast" to b11, "Lunch" to l11, "Snacks" to s11, "Dinner" to d11)
        val map2 = mapOf("Breakfast" to b12, "Lunch" to l12, "Snacks" to s12, "Dinner" to d12)
        val map3 = mapOf("Breakfast" to b13, "Lunch" to l13, "Snacks" to s13, "Dinner" to d13)
        val map4 = mapOf("Breakfast" to b14, "Lunch" to l14, "Snacks" to s14, "Dinner" to d14)
        val map5 = mapOf("Breakfast" to b15, "Lunch" to l15, "Snacks" to s15, "Dinner" to d15)
        val map6 = mapOf("Breakfast" to b16, "Lunch" to l16, "Snacks" to s16, "Dinner" to d16)
        val map7 = mapOf("Breakfast" to b17, "Lunch" to l17, "Snacks" to s17, "Dinner" to d17)

        val data : List<Map<String, String>> = listOf(map1 ,map2,map3,map4,map5,map6,map7)

        val items = HashMap<String, Any>()
        items.put("sugglist", data)
        val rootRef = FirebaseFirestore.getInstance()
        val ref = rootRef.collection("ownplan").document(userid)
        ref.update(items)
                .addOnSuccessListener { void: Void? ->
                    Toast.makeText(this@CreatePlan, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { exception: java.lang.Exception ->
                    Toast.makeText(this@CreatePlan, exception.toString(), Toast.LENGTH_LONG).show()
                }
        val items1 = HashMap<String, Any>()
        db.collection("users").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                var bmi = document.getString("bmi").toString()
                items1.put("suggestion", "ownplan")
                items1.put("sugglist","sugglist")
                items1.put("bmi",bmi)
                db.collection("suggestions").document(userid).set(items1)
                        .addOnSuccessListener { void: Void? ->
                            Toast.makeText(this@CreatePlan, "Successfully Added :)", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener { exception: java.lang.Exception ->
                            Toast.makeText(this@CreatePlan, exception.toString(), Toast.LENGTH_LONG).show()
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