package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.quizapp.R
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
//import android.R





data class MyQuestionList(
    var questionsList: ArrayList<String>
)
class QuizListActivity : AppCompatActivity() {

    lateinit var quiztitle:TextView
    lateinit var quizId:String
    lateinit var aQuiz: QuizModel
    lateinit var questionLst: ArrayList<QuizModel>
    lateinit var newqt: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list)
        FirebaseApp.initializeApp(this)

        quiztitle = findViewById(R.id.qztitle)

        //name of quiz
        quiztitle.text=intent.getStringExtra("quiId").toString()
        quizId = intent.getStringExtra("quiId2").toString()
        newqt = findViewById(R.id.newquestion)
        newqt.setOnClickListener {
            val intent = Intent(this, CreateQuizActivity::class.java)
            intent.putExtra("Title",quiztitle.text)
            intent.putExtra("Id",quizId)
            startActivity(intent);
        }

        aQuiz = QuizModel()
        questionLst = ArrayList()

        Log.d("QUESTION LIST B",questionLst.size.toString())

        val rvQuestions = findViewById<View>(R.id.rvquestions) as RecyclerView
        var adapter =  QuizAdapter(questionLst)
        rvQuestions.adapter = adapter
        rvQuestions.layoutManager = LinearLayoutManager(this)

        FirebaseUtils().fireStoreDatabase
            .collection("quiz").document(quizId).collection("questions").get().addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { doc ->
                    aQuiz = QuizModel()
                    aQuiz.question = doc.data["question"] as String
                    aQuiz.answer = doc.data["answer"] as String
                    aQuiz.questiontype = doc.data["questiontype"] as String
                    aQuiz.multipleCh = ArrayList()
                    if (aQuiz.questiontype == "Multiple Choice") {
                        aQuiz.multipleCh = doc.data["multipleCh"] as ArrayList<String>
                    }

                    questionLst.add(aQuiz)

                    // urlsFstore.add(doc.data["imageUrl"]as String)


                 //   rvQuestions.adapter = adapter
                 //   rvQuestions.layoutManager = LinearLayoutManager(this)
                    adapter.notifyDataSetChanged()

                    // val myQuestionList =

                    // Lookup the recyclerview in activity layout
                    // Lookup the recyclerview in activity layout


                }

                Log.d("QUESTION LIST A",questionLst.size.toString())

            }
        // Initialize contacts

        // Initialize contacts
        //q = Contact.createContactsList(20)
        // Create adapter passing in the sample user data
        // Create adapter passing in the sample user data

        //list of questions in a quiz using the quiz ID





        //create quiz id
        //list from firestore


        //add to firestore go to create question activity and comeback here after question has saved
        // take quiz id to the create question activity

    }

}