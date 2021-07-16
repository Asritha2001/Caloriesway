package com.example.sway

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Register : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var firstTimeUser = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        buttonClicks()

    }



    private fun buttonClicks() {
        var next: Button = findViewById<Button>(R.id.next)
        next.setOnClickListener {
            firstTimeUser = true
            createOrLoginUser()
        }
    }
    private fun createOrLoginUser(){
        var reg_name: EditText = findViewById<EditText>(R.id.reg_name)
        var reg_email: EditText = findViewById<EditText>(R.id.reg_email)
        var reg_password: EditText = findViewById<EditText>(R.id.reg_password)
        var reg_conpassword: EditText = findViewById<EditText>(R.id.reg_conpassword)
        var email =reg_email.text.toString()
        var password =reg_password.text.toString()
        var name =reg_name.text.toString()
        var conpassword =reg_conpassword.text.toString()
        val db = FirebaseFirestore.getInstance()

        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && conpassword.isNotEmpty()){
            if (password.equals(conpassword)) {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        if (firstTimeUser) {
                            auth.createUserWithEmailAndPassword(email, password).await()
                            val userid = auth.currentUser.uid
                            val items = HashMap<String, Any>()
                            items.put("name", name)
                            items.put("email", email)
                            val db = FirebaseFirestore.getInstance()
                            db.collection("users").document(userid).set(items)
                                .addOnSuccessListener { void: Void? ->
                                    Toast.makeText(
                                        this@Register,
                                        "Successfully uploaded to the database :)",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener { exception: java.lang.Exception ->
                                    Toast.makeText(
                                        this@Register,
                                        exception.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                        } else {
                            auth.signInWithEmailAndPassword(email, password).await()
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Register, "", Toast.LENGTH_SHORT)
                                .show()
                            val i = Intent(this@Register, bmilog::class.java)
                            startActivity(i)
                            finish()
                        }
                        var next: Button = findViewById<Button>(R.id.next)
                        next.setOnClickListener {
                            val intent2 = Intent(this@Register, bmilog::class.java)
                            startActivity(intent2)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Register, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Passwords doesn't match!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}