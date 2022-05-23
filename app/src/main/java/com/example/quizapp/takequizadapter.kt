package com.example.quizapp

import android.content.Intent
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TakeQuizAdapter (private val mQuiz: ArrayList<QuizModel>) : RecyclerView.Adapter<TakeQuizAdapter.ViewHolder>()
{



    var score: Int=0
    lateinit var done: ArrayList<Boolean>
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val questextview: TextView = itemView.findViewById<TextView>(R.id.aquestion)
        val mu1TextView: RadioButton = itemView.findViewById<RadioButton>(R.id.choice1)
        val mu2TextView: RadioButton = itemView.findViewById<RadioButton>(R.id.choice2)
        val mu3TextView: RadioButton = itemView.findViewById<RadioButton>(R.id.choice3)
        val mu4TextView: RadioButton = itemView.findViewById<RadioButton>(R.id.choice4)
        //val muanswer: TextView = itemView.findViewById<TextView>(R.id.muanswer)
        val theoanswer: EditText = itemView.findViewById<EditText>(R.id.auseranswer)
        val answered: Button = itemView.findViewById(R.id.donequestion)





        //question name,
    // multichoice 1,2,3,4, answer, question type
        //or edittext for theory
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeQuizAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.takequiz, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: TakeQuizAdapter.ViewHolder, position: Int) {
      //  FirebaseApp.initializeApp(this)

        // Get the data model based on position
        val quiz: QuizModel = mQuiz[position]
        // Set item views based on your views and data model
        val questextView1 = viewHolder.questextview
        questextView1.text = quiz.question
        Log.d("working?", "workingww")
        Log.d("working?", quiz.question)
        val theotextView1 = viewHolder.theoanswer
        val mu1textView1 = viewHolder.mu1TextView
        val mu2textView1 = viewHolder.mu2TextView
        val mu3textView1 = viewHolder.mu3TextView
        val mu4textView1 = viewHolder.mu4TextView
        val theoedit = viewHolder.theoanswer
        val  doneanswer = viewHolder.answered

        if(quiz.questiontype=="Multiple Choice"){

            val ans: Int= quiz.multipleCh.indexOf(quiz.answer)

            val strans =  "Answer: ${ans}."+quiz.answer


            mu1textView1.text = quiz.multipleCh[0]
            mu2textView1.text = quiz.multipleCh[1]
            mu3textView1.text = quiz.multipleCh[2]
            mu4textView1.text = quiz.multipleCh[3]
            // mu5textView1.text =strans
            theotextView1.visibility=View.GONE



            //check answer when DONE
            //done function

        }else{
            Log.d("working?", "workingww")
            Log.d("working?", quiz.answer)
            // theotextView1.visibility = View.VISIBLE
            mu1textView1.visibility= View.GONE
            mu2textView1.visibility= View.GONE
            mu3textView1.visibility= View.GONE
            mu4textView1.visibility= View.GONE
            // mu5textView1.visibility= View.GONE

            Log.d("working?", "workingww")
            Log.d("working?", quiz.answer)



            //when done button is clecked
            //check theory answer function




        }


        done = ArrayList<Boolean>()

        doneanswer.setOnClickListener {
            if(quiz.questiontype=="Multiple Choice"){
                var chosenans = ""
                if(mu1textView1.isChecked){
                    chosenans = mu1textView1.text.toString()
                    if(chosenans.lowercase()==quiz.answer.lowercase()){
                        score++
                    }
                    //disable button
                    doneanswer.isEnabled=false
                    done.add(true)
                }else if(mu2textView1.isChecked){
                    chosenans = mu2textView1.text.toString()
                    if(chosenans.lowercase()==quiz.answer.lowercase()){
                        score++
                    }
                    doneanswer.isEnabled=false
                    done.add(true)
                }else if(mu3textView1.isChecked){
                    chosenans = mu3textView1.text.toString()
                    if(chosenans.lowercase()==quiz.answer.lowercase()){
                        score++
                    }
                    doneanswer.isEnabled=false
                    done.add(true)
                }else if(mu4textView1.isChecked){
                    chosenans = mu4textView1.text.toString()
                    if(chosenans.lowercase()==quiz.answer.lowercase()){
                        score++
                    }
                    doneanswer.isEnabled=false
                    done.add(true)
                } else{
                    //ask for answer
                }

            }else{
                var tempans = theotextView1.text.toString()

                //check if empty
                if(tempans.isEmpty()){

                }else {


                    // tempans.replace(","," ").replace()
                    val regex = Regex("[^A-Za-z0-9]")
                    val result = regex.replace(tempans, " ")
                    var answrs: List<String> = result.split(" ")
                    var correct = false

                    for (ans1 in answrs) {
                        if (quiz.answer.contains(ans1)) {
                            correct = true
                            break
                        }
                    }

                    if (correct) {
                        score++
                    }
                    doneanswer.isEnabled=false
                    done.add(true)
                }

            }


            val cUserId = FirebaseAuth.getInstance().currentUser!!.uid
            if (done.size==mQuiz.size){
                val scorestr = "${score}${"/"}${mQuiz.size}"

                var qzscore=QuizScore()
                qzscore.quizId = quiz.id
                qzscore.quizname = quiz.title
                qzscore.quizScore = scorestr

                FirebaseUtils().fireStoreDatabase
                    .collection("user").document(cUserId).collection("Scores").add(qzscore)

                //show dialog
                val builder = AlertDialog.Builder(viewHolder.itemView.context)
                builder.setTitle("Confirm")
                builder.setMessage("Your score is: $scorestr")
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setNeutralButton("Close") { dialog, which ->
                    //go to results page
                    var intent= Intent(viewHolder.itemView.context, QuizResultsActivity::class.java)
                    viewHolder.itemView.context.startActivity(intent)
                    dialog.dismiss()
                }




                builder.show()
            }


        }






        //answered bool, oldscore int, if answered true, subtract old score  from main score then add new score to main score


        //when radio button is selected, check the postion, text of the position and see if the text of the position is similar to answer
        //add score //send the score to the activity




        // val button = viewHolder.messageButton
        //button.text = if (quiz.isOnline) "Message" else "Offline"
        //button.isEnabled = quiz.isOnline

        //set question title
        //check question type

        //make views invisible

        //view multiple choice
        //set answer

    }

    //bool checkTheory

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mQuiz.size
    }
}