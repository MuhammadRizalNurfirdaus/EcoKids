package com.example.ecokids

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManageQuizActivity : BaseActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyState: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_quiz)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.rvQuizList)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<android.view.View>(R.id.btnAddQuiz).setOnClickListener {
            showQuizDialog(null)
        }

        loadQuizzes()
    }

    private fun loadQuizzes() {
        val quizzes = dbHelper.getAllManualQuizzes() 
        if (quizzes.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            tvEmptyState.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            tvEmptyState.visibility = android.view.View.GONE
            recyclerView.adapter = QuizManageAdapter(quizzes, 
                onEdit = { quiz -> showQuizDialog(quiz) },
                onDelete = { quiz -> showDeleteConfirmation(quiz) }
            )
        }
    }

    private fun showQuizDialog(quiz: QuizQuestion?) {
        val view = layoutInflater.inflate(R.layout.dialog_add_quiz, null)
        val etQuestion = view.findViewById<EditText>(R.id.etQuestion)
        val spinnerLevel = view.findViewById<Spinner>(R.id.spinnerLevel)
        val etOptA = view.findViewById<EditText>(R.id.etOptionA)
        val etOptB = view.findViewById<EditText>(R.id.etOptionB)
        val etOptC = view.findViewById<EditText>(R.id.etOptionC)
        val etOptD = view.findViewById<EditText>(R.id.etOptionD)
        val rgCorrect = view.findViewById<RadioGroup>(R.id.rgCorrectAnswer)
        val title = view.findViewById<TextView>(R.id.tvDialogTitle)

        // Pre-fill if editing
        if (quiz != null) {
            title.text = "Ubah Soal Kuis"
            etQuestion.setText(quiz.question)
            etOptA.setText(quiz.optionA)
            etOptB.setText(quiz.optionB)
            etOptC.setText(quiz.optionC)
            etOptD.setText(quiz.optionD)
            
            // Set Spinner Level
            val levels = resources.getStringArray(R.array.quiz_levels)
            val levelIndex = levels.indexOf(quiz.level)
            if (levelIndex >= 0) spinnerLevel.setSelection(levelIndex)

            // Set Correct Answer
            when (quiz.correctAnswer) {
                0 -> rgCorrect.check(R.id.rbA)
                1 -> rgCorrect.check(R.id.rbB)
                2 -> rgCorrect.check(R.id.rbC)
                3 -> rgCorrect.check(R.id.rbD)
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        view.findViewById<android.view.View>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        view.findViewById<android.view.View>(R.id.btnSave).setOnClickListener {
            val question = etQuestion.text.toString().trim()
            val optA = etOptA.text.toString().trim()
            val optB = etOptB.text.toString().trim()
            val optC = etOptC.text.toString().trim()
            val optD = etOptD.text.toString().trim()
            val level = spinnerLevel.selectedItem.toString()
            
            val selectedId = rgCorrect.checkedRadioButtonId
            
            if (question.isEmpty() || optA.isEmpty() || optB.isEmpty() || optC.isEmpty() || optD.isEmpty() || selectedId == -1) {
                Toast.makeText(this, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ansIndex = when(selectedId) {
                R.id.rbA -> 0
                R.id.rbB -> 1
                R.id.rbC -> 2
                R.id.rbD -> 3
                else -> 0
            }

            val newQuiz = QuizQuestion(
                id = quiz?.id ?: 0, // 0 means new
                question = question,
                optionA = optA,
                optionB = optB,
                optionC = optC,
                optionD = optD,
                correctAnswer = ansIndex,
                level = level,
                imageResId = 0 // Manual quiz text only for now
            )

            val success = if (quiz == null) {
                dbHelper.addQuiz(newQuiz) > -1
            } else {
                dbHelper.updateQuiz(newQuiz)
            }

            if (success) {
                Toast.makeText(this, "Berhasil disimpan!", Toast.LENGTH_SHORT).show()
                loadQuizzes()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Gagal menyimpan data.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmation(quiz: QuizQuestion) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Soal?")
            .setMessage("Yakin ingin menghapus soal ini?")
            .setPositiveButton("Hapus") { _, _ ->
                if (dbHelper.deleteQuiz(quiz.id)) {
                    Toast.makeText(this, "Soal dihapus!", Toast.LENGTH_SHORT).show()
                    loadQuizzes()
                } else {
                    Toast.makeText(this, "Gagal menghapus.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
