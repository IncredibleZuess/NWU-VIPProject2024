package com.example.vip_project_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlin.random.Random

class GameHangManActivity : AppCompatActivity() {

    private lateinit var txtWordView: TextView
    private lateinit var txtLettersUsed: TextView
    private lateinit var imageHangMan: ImageView
    private lateinit var txtGameLost: TextView
    private lateinit var txtGameWon: TextView
    private lateinit var letterlayout: ConstraintLayout
    private var wordGuessArray = listOf<String>()
    private var lettersUsed = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxTries = 6
    private var currentTries = 0
    private var drawable: Int = R.drawable.hangman_0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_hang_man)

        imageHangMan = findViewById(R.id.imgViewHangMan)
        txtWordView = findViewById(R.id.txtWordView)
        txtLettersUsed = findViewById(R.id.txtUsedLetters)
        txtGameLost = findViewById(R.id.txtYouLost)
        txtGameWon = findViewById(R.id.txtYouWon)
        letterlayout = findViewById(R.id.lettersLayout)

        val difficultyLevel = intent.getStringExtra("difficulty_level")
        when (difficultyLevel) {
            "Easy" -> wordGuessArray = listOf("SEARCH", "MANGO", "CRAVE")

            "Medium" -> wordGuessArray = listOf("CONUNDRUM", "INCOGNITO", "LABORATORY")
            "Hard" -> wordGuessArray = listOf("CONGRATULATIONS", "ONOMATOPOEIA", "ETIQUETTE")
        }

        txtGameLost.visibility = View.INVISIBLE
        txtGameWon.visibility = View.INVISIBLE

        startNewGame()
        setupLetterClickListeners()
    }

    private fun startNewGame() {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.hangman_0

        val randomIndex = Random.nextInt(0, wordGuessArray.size)
        wordToGuess = wordGuessArray[randomIndex]
        generateUnderscores(wordToGuess)
        //txtWordView.text = formatWordDisplay(underscoreWord)
        updateUI()
    }

    private fun generateUnderscores(word: String) {
        underscoreWord = word.map {
            when (it) {
                ' ' -> ' ' // Keep spaces as spaces
                else -> '_' // Replace all other characters with '_'
            }
        }.joinToString("")
    }

    private fun setupLetterClickListeners() {
        letterlayout.children.forEach { letterView ->
            if (letterView is TextView)
            {
                letterView.setOnClickListener {
                    val letter = letterView.text[0]
                    play(letter)
                    updateUI()
                    letterView.visibility = View.GONE
                }
            }
        }
    }

    private fun play(letter: Char) {
        if (lettersUsed.contains(letter))
        {
            return
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true))
            {
                indexes.add(index)
            }
        }

        val sb = StringBuilder(underscoreWord)
        indexes.forEach { index ->
            sb.setCharAt(index, letter)
        }
        underscoreWord = sb.toString()

        if (indexes.isEmpty())
        {
            currentTries++
        }

        updateUI()
    }

    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.hangman_0
            1 -> R.drawable.hangman_1
            2 -> R.drawable.hangman_2
            3 -> R.drawable.hangman_3
            4 -> R.drawable.hangman_4
            5 -> R.drawable.hangman_5
            6 -> R.drawable.hangman_6
            else -> R.drawable.hangman_6
        }
    }

    private fun updateUI() {
        txtWordView.text = formatWordDisplay(underscoreWord)
        txtLettersUsed.text = "Letters used: $lettersUsed"
        imageHangMan.setImageDrawable(ContextCompat.getDrawable(this, getHangmanDrawable()))

        if (underscoreWord.equals(wordToGuess, true))
        {
            showGameWon()
            val data = Intent()
            data.putExtra("user_won", true)
            setResult(Activity.RESULT_OK, data)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 2000)

        }
        else if (currentTries >= maxTries)
        {
            showGameLost()
            val data = Intent()
            data.putExtra("user_won", false)
            setResult(Activity.RESULT_OK, data)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 2000)

        }
    }
    private fun formatWordDisplay(word: String): String {
        // Insert a space between each character to improve visibility
        return word.map { "$it " }.joinToString("").trim()
    }

    private fun showGameLost() {
        txtWordView.text = wordToGuess
        txtGameLost.visibility = View.VISIBLE
        letterlayout.visibility = View.GONE
    }

    private fun showGameWon() {
        txtWordView.text = wordToGuess
        txtGameWon.visibility = View.VISIBLE
        letterlayout.visibility = View.GONE


    }
}
