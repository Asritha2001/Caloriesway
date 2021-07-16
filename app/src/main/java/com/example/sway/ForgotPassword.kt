package com.example.sway

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth


class ForgotPassword : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        var sendmail: TextView = findViewById<Button>(R.id.forgot_button)
        sendmail.setOnClickListener {
            var email: EditText = findViewById(R.id.email)
            var mail = email.text.toString()
            Log.d("TAG","................................................"+mail)
            auth= FirebaseAuth.getInstance()
            FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "Email sent to $mail")
                    }
                }
//                auth!!.sendPasswordResetEmail(mail)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this@ForgotPassword, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
//                        Log.d("TAG","Success")
//                    } else {
//                        Toast.makeText(
//                            this@ForgotPassword,
//                            "Fail to send reset password email!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.d("TAG","Fail")
//                    }
//
//
//                }

        }
    }
}