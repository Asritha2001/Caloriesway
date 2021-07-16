package com.example.sway

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class Profile : AppCompatActivity() {
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 10000 //Set after 5 seconds from the current time.
    private var mNotified = false
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        var bottomNavigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.profile
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
                    val intent3 = Intent(this,Bmichart::class.java)
                    startActivity(intent3)
                    overridePendingTransition(0,0);

                }
                R.id.profile -> {

                }

            }
            true
        }
        var notify: Switch =findViewById(R.id.profile_notify)
        notify.setOnCheckedChangeListener { buttonView, isChecked ->
            setNotification(mNotificationTime, this@Profile)
        }
        var graph: Button =findViewById(R.id.profile_graph)
        graph.setOnClickListener {
            val intent6 = Intent(this,Graph::class.java)
            startActivity(intent6)
        }

        var feedback: Button =findViewById(R.id.profile_feedback)
        feedback.setOnClickListener {
            val intent7 = Intent(this,Feedback::class.java)
            startActivity(intent7)
        }
        var update: Button =findViewById(R.id.profile_update)
        update.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter food taken today")
            val view = layoutInflater.inflate(R.layout.daily,null)
            val data = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Add", DialogInterface.OnClickListener { _, _ ->
                adddata(data)
            })
            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ ->  })
            builder.show()
        }
        gettingData()
        var change_pass: Button = findViewById<Button>(R.id.profile_change)
        change_pass.setOnClickListener {
            val intent1 = Intent(this@Profile, ChangePassword::class.java)
            startActivity(intent1)
        }

        var logout: Button = findViewById<Button>(R.id.profile_logout)
        logout.setOnClickListener {
            val intent2 = Intent(this@Profile, MainActivity::class.java)
            startActivity(intent2)
        }
    }
    private fun adddata(data : EditText){
        val userid = auth.currentUser.uid
        var data1=data.text.toString()
        db.collection("dailygraph").document(userid).update("daily",FieldValue.arrayUnion(data1))
    }
    fun gettingData(){
        val userid = auth.currentUser.uid
        var name: TextView =findViewById(R.id.profile_name)
        var email: TextView =findViewById(R.id.profile_email)
        db.collection("users").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists","DocumentSnapshot Data:${document.data}")
                name.text=document.getString("name").toString()
                email.text=document.getString("email").toString()
            }
            else{
                Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
            }
        }  .addOnFailureListener { exception ->
            Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()

        }
    }

    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {

        //------------  alarm settings start  -----------------//

        if (timeInMilliSeconds > 0) {


            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, Notify::class.java) // AlarmReceiver1 = broadcast receiver

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)


            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds


            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,AlarmManager.INTERVAL_DAY, pendingIntent)

        }

        //------------ end of alarm settings  -----------------//


    }
}