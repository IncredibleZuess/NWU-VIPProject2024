package com.example.vip_project_1

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import android.widget.Toast
import android.widget.Button
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper

class GameMathActivity : AppCompatActivity() {

    private lateinit var answerEditText: EditText
    private lateinit var displayQuestion: TextView
    private lateinit var submitAnswer: Button
    private var questionArray = mutableListOf<String>()
    private var answerArray = mutableListOf<Int>()
    private var correctAnswer = 0
    private var numCorrect = 0
    private var numIncorrect = 0
    private var numQuestions = 0
    private var questionIndex = 0

    private fun getListQuestions(numQuestions: Int, maxNumber: Int) {
        val operators = listOf("+", "-", "*", "/")
        while (questionArray.size < numQuestions) {
            val randomInt1 = Random.nextInt(1, maxNumber)
            val randomInt2 = Random.nextInt(1, maxNumber)
            val randomOperator = operators.random()

            var correctAnswer: Int
            val question: String

            if (randomOperator == "/")
            {
                if (randomInt2 != 0 && randomInt1 % randomInt2 == 0)
                {
                    correctAnswer = randomInt1 / randomInt2
                    question = "What is $randomInt1 $randomOperator $randomInt2 = "
                }
                else
                {
                    // Continue to generate a valid division question
                    continue
                }
            }
            else if (randomOperator == "*")
            {
                correctAnswer = randomInt1 * randomInt2
                question = "What is $randomInt1 $randomOperator $randomInt2 = "
            }
            else if (randomOperator == "+")
            {
                correctAnswer = randomInt1 + randomInt2
                question = "What is $randomInt1 $randomOperator $randomInt2 = "
            }
            else if (randomOperator == "-")
            {
                if (randomInt1 >= randomInt2)
                {
                    correctAnswer = randomInt1 - randomInt2
                    question = "What is $randomInt1 $randomOperator $randomInt2 = "
                }
                else
                {
                    correctAnswer = randomInt2 - randomInt1
                    question = "What is $randomInt2 $randomOperator $randomInt1 = "
                }
            }
            else
            {
                continue
            }

            questionArray.add(question)
            answerArray.add(correctAnswer)
        }
    }

    private fun GameOver() {
        displayQuestion.text = "Game Over! Correct: $numCorrect, Incorrect: $numIncorrect"
        val total = numCorrect + numIncorrect
        val data = Intent()
        data.putExtra("user_won", numCorrect >= total / 2)
        setResult(Activity.RESULT_OK, data)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 2000)
    }

    private fun checkAnswer(answer: String) {
        val userAnswer = answer.toIntOrNull()
        if (userAnswer != null) {
            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                numCorrect++
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
                numIncorrect++
            }
            questionIndex++
            if (questionIndex < numQuestions) {
                questionDisplay(questionIndex)
            } else {
                GameOver()
            }
        } else {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun questionDisplay(questionIndex: Int) {
        displayQuestion.text = questionArray[questionIndex]
        correctAnswer = answerArray[questionIndex]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_math)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayQuestion = findViewById(R.id.txtMathProb)
        answerEditText = findViewById(R.id.txtAnsw_Input)
        submitAnswer = findViewById(R.id.btnSubmit)

        val difficultyLevel = intent.getStringExtra("difficulty_level")
        if (difficultyLevel != null) {
            numQuestions = when (difficultyLevel) {
                "Easy" -> 10
                "Medium" -> 15
                "Hard" -> 20
                else -> 10
            }
            val maxNumber = when (difficultyLevel) {
                "Easy" -> 11
                "Medium" -> 51
                "Hard" -> 101
                else -> 11
            }
            getListQuestions(numQuestions, maxNumber)
        }

        questionIndex = 0
        questionDisplay(questionIndex)

        submitAnswer.setOnClickListener {
            checkAnswer(answerEditText.text.toString())
            answerEditText.text.clear()
        }
    }
}
