package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class QuizResultsActivity : AppCompatActivity() {



    lateinit var quiztitle: TextView
    lateinit var quizId:String
    lateinit var aQuizscore: QuizScore
    lateinit var quizList: ArrayList<QuizScore>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_results)
        FirebaseApp.initializeApp(this)


        //list of quizzes
        //name of quiz
        // quiztitle.text = intent.getStringExtra("quiId").toString()
        //quizId = intent.getStringExtra("quiId2").toString()
        quizList = ArrayList()

        val rvQuestions = findViewById<View>(R.id.rvuserscores) as RecyclerView


        // Initialize contacts

        // Initialize contacts
        //q = Contact.createContactsList(20)
        // Create adapter passing in the sample user data
        // Create adapter passing in the sample user data
        val adapter = QuizResultsAdapter(quizList)
        // Attach the adapter to the recyclerview to populate items
        // Attach the adapter to the recyclerview to populate items
        rvQuestions.adapter = adapter
        // Set layout manager to position the items
        // Set layout manager to position the items
        rvQuestions.layoutManager = LinearLayoutManager(this)
        // That's all!

        var cUserId = FirebaseAuth.getInstance().currentUser!!.uid


        //list of questions in a quiz using the quiz ID
        FirebaseUtils().fireStoreDatabase
            .collection("user").document(cUserId).collection("Scores").get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { doc ->

                    aQuizscore = QuizScore()
                    aQuizscore.quizId = doc.data["quizId"] as String
                    aQuizscore.quizname = doc.data["quizname"] as String
                    aQuizscore.quizScore = doc.data["quizScore"] as String
//                    aQuiz.questiontype = doc.data["questionType"] as String
//                    aQuiz.multipleCh = doc.data["multipleCh"] as ArrayList<String>

                    quizList.add(aQuizscore)

                    // urlsFstore.add(doc.data["imageUrl"]as String)


                    // val myQuestionList =

                    // Lookup the recyclerview in activity layout
                    // Lookup the recyclerview in activity layout

                }
                adapter.notifyDataSetChanged()
            }


    }



}