package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp

class PickQuizActivity : AppCompatActivity() {

    lateinit var quiztitle: TextView
    lateinit var quizId:String
        lateinit var aQuizname: QuizName
    lateinit var quizList: ArrayList<QuizName>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_quiz)
        FirebaseApp.initializeApp(this)

        //list of quizzes
        //name of quiz
       // quiztitle.text = intent.getStringExtra("quiId").toString()
        //quizId = intent.getStringExtra("quiId2").toString()
        quizList = ArrayList()
        aQuizname =QuizName()

        //list of questions in a quiz using the quiz ID
        FirebaseUtils().fireStoreDatabase
            .collection("quiz").get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { doc ->

                    aQuizname = QuizName()
                    aQuizname.quizId = doc.id
                    aQuizname.quizname = doc.data["quizname"] as String
//                    aQuiz.questiontype = doc.data["questionType"] as String
//                    aQuiz.multipleCh = doc.data["multipleCh"] as ArrayList<String>

                    quizList.add(aQuizname)

                    // urlsFstore.add(doc.data["imageUrl"]as String)


                    // val myQuestionList =

                }

                // Lookup the recyclerview in activity layout
                // Lookup the recyclerview in activity layout
                val rvQuestions = findViewById<View>(R.id.rvquiznames) as RecyclerView


                // Initialize contacts

                // Initialize contacts
                //q = Contact.createContactsList(20)
                // Create adapter passing in the sample user data
                // Create adapter passing in the sample user data
                val adapter = QuizChoiceAdapter(quizList)
                // Attach the adapter to the recyclerview to populate items
                // Attach the adapter to the recyclerview to populate items
                rvQuestions.adapter = adapter
                // Set layout manager to position the items
                // Set layout manager to position the items
                rvQuestions.layoutManager = LinearLayoutManager(this)
                // That's all!
            }


    }
}