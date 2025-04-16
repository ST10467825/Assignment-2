package com.example.st10467825_assignment2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare all UI components
    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var scoreTextView: TextView

    // Arrays to store questions and answers
    private val questions = arrayOf(
        "What is the capital of France?",
        "What is 2 + 2?",
        "What is the largest planet in our solar system?",
        "Who painted the Mona Lisa?",
        "What is the chemical symbol for gold?"
    )

    private val answers = arrayOf(
        "Paris",
        "4",
        "Jupiter",
        "Leonardo da Vinci",
        "Au"
    )

    // Variables to track current question and score
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all UI components
        initializeViews()

        // Display the first question
        displayQuestion(currentQuestionIndex)

        // Set up button click listeners
        setupButtonListeners()
    }

    private fun initializeViews() {
        // Connect Kotlin variables with XML views
        questionTextView = findViewById(R.id.questTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)
        nextButton = findViewById(R.id.nextButton)
        scoreTextView = findViewById(R.id.scoreTextView)
    }

    private fun setupButtonListeners() {
        // Submit button click listener
        submitButton.setOnClickListener {
            // Get user's answer and trim whitespace
            val userAnswer = answerEditText.text.toString().trim()

            // Check if answer is empty
            when {
                userAnswer.isEmpty() -> {
                    feedbackTextView.text = "Please enter an answer!"
                }
                else -> {
                    // Check the answer against the correct answer
                    checkAnswer(userAnswer)
                }
            }
        }

        // Next button click listener
        nextButton.setOnClickListener {
            // Move to the next question
            currentQuestionIndex++

            // Check if we've reached the end of questions
            when (currentQuestionIndex) {
                in 0 until questions.size -> {
                    // Reset UI for next question
                    resetQuestionUI()
                    // Display the next question
                    displayQuestion(currentQuestionIndex)
                }
                else -> {
                    // Quiz is finished, show final score
                    showFinalScore()
                }
            }
        }
    }

    private fun displayQuestion(index: Int) {
        // Set the question text from our questions array
        questionTextView.text = questions[index]
        // Clear the answer field
        answerEditText.text.clear()
        // Hide feedback and next button initially
        feedbackTextView.text = ""
        nextButton.visibility = android.view.View.GONE
    }

    private fun checkAnswer(userAnswer: String) {
        // Get the correct answer for the current question
        val correctAnswer = answers[currentQuestionIndex]

        // Compare user's answer with correct answer (case-insensitive)
        val isCorrect = userAnswer.equals(correctAnswer, ignoreCase = true)

        // Update score if answer is correct
        score += if (isCorrect) 1 else 0

        // Show feedback to user
        feedbackTextView.text = when (isCorrect) {
            true -> "Correct! Well done!"
            false -> "Incorrect. The correct answer is: $correctAnswer"
        }

        // Disable the submit button to prevent multiple submissions
        submitButton.isEnabled = false
        // Show the next button
        nextButton.visibility = android.view.View.VISIBLE

        // Update the score display
        updateScoreDisplay()
    }

    private fun resetQuestionUI() {
        // Re-enable the submit button
        submitButton.isEnabled = true
        // Hide the next button again
        nextButton.visibility = android.view.View.GONE
        // Clear feedback
        feedbackTextView.text = ""
    }

    private fun updateScoreDisplay() {
        // Show current score out of total questions
        scoreTextView.text = "Score: $score/${questions.size}"
    }

    private fun showFinalScore() {
        // Hide question and answer elements
        questionTextView.visibility = android.view.View.GONE
        answerEditText.visibility = android.view.View.GONE
        submitButton.visibility = android.view.View.GONE
        nextButton.visibility = android.view.View.GONE
        feedbackTextView.visibility = android.view.View.GONE

        // Show final score message
        scoreTextView.text = "Quiz completed! Final score: $score/${questions.size}\n" +
                "Percentage: ${(score * 100) / questions.size}%"

        // Optionally, you could add a "Restart Quiz" button here
    }
}