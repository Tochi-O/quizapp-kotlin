package com.example.quizapp

import android.content.ContentValues
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.SetOptions

class CreateQuizActivity : AppCompatActivity() {


    lateinit var qtTitle: EditText
     var qtType: String=""
    lateinit var theAnsw: EditText
    lateinit var multichoice1: EditText
    lateinit var multichoice2: EditText
    lateinit var multichoice3: EditText
    lateinit var multichoice4: EditText
    lateinit var saveQuestion: Button
    lateinit var multiansw: RadioButton
    lateinit var multiansw1: RadioButton
    lateinit var multiansw2: RadioButton
    lateinit var multiansw3: RadioButton
    lateinit var quizId: String
    lateinit var newQuiz: QuizModel
    lateinit var spinner: Spinner
    lateinit var quiztitle: String
    lateinit var theanstxt:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)
        FirebaseApp.initializeApp(this)


        quiztitle = intent.getStringExtra("Title").toString()
        quizId = intent.getStringExtra("Id").toString()


        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = quiztitle

        //open dialog to add quiz
        //get question title, multiplechoice or theory, theory answer or multiple choice answer

        qtTitle = findViewById(R.id.questionTitle)
        //qtType = findViewById(R.id.spinneranswertype)
         theanstxt =  findViewById(R.id.sltions)
        theAnsw = findViewById(R.id.theoryanswer)
        multichoice1 = findViewById(R.id.multichoice1)
        multichoice2 = findViewById(R.id.multichoice2)
        multichoice3 = findViewById(R.id.multichoice3)
        multichoice4 = findViewById(R.id.multichoice4)
        multiansw = findViewById(R.id.simpleRadioButton)
        multiansw1 = findViewById(R.id.simpleRadioButton1)
        multiansw2 = findViewById(R.id.simpleRadioButton2)
        multiansw3 = findViewById(R.id.simpleRadioButton3)
        theAnsw.visibility = View.GONE
        multichoice1.visibility = View.GONE
        multichoice2.visibility = View.GONE
        multichoice3.visibility = View.GONE
        multichoice4.visibility = View.GONE
        multiansw.visibility = View.GONE
        multiansw1.visibility = View.GONE
        multiansw2.visibility = View.GONE
        multiansw3.visibility = View.GONE

        saveQuestion = findViewById(R.id.saveQuest)
        val answerTypes = resources.getStringArray(R.array.AnswerTypes)
        qtType = answerTypes[0]

        spinner = findViewById(R.id.spinneranswertype)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, answerTypes
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    qtType = answerTypes[position]
                    if(position==0) {
                        multichoice1.visibility = View.VISIBLE
                        multichoice2.visibility = View.VISIBLE
                        multichoice3.visibility = View.VISIBLE
                        multichoice4.visibility = View.VISIBLE
                        multiansw.visibility = View.VISIBLE
                        multiansw1.visibility = View.VISIBLE
                        multiansw2.visibility = View.VISIBLE
                        multiansw3.visibility = View.VISIBLE
                        theAnsw.visibility = View.GONE
                        theanstxt.visibility = View.GONE

                    }
                    else{
                        theAnsw.visibility = View.VISIBLE
                        multichoice1.visibility = View.GONE
                        multichoice2.visibility = View.GONE
                        multichoice3.visibility = View.GONE
                        multichoice4.visibility = View.GONE
                        multiansw.visibility = View.GONE
                        multiansw1.visibility = View.GONE
                        multiansw2.visibility = View.GONE
                        multiansw3.visibility = View.GONE
                    }
//                    Toast.makeText(this@MainActivity,
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        var mulltichoicelist: ArrayList<String>
        // mulltichoicelist.add()




        saveQuestion.setOnClickListener {
            newQuiz = QuizModel()
            newQuiz.question = qtTitle.text.toString()
            newQuiz.questiontype = qtType
            // newQuiz.answer = theAnsw.text.toString()
            newQuiz.multipleCh = ArrayList()

            print("Question Type $qtType")

            if (qtType == "Multiple Choice") {
                newQuiz.multipleCh.add(multichoice1.text.toString())
                newQuiz.multipleCh.add(multichoice2.text.toString())
                newQuiz.multipleCh.add(multichoice3.text.toString())
                newQuiz.multipleCh.add(multichoice4.text.toString())

                if (multiansw.isChecked) {
                    newQuiz.answer = multichoice1.text.toString()
                } else if (multiansw1.isChecked) {
                    newQuiz.answer = multichoice2.text.toString()
                } else if (multiansw2.isChecked) {
                    newQuiz.answer = multichoice3.text.toString()
                } else if (multiansw3.isChecked) {
                    newQuiz.answer = multichoice4.text.toString()
                }
            } else {
                newQuiz.answer = theAnsw.text.toString()
            }
            //save to firestore under quiz id
            FirebaseUtils().fireStoreDatabase.collection("quiz").document(quizId).collection("questions")
                .add(newQuiz).addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Updated document with ID ${it.id}")
                    val intent = Intent(this, QuizListActivity::class.java)
                    intent.putExtra("quiId",quiztitle)
                    intent.putExtra("quiId2",quizId)
                    intent.putExtra("queId",it.id)
                    startActivity(intent);
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error Updating document $exception")
                }

        }
        //

    }


}