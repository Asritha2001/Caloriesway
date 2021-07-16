package com.example.sway

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat


class bmilog : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var sp:Spinner
    lateinit var selectedage: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmilog)
        val ages = resources.getStringArray(R.array.ages)
        db = FirebaseFirestore.getInstance()
        sp= findViewById<Spinner>(R.id.bmi_age)

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
        auth = FirebaseAuth.getInstance()
        bmilogButton()
        myplan()
    }
    fun myplan(){
        var userid=auth.currentUser.uid
        val numbersMap = mapOf("Breakfast" to "", "Lunch" to "", "Snacks" to "", "Dinner" to "")
        val data : List<Map<String, String>> = listOf(numbersMap ,numbersMap,numbersMap,numbersMap,numbersMap,numbersMap,numbersMap)
        val items = HashMap<String, Any>()
        items.put("sugglist", data)
        val rootRef = FirebaseFirestore.getInstance()
        val ref = rootRef.collection("ownplan").document(userid)
        ref.set(items)
            .addOnSuccessListener { void: Void? ->
                Toast.makeText(this@bmilog, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { exception: java.lang.Exception ->
                Toast.makeText(this@bmilog, exception.toString(), Toast.LENGTH_LONG).show()
            }
        val arr:List<String> = listOf()
        val items1 = HashMap<String, Any>()
        items1.put("daily", arr)
        val ref1=rootRef.collection("dailygraph").document(userid)
        ref1.set(items1)
                .addOnSuccessListener { void: Void? ->
                    Toast.makeText(this@bmilog, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { exception: java.lang.Exception ->
                    Toast.makeText(this@bmilog, exception.toString(), Toast.LENGTH_LONG).show()
                }


    }
    private fun bmilogButton() {
        var signup: Button = findViewById<Button>(R.id.signUp)
        signup.setOnClickListener {
            signningUp()
        }
    }
    private fun signningUp(){
        var bmi_gender_options : RadioGroup=findViewById<RadioGroup>(R.id.bmi_gender_options)
        bmi_gender_options.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
            })
        var signup: Button = findViewById<Button>(R.id.signUp)

        var id: Int = bmi_gender_options.checkedRadioButtonId
        if (id!=-1){ // If any radio button checked from radio group
            val radio:RadioButton = findViewById(id)
//                Toast.makeText(applicationContext,"On button click : ${radio.text}",
//                    Toast.LENGTH_SHORT).show()
        }

        var bmi_height: EditText = findViewById<EditText>(R.id.bmi_height)
        var bmi_weight: EditText = findViewById<EditText>(R.id.bmi_weight)
        var height =bmi_height.text.toString()
        var weight =bmi_weight.text.toString()
        var h1=height.toFloat()
        var w1=weight.toFloat()
        var bmi=(w1*10000)/(h1*h1)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        var x=df.format(bmi)
        x=x.toString()

        var gender=radio_button_click(bmi_gender_options)
        if (height.isNotEmpty() && weight.isNotEmpty() && gender.isNotEmpty() && selectedage.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val userid = auth.currentUser.uid
                    val items = HashMap<String, Any>()
                    items.put("gender", gender)
                    items.put("age", selectedage)
                    items.put("height", height)
                    items.put("weight",weight)
                    items.put("bmi",x)
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users").document(userid).update(items)
                        .addOnSuccessListener { void: Void? ->
                            Toast.makeText(this@bmilog, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener { exception: java.lang.Exception ->
                            Toast.makeText(
                                this@bmilog, exception.toString(), Toast.LENGTH_LONG).show()
                        }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@bmilog, "", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@bmilog, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    var signup: Button = findViewById<Button>(R.id.signUp)
                    signup.setOnClickListener {
                        val intent2 = Intent(this@bmilog, MainActivity::class.java)
                        startActivity(intent2)
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@bmilog, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun radio_button_click(view: View): String{
        var bmi_gender_options : RadioGroup=findViewById<RadioGroup>(R.id.bmi_gender_options)
        val radio: RadioButton = findViewById(bmi_gender_options.checkedRadioButtonId)
        return(radio.text.toString())
    }
}