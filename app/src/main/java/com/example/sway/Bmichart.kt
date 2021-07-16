package com.example.sway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat

class Bmichart : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var sp:Spinner
    lateinit var selectedage: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmichart)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val ages = resources.getStringArray(R.array.ages)
        sp= findViewById<Spinner>(R.id.bmihome_change_age)

        if (sp != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ages)
            sp.adapter = adapter
            sp.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedage=ages[position].toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
        var save:Button=findViewById(R.id.save)
        save.setOnClickListener(){
            updateInfo()
        }

        var bottomNavigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.bmichart
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent1 = Intent(this,Home::class.java)
                    startActivity(intent1)
                    overridePendingTransition(0,0);

                }
                R.id.suggestions -> {
                    val intent2 = Intent(this,Suggestions::class.java)
                    startActivity(intent2)
                    overridePendingTransition(0,0);
                }
                R.id.bmichart -> {

                }
                R.id.profile -> {
                    val intent3 = Intent(this,Profile::class.java)
                    startActivity(intent3)
                    overridePendingTransition(0,0);
                }

            }
            true
        }
        bmi_info()
    }

    fun bmi_info() {
        //Toast.makeText(this@Bmichart, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
        val userid = auth.currentUser.uid
        var bmihome_gender: TextView =findViewById(R.id.bmihome_gender_options)
        var bmihome_age: TextView =findViewById(R.id.bmihome_age)
        val bmihome_change_age_: Spinner = findViewById(R.id.bmihome_change_age)
        var bmihome_height: EditText =findViewById(R.id.bmihome_height)
        var bmihome_weight: EditText =findViewById(R.id.bmihome_weight)
        var bmihome_bmi: TextView =findViewById(R.id.bmihome_bmi)
        var age1:String
        var height1:String
        var weight1:String


        db.collection("users").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists","DocumentSnapshot Data:${document.data}")
                bmihome_gender.text=document.getString("gender").toString()
                bmihome_age.text=document.getString("age").toString()
                height1=document.getString("height").toString()
                weight1=document.getString("weight").toString()
                bmihome_height.setText(height1)
                bmihome_weight.setText(weight1)
                bmihome_bmi.text=document.getString("bmi").toString()
            }
            else{
                Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
            }
        }  .addOnFailureListener { exception ->
            Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()

        }
    }
    fun updateInfo(){
        var bmi_height: EditText = findViewById<EditText>(R.id.bmihome_height)
        var bmi_weight: EditText = findViewById<EditText>(R.id.bmihome_weight)
        var height =bmi_height.text.toString()
        var weight =bmi_weight.text.toString()
        var h1=height.toFloat()
        var w1=weight.toFloat()
        var bmi=(w1*10000)/(h1*h1)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        var x=df.format(bmi)
        x=x.toString()
        if (height.isNotEmpty() && weight.isNotEmpty()  && selectedage.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val userid = auth.currentUser.uid
                    val items = HashMap<String, Any>()
                    items.put("age", selectedage)
                    items.put("height", height)
                    items.put("weight",weight)
                    items.put("bmi",x)
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users").document(userid).update(items)
                            .addOnSuccessListener { void: Void? ->
                                Toast.makeText(this@Bmichart, "Successfully updated data", Toast.LENGTH_LONG).show()
                            }.addOnFailureListener { exception: java.lang.Exception ->
                                Toast.makeText(
                                        this@Bmichart, exception.toString(), Toast.LENGTH_LONG).show()
                            }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Bmichart, "", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@Bmichart, Bmichart::class.java)
                        startActivity(i)
                        finish()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Bmichart, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}