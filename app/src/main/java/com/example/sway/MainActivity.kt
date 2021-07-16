package com.example.sway

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        var askforsignUp:TextView = findViewById<Button>(R.id.askforsignUp)
        askforsignUp.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
        var forgotpassword:TextView = findViewById<Button>(R.id.forgot_password)
        forgotpassword.setOnClickListener{
            val intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }
//        var login: Button = findViewById<Button>(R.id.login_button)
//        login.setOnClickListener{
//            val intent4 = Intent(this,Home::class.java)
//            startActivity(intent4)
//        }

        toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        var email:EditText=findViewById(R.id.email)
        var password:EditText=findViewById(R.id.password)
        var saveLoginCheckBox:CheckBox=findViewById(R.id.remember_password)
        var login_button:Button=findViewById(R.id.login_button)
        var loginPreferences: SharedPreferences=getSharedPreferences("loginPrefs", MODE_PRIVATE)
        var loginPrefsEditor: SharedPreferences.Editor=loginPreferences.edit()
        var  saveLogin:Boolean= loginPreferences.getBoolean("saveLogin", false)


        email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.email, 0, 0, 0)
        password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password, 0, 0, 0)


        email.addTextChangedListener(loginTextWatcher)
        password.addTextChangedListener(loginTextWatcher)


        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {

            }

            override fun afterTextChanged(s: Editable)
            {
                if (s.length != 0)
                {
                    var drawable = resources.getDrawable(R.drawable.email) //Your drawable image
                    drawable = DrawableCompat.wrap(drawable!!)
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.colordarkblue))
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                    email.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    email.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.email),
                        null, resources.getDrawable(R.drawable.cancel), null)
                }
                else if (s.length == 0)
                {
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.email,
                        0, 0, 0)
                    var drawable = resources.getDrawable(R.drawable.email) //Your drawable image
                    drawable = DrawableCompat.wrap(drawable!!)
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.colorDefault))
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                    email.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    email.setCompoundDrawablesWithIntrinsicBounds(
                        resources.getDrawable(R.drawable.email),
                        null, null, null
                    )
                }
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
            }

            override fun afterTextChanged(s: Editable)
            {
                if (s.length != 0)
                {
                    var drawable = resources.getDrawable(R.drawable.password)
                    drawable = DrawableCompat.wrap(drawable!!)
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.colordarkblue))
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                    password.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    password.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.password),
                        null, resources.getDrawable(R.drawable.cancel), null)
                }
                else if (s.length == 0)
                {
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password,
                        0, 0, 0)
                    var drawable = resources.getDrawable(R.drawable.password)
                    drawable = DrawableCompat.wrap(drawable!!)
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.colorDefault))
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                    password.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    password.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.password),
                        null, null, null
                    )
                }
            }
        })

        email.setOnTouchListener(View.OnTouchListener { v, event ->

            if (event.action == MotionEvent.ACTION_DOWN)
            {
                if (email.getCompoundDrawables().get(2) != null)
                {
                    if (event.x >= email.getRight() - email.getLeft() -
                        email.getCompoundDrawables().get(2).getBounds().width())
                    {
                        if (email.getText().toString() != "")
                        {
                            email.setText("")
                        }
                    }
                }
            }
            false
        })

        password.setOnTouchListener(View.OnTouchListener { v, event ->

            if (event.action == MotionEvent.ACTION_DOWN)
            {
                if (password.getCompoundDrawables().get(2) != null)
                {
                    if (event.x >= password.getRight() - password.getLeft() -
                        password.getCompoundDrawables().get(2).getBounds().width()
                    )
                    {
                        if (password.getText().toString() != "")
                        {
                            password.setText("")
                        }
                    }
                }
            }
            false
        })

        saveLoginCheckBox.setOnClickListener(View.OnClickListener {

            if (!(saveLoginCheckBox.isSelected)) {
                saveLoginCheckBox.isChecked = true
                saveLoginCheckBox.isSelected = true
            } else {
                saveLoginCheckBox.isChecked = false
                saveLoginCheckBox.isSelected = false
            }
        })
        loggingIn()
    }
    private fun loggingIn() {
        var login: Button = findViewById<Button>(R.id.login_button)
        login.setOnClickListener {
            logs()
        }
    }
    private fun logs(){
        var log_email: EditText = findViewById<EditText>(R.id.email)
        var log_password: EditText = findViewById<EditText>(R.id.password)
        var email =log_email.text.toString()
        var password =log_password.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val i = Intent(this@MainActivity, Home::class.java)
                        startActivity(i)
                        val user = auth.currentUser
                    } else {
                        Toast.makeText(baseContext, "Incorrect Email or Password", Toast.LENGTH_SHORT).show()
                        Log.d("Tag","Incorrect Email or Password")
                    }
                }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
        {
        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
        {
        }
        override fun afterTextChanged(s: Editable)
        {
            var email:EditText=findViewById(R.id.email)
            var password:EditText=findViewById(R.id.password)
            var login_button:Button=findViewById(R.id.login_button)
            val mUsername: String = email.getText().toString().trim()
            val mPassword: String = password.getText().toString().trim()
            val t = !mUsername.isEmpty() && !mPassword.isEmpty()
            if (t)
            {
                login_button.setBackgroundResource(R.color.colordarkblue)
            }
            else
            {
                login_button.setBackgroundResource(R.color.colorwhiteblueshade)
            }

        }
//        fun MainActivity.FragmentMethod() {
//            Toast.makeText(this@MainActivity, "Method called From Fragment", Toast.LENGTH_LONG).show()
//        }
    }
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            val i = Intent(this@MainActivity,Home::class.java)
//            startActivity(i)
//            finish()
//        }
//    }

}