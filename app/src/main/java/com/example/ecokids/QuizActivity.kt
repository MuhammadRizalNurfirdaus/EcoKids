package com.example.ecokids

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var questions: List<QuizQuestion>
    private var currentQuestionIndex = 0
    private var correctAnswersInLevel = 0
    private var currentLevel = "A"

    private lateinit var tvQuestion: TextView
    private lateinit var ivQuestion: android.widget.ImageView
    private lateinit var tvLevel: TextView
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var btnOptionD: Button
    private lateinit var tvScore: TextView
    private lateinit var tvTimer: TextView
    private lateinit var progressBarTimer: android.widget.ProgressBar

    private var quizTimer: android.os.CountDownTimer? = null
    private val timePerQuestion = 15000L // 15 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        
        // Stop background music for concentration
        MusicManager.pauseMusic()

        dbHelper = DatabaseHelper(this)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivQuestion = findViewById(R.id.ivQuestion)
        tvLevel = findViewById(R.id.tvLevel)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        btnOptionD = findViewById(R.id.btnOptionD)
        tvScore = findViewById(R.id.tvScore)
        tvTimer = findViewById(R.id.tvTimer)
        progressBarTimer = findViewById(R.id.progressBarTimer)

        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener {
            showExitConfirmation()
        }

        loadLevel(currentLevel)

        btnOptionA.setOnClickListener { checkAnswer(0) }
        btnOptionB.setOnClickListener { checkAnswer(1) }
        btnOptionC.setOnClickListener { checkAnswer(2) }
        btnOptionD.setOnClickListener { checkAnswer(3) }
    }

    private fun loadLevel(level: String) {
        currentLevel = level
        questions = dbHelper.getQuizzesByLevel(level)
        currentQuestionIndex = 0
        correctAnswersInLevel = 0
        
        tvLevel.text = "Level $currentLevel"
        
        if (questions.isNotEmpty()) {
            loadQuestion()
        } else {
            Toast.makeText(this, "Tidak ada soal untuk Level $level", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadQuestion() {
        val q = questions[currentQuestionIndex]
        tvQuestion.text = q.question
        
        // Load Image
        if (q.imageResId != 0) {
            ivQuestion.setImageResource(q.imageResId)
            ivQuestion.visibility = android.view.View.VISIBLE
        } else {
            ivQuestion.visibility = android.view.View.GONE
        }
        
        btnOptionA.text = q.optionA
        btnOptionB.text = q.optionB
        btnOptionC.text = q.optionC
        btnOptionD.text = q.optionD

        enableButtons(true)
        tvScore.text = "Benar: $correctAnswersInLevel"
        startTimer()
    }

    private fun startTimer() {
        quizTimer?.cancel()
        progressBarTimer.max = 15
        progressBarTimer.progress = 15
        
        quizTimer = object : android.os.CountDownTimer(timePerQuestion, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                tvTimer.text = secondsRemaining.toString()
                progressBarTimer.progress = secondsRemaining
            }

            override fun onFinish() {
                tvTimer.text = "0"
                progressBarTimer.progress = 0
                showTimeoutDialog()
            }
        }.start()
    }

    private fun checkAnswer(selectedOption: Int) {
        quizTimer?.cancel()
        val q = questions[currentQuestionIndex]
        val isCorrect = selectedOption == q.correctAnswer
        
        if (isCorrect) {
            correctAnswersInLevel++
            showResultDialog(true, "")
        } else {
            val correctAnswerText = when (q.correctAnswer) {
                0 -> q.optionA
                1 -> q.optionB
                2 -> q.optionC
                3 -> q.optionD
                else -> ""
            }
            showResultDialog(false, correctAnswerText)
        }
    }

    private fun showResultDialog(isCorrect: Boolean, correctAnswer: String) {
        val title = if (isCorrect) "HEBAT! BENAR!" else "YAH! SALAH..."
        val message = if (isCorrect) "Kamu pintar sekali!" else "Jawaban yang benar adalah:\n$correctAnswer"
        val icon = if (isCorrect) R.drawable.logoecokids else R.drawable.ic_back // Placeholder for icon

        // Custom Dialog or standard Access for simplicity first, but customized message
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("Lanjut") { dialog, _ ->
            dialog.dismiss()
            goToNextQuestion()
        }
        builder.show()
    }

    private fun showTimeoutDialog() {
        val q = questions[currentQuestionIndex]
        val correctAnswerText = when (q.correctAnswer) {
            0 -> q.optionA
            1 -> q.optionB
            2 -> q.optionC
            3 -> q.optionD
            else -> ""
        }

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("WAKTU HABIS!")
        builder.setMessage("Kamu kehabisan waktu.\nJawabannya adalah: $correctAnswerText")
        builder.setCancelable(false)
        builder.setPositiveButton("Lanjut") { dialog, _ ->
            dialog.dismiss()
            goToNextQuestion()
        }
        builder.show()
    }

    private fun goToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            loadQuestion()
        } else {
            finishLevel()
        }
    }

    private fun finishLevel() {
        if (correctAnswersInLevel == questions.size) {
            // All correct
            when (currentLevel) {
                "A" -> showNextLevelDialog("B")
                "B" -> showNextLevelDialog("C")
                "C" -> showNextLevelDialog("D")
                "D" -> showGameComplete()
            }
        } else {
            // Failed
            showTryAgainDialog()
        }
    }

    private fun showNextLevelDialog(nextLevel: String) {
        tvQuestion.text = "Hebat! Lanjut Level $nextLevel?"
        enableButtons(false)
        android.app.AlertDialog.Builder(this)
            .setTitle("Level Selesai!")
            .setMessage("Kamu benar semua! Lanjut ke Level $nextLevel?")
            .setPositiveButton("Lanjut") { _, _ -> loadLevel(nextLevel) }
            .setNegativeButton("Home") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun showTryAgainDialog() {
        tvQuestion.text = "Yah, belum benar semua."
        enableButtons(false)
        android.app.AlertDialog.Builder(this)
            .setTitle("Belum Berhasil")
            .setMessage("Kamu harus benar semua untuk lanjut. Coba lagi?")
            .setPositiveButton("Coba Lagi") { _, _ -> loadLevel(currentLevel) }
            .setNegativeButton("Home") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun showGameComplete() {
        tvQuestion.text = "JUARA ECOKIDS!"
        enableButtons(false)
        android.app.AlertDialog.Builder(this)
            .setTitle("SELAMAT!")
            .setMessage("Kamu Juara EcoKids! Kamu berhasil menyelesaikan semua level.")
            .setPositiveButton("Main Lagi") { _, _ -> loadLevel("A") }
            .setNegativeButton("Selesai") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun showExitConfirmation() {
        quizTimer?.cancel() // Pause timer
        android.app.AlertDialog.Builder(this)
            .setTitle("Keluar Quiz?")
            .setMessage("Yakin mau keluar? Progres kamu akan hilang.")
            .setPositiveButton("Ya, Keluar") { _, _ -> finish() }
            .setNegativeButton("Main Lagi") { dialog, _ -> 
                dialog.dismiss()
                // Resume timer logic is complex with CountDownTimer, easiest is to restart question or just let it resume (requires custom implementation)
                // For simplicity, we just resume logic: creating new timer with remaining time is best, but for now let's just restart the timer or ignore (since we cancelled it).
                // Better approach: Don't cancel timer on dialog, just let it run? No, that's unfair.
                // Re-start timer with remaining time would be ideal, but for MVP, let's just restart the timer for this question or accept penalty.
                // Let's restart the question timer for simplicity of "child friendly" loose rules.
                startTimer() 
            }
            .setCancelable(false)
            .show()
    }

    private fun enableButtons(enable: Boolean) {
        btnOptionA.isEnabled = enable
        btnOptionB.isEnabled = enable
        btnOptionC.isEnabled = enable
        btnOptionD.isEnabled = enable
    }

    override fun onBackPressed() {
        showExitConfirmation()
    }




    override fun onDestroy() {
        super.onDestroy()
        quizTimer?.cancel()
        
        // Resume main BGM when exiting quiz
        val prefs = getSharedPreferences("EcoKidsPrefs", MODE_PRIVATE)
        if (prefs.getBoolean("SOUND_ENABLED", true)) {
            MusicManager.playMusic(this)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
