package com.example.vip_project_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vip_project_1.GameHangManActivity
import com.example.vip_project_1.GameMathActivity
import com.example.vip_project_1.GameWordSearchActivity

class GameDifficultyActivity : AppCompatActivity() {

    private lateinit var gameResultLauncher: ActivityResultLauncher<Intent>
    private val GAME_RESULT_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_difficulty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnStart)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rbGroup = findViewById<RadioGroup>(R.id.Dificulty)
        val startButton: Button = findViewById(R.id.btnStart)
        startButton.visibility = Button.INVISIBLE

        rbGroup.setOnCheckedChangeListener { _, checkedId ->
            val btn = findViewById<RadioButton>(checkedId)
            startButton.visibility =
                if (btn != null && (btn.text == "Easy" || btn.text == "Medium" || btn.text == "Hard"))
                {
                    Button.VISIBLE
                }
                else
                {
                    Button.INVISIBLE
                }
        }

        val gameType = intent.getStringExtra("game_type")
        if (gameType != null)
        {
            Toast.makeText(this, "Game Type: $gameType", Toast.LENGTH_SHORT).show()
        }

        gameResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(GAME_RESULT_REQUEST_CODE, result.resultCode, result.data)
        }

        startButton.setOnClickListener {
            val selectedRdoId: Int = rbGroup.checkedRadioButtonId
            val btn = findViewById<RadioButton>(selectedRdoId)
            if (btn != null)
            {
                val intent = when (gameType) {
                    "WordSearch" -> Intent(this, GameWordSearchActivity::class.java)
                    "Math" -> Intent(this, GameMathActivity::class.java)
                    "Hangman" -> Intent(this, GameHangManActivity::class.java)
                    else -> null
                }
                intent?.let {
                    it.putExtra("difficulty_level", btn.text.toString())
                    gameResultLauncher.launch(it)
                }
            }
        }
    }

    @Deprecated("This function will be updated in future")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GAME_RESULT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val userWon = data?.getBooleanExtra("user_won", false) ?: false
                val resultIntent = Intent().apply {
                    putExtra("user_won", userWon)
                }
                setResult(RESULT_OK, resultIntent)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }
}
