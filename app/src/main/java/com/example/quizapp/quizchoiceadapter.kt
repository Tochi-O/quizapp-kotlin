package com.example.quizapp


import android.content.Intent
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class QuizChoiceAdapter (private val mQuiznames: ArrayList<QuizName>) : RecyclerView.Adapter<QuizChoiceAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView: TextView = itemView.findViewById<TextView>(R.id.quizname)
//        val questextview: Button = itemView.findViewById<Button>(R.id.questionTxt)
//        val mu1TextView: TextView = itemView.findViewById<TextView>(R.id.mu1)
//        val mu2TextView: TextView = itemView.findViewById<TextView>(R.id.mu2)
//        val mu3TextView: TextView = itemView.findViewById<TextView>(R.id.mu3)
//        val mu4TextView: TextView = itemView.findViewById<TextView>(R.id.mu4)
//        val muanswer: TextView = itemView.findViewById<TextView>(R.id.muanswer)
//        val theoanswer: TextView = itemView.findViewById<TextView>(R.id.theoanswer)





        //question name, multichoice 1,2,3,4, answer, question type
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizChoiceAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.quizname, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: QuizChoiceAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val quizname: QuizName = mQuiznames[position]
        // Set item views based on your views and data model
        val quiznametextView1 = viewHolder.nameTextView
        quiznametextView1.text = quizname.quizname
        quiznametextView1.setOnClickListener {
            //go to quiz with its id
            quizname.quizId
            val takeqzInt=  Intent(viewHolder.itemView.context, AnswerQuizActivity::class.java)
            takeqzInt.putExtra("Title",quizname.quizname)
            takeqzInt.putExtra("Id",quizname.quizId)
            quiznametextView1.context.startActivity(takeqzInt)

        }
//        val theotextView1 = viewHolder.theoanswer
//        val mu1textView1 = viewHolder.mu1TextView
//        val mu2textView1 = viewHolder.mu2TextView
//        val mu3textView1 = viewHolder.mu3TextView
//        val mu4textView1 = viewHolder.mu4TextView
//        val mu5textView1 = viewHolder.muanswer
//
//
//        if(quiz.questiontype=="Multiple Choice"){
//
//            val ans: Int= quiz.multipleCh.indexOf(quiz.answer)
//
//            val strans =  "Answer: ${ans}."+quiz.answer
//
//            mu1textView1.text = quiz.multipleCh[0]
//            mu2textView1.text = quiz.multipleCh[1]
//            mu3textView1.text = quiz.multipleCh[2]
//            mu4textView1.text = quiz.multipleCh[3]
//            mu5textView1.text =strans
//            theotextView1.visibility=View.GONE
//
//        }else{
//            mu1textView1.visibility= View.GONE
//            mu2textView1.visibility= View.GONE
//            mu3textView1.visibility= View.GONE
//            mu4textView1.visibility= View.GONE
//            mu5textView1.visibility= View.GONE
//            theotextView1.text = quiz.answer
//        }





        // val button = viewHolder.messageButton
        //button.text = if (quiz.isOnline) "Message" else "Offline"
        //button.isEnabled = quiz.isOnline

        //set question title
        //check question type

        //make views invisible

        //view multiple choice
        //set answer

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mQuiznames.size
    }
}