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

class QuizResultsAdapter (private val mQuiznames: ArrayList<QuizScore>) : RecyclerView.Adapter<QuizResultsAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView: TextView = itemView.findViewById<TextView>(R.id.quizname)
        val scoreTextView: TextView = itemView.findViewById<TextView>(R.id.quizscore)

    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizResultsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.quizresult, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: QuizResultsAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val quizname: QuizScore = mQuiznames[position]
        // Set item views based on your views and data model
        val quiznametextView1 = viewHolder.nameTextView
        quiznametextView1.text = quizname.quizname
        val quizscoretextView1 = viewHolder.scoreTextView
        quizscoretextView1.text = quizname.quizScore


    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mQuiznames.size
    }


}