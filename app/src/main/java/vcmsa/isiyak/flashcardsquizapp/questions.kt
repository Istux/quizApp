package vcmsa.isiyak.flashcardsquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class questions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //code starts here
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvAnswer = findViewById<TextView>(R.id.tvAnswer)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val tvScore = findViewById<TextView>(R.id.tvScore)

        // Array of questions
        val questions = arrayOf(
            "1. The silk Road was a trade route that connected China with Europe.", // true
            "2. Mahatma Gandhi was born in Nigeria.", // false
            "3. Adolf Hitler was a good man.", // false
            "4. World war I began in 1914.", // true
            "5. The Great wall of china was built using only roasted marshmallows and caramel." // false
        )
        val answers = arrayOf(true, false, false, true, false)
        var score = 0
        var questionIndex = 0
        val userAnswers = mutableListOf<Boolean>() // Track user's answers

        // Load the current question
        fun loadQuestion() {
            tvQuestion.text = questions[questionIndex]
            tvAnswer.text = ""
            btnTrue.isEnabled = true
            btnFalse.isEnabled = true
        }
        loadQuestion()

        // Check user's answer
        fun checkAnswer(userAnswer: Boolean) {
            if (userAnswer == answers[questionIndex]) {
                tvAnswer.text = "Correct!"
                score++
            } else {
                tvAnswer.text = "Incorrect!"
            }
            userAnswers.add(userAnswer) // Store user's answer
            btnTrue.isEnabled = false
            btnFalse.isEnabled = false
        }

        btnTrue.setOnClickListener {
            checkAnswer(true)
        }
        btnFalse.setOnClickListener {
            checkAnswer(false)
        }

        btnNext.setOnClickListener {
            questionIndex = (questionIndex + 1) % questions.size
            loadQuestion()

            // Show score after each question
            tvScore.text = "Score: $score/${questions.size}"
        }

        btnReview.setOnClickListener {
            // Create intent to go to the score page
            val intent = Intent(this, scorePage::class.java).apply {
                putExtra("score", score)  // Send the score to the scorePage
                putStringArrayListExtra("questions", ArrayList(questions.toList()))  // Send questions
            }
            // Start the Score Page Activity
            startActivity(intent)
        }
    }
}
