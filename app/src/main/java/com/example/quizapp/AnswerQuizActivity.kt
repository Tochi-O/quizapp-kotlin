package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp

class AnswerQuizActivity : AppCompatActivity() {

    lateinit var quiztitle: String
    lateinit var quiztitletxt: TextView

    lateinit var quizId: String

    lateinit var aQuiz: QuizModel
    lateinit var questionLst: ArrayList<QuizModel>
    lateinit var newqt: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_quiz)
        FirebaseApp.initializeApp(this)

        quiztitle = intent.getStringExtra("Title").toString()
        quizId = intent.getStringExtra("Id").toString()
        aQuiz = QuizModel()
        questionLst = ArrayList()

        quiztitletxt = findViewById(R.id.qztitle2)
        quiztitletxt.text = quiztitle

        Log.d("QUESTION LIST B",questionLst.size.toString())

        val rvQuestions = findViewById<View>(R.id.rvquestions2) as RecyclerView
        var adapter =  TakeQuizAdapter(questionLst)
        rvQuestions.adapter = adapter
        rvQuestions.layoutManager = LinearLayoutManager(this)

        //get list of quesions from firestore
        FirebaseUtils().fireStoreDatabase
            .collection("quiz").document(quizId).collection("questions").get().addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { doc ->
                    aQuiz = QuizModel()
                    aQuiz.title = quiztitle
                    aQuiz.id = quizId
                    aQuiz.question = doc.data["question"] as String
                    aQuiz.answer = doc.data["answer"] as String
                    aQuiz.questiontype = doc.data["questiontype"] as String
                    aQuiz.multipleCh = ArrayList()
//                    if (aQuiz.questiontype == "Multiple Choice") {
                        aQuiz.multipleCh = doc.data["multipleCh"] as ArrayList<String>
//                    }

                    questionLst.add(aQuiz)


                    // urlsFstore.add(doc.data["imageUrl"]as String)


                    //   rvQuestions.adapter = adapter
                    //   rvQuestions.layoutManager = LinearLayoutManager(this)

                    // val myQuestionList =

                    // Lookup the recyclerview in activity layout
                    // Lookup the recyclerview in activity layout


                }

                Log.d("QUESTION LIST A",questionLst.size.toString())
                adapter.notifyDataSetChanged()


            }
        //answer questions

        //if multichoice
        //select radio button, if position of radio button is equals to the index of text of answer in the list
        //if theory
        //enter answer in text view, in lowercase, remove commas or fullstop  and split answer by space and for every word,
        // if its contained in the answer then its correct, if 30% of the words contain, add a score for every word that contains



    }
}