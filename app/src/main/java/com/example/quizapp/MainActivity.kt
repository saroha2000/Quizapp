package com.example.quizapp


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Question(val text: String, val answers: List<String>, val correctAnswerIndex: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answersRadioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var resultTextView: TextView

    private val questions = listOf(
        Question("What is the capital of France?", listOf("Paris", "London", "Berlin", "Madrid"), 0),
        Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
        Question("What is the color of the sky?", listOf("Blue", "Green", "Red", "Yellow"), 0)
    )

    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.questionTextView)
        answersRadioGroup = findViewById(R.id.answersRadioGroup)
        submitButton = findViewById(R.id.submitButton)
        resultTextView = findViewById(R.id.resultTextView)

        loadQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            questionTextView.text = question.text
            (answersRadioGroup.getChildAt(0) as RadioButton).text = question.answers[0]
            (answersRadioGroup.getChildAt(1) as RadioButton).text = question.answers[1]
            (answersRadioGroup.getChildAt(2) as RadioButton).text = question.answers[2]
            (answersRadioGroup.getChildAt(3) as RadioButton).text = question.answers[3]
            answersRadioGroup.clearCheck() // Clear previous selections
            resultTextView.text = "" // Clear previous result
        } else {
            // If there are no more questions, show the final result
            showFinalResult()
        }
    }

    private fun checkAnswer() {
        val selectedId = answersRadioGroup.checkedRadioButtonId
        if (selectedId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedAnswerIndex = answersRadioGroup.indexOfChild(selectedRadioButton)
            val question = questions[currentQuestionIndex]

            if (selectedAnswerIndex == question.correctAnswerIndex) {
                correctAnswersCount++
                resultTextView.text = "Correct!"
            } else {
                resultTextView.text = "Incorrect! The correct answer is ${question.answers[question.correctAnswerIndex]}."
            }

            currentQuestionIndex++

            // Load the next question or show the final result
            if (currentQuestionIndex < questions.size) {
                loadQuestion()
            } else {
                showFinalResult()
            }
        } else {
            resultTextView.text = "Please select an answer."
        }
    }

    private fun showFinalResult() {
        questionTextView.visibility = View.GONE
        answersRadioGroup.visibility = View.GONE
        submitButton.visibility = View.GONE

        resultTextView.text = "Quiz Finished! You got $correctAnswersCount out of ${questions.size} correct."
    }
}