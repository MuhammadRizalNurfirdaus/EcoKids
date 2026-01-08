package com.example.ecokids

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizManageAdapter(
    private val quizList: List<QuizQuestion>,
    private val onEdit: (QuizQuestion) -> Unit,
    private val onDelete: (QuizQuestion) -> Unit
) : RecyclerView.Adapter<QuizManageAdapter.QuizViewHolder>() {

    class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLevelBadge: TextView = itemView.findViewById(R.id.tvLevelBadge)
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestionPreview)
        val tvAnswer: TextView = itemView.findViewById(R.id.tvAnswerPreview)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditQuiz)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteQuiz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_manage, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]
        
        holder.tvLevelBadge.text = "Level ${quiz.level}"
        holder.tvQuestion.text = quiz.question
        
        val correctAnswerText = when (quiz.correctAnswer) {
            0 -> "A. ${quiz.optionA}"
            1 -> "B. ${quiz.optionB}"
            2 -> "C. ${quiz.optionC}"
            3 -> "D. ${quiz.optionD}"
            else -> "?"
        }
        holder.tvAnswer.text = "Jawaban: $correctAnswerText"

        // Set Level Color Logic (Optional)
        val bgRes = when(quiz.level) {
            "A" -> R.drawable.bg_button
            "B" -> R.drawable.bg_button_edit
            "C" -> R.drawable.bg_button_delete
            else -> R.drawable.bg_button
        }
        holder.tvLevelBadge.setBackgroundResource(bgRes)

        holder.btnEdit.setOnClickListener { onEdit(quiz) }
        holder.btnDelete.setOnClickListener { onDelete(quiz) }
    }

    override fun getItemCount(): Int = quizList.size
}
