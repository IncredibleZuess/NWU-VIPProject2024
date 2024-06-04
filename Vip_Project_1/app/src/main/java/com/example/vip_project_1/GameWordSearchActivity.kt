package com.example.vip_project_1

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.os.Handler
import android.os.Looper
import android.content.Intent


class GameWordSearchActivity : AppCompatActivity() {
    private var wordList = listOf("")
    private var amountofwords = wordList.size

    private lateinit var wordSearchGrid: GridView
    private lateinit var wordListText: TextView

    private val gridSize = 9
    private lateinit var gridAdapter: ArrayAdapter<String>
    private lateinit var gridData: Array<Array<Char>>

    private var selectedWord = StringBuilder()
    private var lastSelectedPosition: Int = -1

    // Map to store word positions
    private val wordPositions = mutableMapOf<String, List<Pair<Int, Int>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_word_search)

        val difficultyLevel = intent.getStringExtra("difficulty_level")
        if (difficultyLevel == "Easy") {
            wordList = listOf("NINE", "TOWN", "CROWN", "STUDIO", "WORD", "SEARCH", "GAME", "LAME")
            amountofwords = wordList.size
        } else if (difficultyLevel == "Medium") {
            wordList =
                listOf("DEVELOPER", "KOTLIN", "IPHONE", "APPLE", "DIRT", "SEARCH", "INK", "MOBILE")
            amountofwords = wordList.size
        } else if (difficultyLevel == "Hard") {
            wordList = listOf("DEVELOPER", "BEN", "PAN", "LAMMA", "BORD", "SEARCH", "GAME", "LINK")
            amountofwords = wordList.size
        }

        wordSearchGrid = findViewById(R.id.wordSearchGrid)
        wordListText = findViewById(R.id.wordListText)

        generateGrid()
        displayWordList()

        wordSearchGrid.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    selectedWord.clear()
                    wordListText.text = selectedWord.toString()
                    lastSelectedPosition = -1
                    clearHighlightedCharacters()
                }

                MotionEvent.ACTION_MOVE -> {
                    val position = wordSearchGrid.pointToPosition(event.x.toInt(), event.y.toInt())
                    if (position != AdapterView.INVALID_POSITION && position != lastSelectedPosition) {
                        val row = position / gridSize
                        val col = position % gridSize
                        val selectedChar = gridData[row][col]

                        if (selectedChar != ' ') {
                            selectedWord.append(selectedChar)
                            wordListText.text = selectedWord.toString()
                            val textView = wordSearchGrid.getChildAt(position) as TextView
                            textView.setBackgroundColor(resources.getColor(R.color.highlightColor))
                            lastSelectedPosition = position
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    checkWord()
                }
            }
            true
        }
    }

    private fun generateGrid() {
        gridData = Array(gridSize) { Array(gridSize) { ' ' } }

        for (word in wordList) {
            placeWord(word.toUpperCase())
        }

        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (gridData[i][j] == ' ') {
                    gridData[i][j] = ('A' + Random.nextInt(26))
                }
            }
        }

        gridAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gridData.flatten().map { it.toString() }) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView = super.getView(position, convertView, parent) as TextView
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.textSize = 20f
                return textView
            }
        }

        wordSearchGrid.numColumns = gridSize
        wordSearchGrid.adapter = gridAdapter
    }

    private fun placeWord(word: String) {
        val wordLength = word.length

        var validPlacement = false
        var attempts = 0

        while (!validPlacement && attempts < 100) {
            val direction = Random.nextInt(2) // 0 for horizontal, 1 for vertical
            val row = Random.nextInt(gridSize)
            val col = Random.nextInt(gridSize)

            val positions = mutableListOf<Pair<Int, Int>>()

            validPlacement = if (direction == 0) {
                // Horizontal placement
                if (col + wordLength <= gridSize) {
                    var valid = true
                    for (i in 0 until wordLength) {
                        if (gridData[row][col + i] != ' ' && gridData[row][col + i] != word[i]) {
                            valid = false
                            break
                        }
                    }
                    if (valid) {
                        for (i in 0 until wordLength) {
                            gridData[row][col + i] = word[i]
                            positions.add(Pair(row, col + i))
                        }
                    }
                    valid
                } else {
                    false
                }
            } else {
                // Vertical placement
                if (row + wordLength <= gridSize) {
                    var valid = true
                    for (i in 0 until wordLength) {
                        if (gridData[row + i][col] != ' ' && gridData[row + i][col] != word[i]) {
                            valid = false
                            break
                        }
                    }
                    if (valid) {
                        for (i in 0 until wordLength) {
                            gridData[row + i][col] = word[i]
                            positions.add(Pair(row + i, col))
                        }
                    }
                    valid
                } else {
                    false
                }
            }

            if (validPlacement) {
                wordPositions[word] = positions
            }

            attempts++
        }
    }

    private fun displayWordList() {
        val wordListString = StringBuilder()

        for (word in wordList) {
            wordListString.append(word).append("\n")
        }
        wordListText.text = wordListString.toString()
    }

    private fun checkWord() {
        val foundWord = selectedWord.toString()
        val foundIndex = wordList.indexOf(foundWord)
        if (foundIndex != -1) {
            Toast.makeText(this, "Found word: $foundWord", Toast.LENGTH_SHORT).show()
            highlightFoundWord(foundWord)

            val mutableWordList = wordList.toMutableList()
            mutableWordList.removeAt(foundIndex)
            wordList = mutableWordList

            displayWordList() // Update the displayed word list
            amountofwords--
            gameOver(amountofwords)
        } else {
            Toast.makeText(this, "Not a valid word: $foundWord", Toast.LENGTH_SHORT).show()
            clearHighlightedCharacters()
            lastSelectedPosition = -1
            displayWordList()
        }
    }

    private fun highlightFoundWord(word: String) {
        val positions = wordPositions[word]
        if (positions != null) {
            for (position in positions) {
                val (row, col) = position
                val gridPosition = row * gridSize + col
                val textView = wordSearchGrid.getChildAt(gridPosition) as TextView
                textView.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun clearHighlightedCharacters() {
        for (i in 0 until wordSearchGrid.childCount) {
            val textView = wordSearchGrid.getChildAt(i) as? TextView
            textView?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    fun exitActivity(view: View) {
        if(amountofwords>0)
        {
            val data = Intent()
            data.putExtra("user_won", false)
            setResult(RESULT_OK, data)
        }
        finish()
    }

    private fun gameOver(amountofwords: Int) {
        if (amountofwords <= 0) {
            wordListText.text = "Congratulations YOU WON"
            //Toast.makeText(this, "Congratulations, you won the game!", Toast.LENGTH_SHORT).show()
            val data = Intent()
            data.putExtra("user_won", true)
            setResult(RESULT_OK, data)

            Handler(Looper.getMainLooper()).postDelayed({
                finish() // Close the activity after the delay
            }, 2000) // Delay in milliseconds (2000ms = 2 seconds)
        }
    }
}