package com.example.sway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePassword : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        var btn_change_password: Button = findViewById<Button>(R.id.btn_change_password)
        btn_change_password.setOnClickListener {
            changePassword()
        }
    }
    private fun changePassword() {
        var et_current_password: EditText = findViewById<EditText>(R.id.et_current_password)
        var et_new_password: EditText = findViewById<EditText>(R.id.et_new_password)
        var et_confirm_password: EditText = findViewById<EditText>(R.id.et_confirm_password)
        if (et_current_password.text.isNotEmpty() &&
            et_new_password.text.isNotEmpty() &&
            et_confirm_password.text.isNotEmpty()
        ) {

            if (et_new_password.text.toString().equals(et_confirm_password.text.toString())) {

                val user = auth.currentUser
                val userid=auth.currentUser.uid
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, et_current_password.text.toString())

// Prompt the user to re-provide their sign-in credentials
                    user?.reauthenticate(credential)
                        ?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Re-Authentication success.", Toast.LENGTH_SHORT).show()
                                user?.updatePassword(et_new_password.text.toString())
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
//                                            var new=et_new_password.text.toString()
//                                            val items = HashMap<String, Any>()
//                                            items.put("password", new)
//                                            GlobalScope.launch(Dispatchers.IO) {
//                                                try {
//                                                    db.collection("users").document(userid)
//                                                        .update(items)
//                                                        .addOnSuccessListener { void: Void? ->
//                                                            Toast.makeText(
//                                                                this@ChangePassword,
//                                                                "Successfully Changed Password",
//                                                                Toast.LENGTH_LONG
//                                                            ).show()
//                                                        }
//                                                        .addOnFailureListener { exception: java.lang.Exception ->
//                                                            Toast.makeText(
//                                                                this@ChangePassword,
//                                                                exception.toString(),
//                                                                Toast.LENGTH_LONG
//                                                            ).show()
//                                                        }
//                                                } catch (e: Exception) {
//                                                    withContext(Dispatchers.Main) {
//                                                        Toast.makeText(
//                                                            this@ChangePassword,
//                                                            e.toString(),
//                                                            Toast.LENGTH_SHORT
//                                                        ).show()
//                                                    }
//                                                }
//                                            }

                                            Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            startActivity(Intent(this, MainActivity::class.java))
                                            finish()
                                        }
                                    }

                            } else {
                                Toast.makeText(this, "Re-Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            } else {
                Toast.makeText(this, "Password mismatching.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }

    }

}