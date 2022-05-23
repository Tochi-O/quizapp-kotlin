package com.example.quizapp

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.Extensions.toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class FirebaseUtils {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
}
class MainActivity : AppCompatActivity() {


    lateinit var creteqz: Button
    lateinit var pckqz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)




        creteqz = findViewById(R.id.newQz)
        pckqz = findViewById(R.id.chooseqz)

        creteqz.setOnClickListener {

            //Make a quiz
            // dialog for quiz name
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Title of Quiz")

// Set up the input
            val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setHint("Fractions 1 assessment")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

// Set up the buttons
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // Here you get get input text from the Edittext
//            cityupdate = input.text.toString()
//            //update in Firestore
//            val hashMap1 = hashMapOf<String, Any>(
//                "name" to nameview.text.toString(),
//                "city" to cityupdate.toString(),
//                "age" to ageview.text.toString()
//            )

                val hashMap1 = hashMapOf<String,Any>(
                    "quizname" to input.text.toString()
                )
                // use the add() method to create a document inside users collection
//
//                var docref = FirebaseUtils().fireStoreDatabase.collection("quiz")
//                    .add(hashMap1)
                FirebaseUtils().fireStoreDatabase.collection("quiz")
                    .add(hashMap1).addOnSuccessListener {
                        Log.d(TAG, "Updated document with ID ${it.id}")
                        val intent = Intent(this, CreateQuizActivity::class.java)
                        intent.putExtra("Title",input.text.toString())
                        intent.putExtra("Id",it.id)
                        startActivity(intent);
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error Updating document $exception")
                    }


            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()

        }


        // Take a quiz
        //pick quiz to take
        pckqz.setOnClickListener {
            val intent = Intent(this, PickQuizActivity::class.java)
            startActivity(intent);
        }


        //open list of past quiz titles, scores and dates taken


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, RegisterActivity::class.java))
                toast("signed out")
                finish()
                //Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}