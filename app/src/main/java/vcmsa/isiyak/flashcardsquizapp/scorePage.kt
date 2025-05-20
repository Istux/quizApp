package vcmsa.isiyak.flashcardsquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class scorePage : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnTheReview = findViewById<Button>(R.id.btnTheReview)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvTheReview = findViewById<TextView>(R.id.tvTheReview)

        // Get data from intent
        val score = intent.getIntExtra("score", 0)
        val questions = intent.getStringArrayListExtra("questions") ?: arrayListOf()
        val answers = intent.getStringArrayListExtra("answers") ?: arrayListOf()

        // Set score dynamically based on question count
        tvScore.text = "Your score is $score/${questions.size}"

        // Simple feedback
        tvTheReview.text = if (score >= 3) "Well done!" else "Try again"

        // Review button action
        btnTheReview.setOnClickListener {
            val reviewIntent = Intent(this, reviewPage::class.java).apply {
                putStringArrayListExtra("questions", questions)
                putStringArrayListExtra("answers", answers)
            }
            startActivity(reviewIntent)
        }
    }
}
