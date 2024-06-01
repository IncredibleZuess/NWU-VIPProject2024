/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 */
package com.example.vip_project_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GamesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var selectedGameLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // Initialize the ActivityResultLauncher
        selectedGameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val userWon = data?.getBooleanExtra("user_won", false) ?: false
                if (userWon) {
                    Toast.makeText(requireContext(), "Congratulations, you won the game!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Better luck next time!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        val startWordSearch: Button = view.findViewById(R.id.btnSearchWord)
        val startMath: Button = view.findViewById(R.id.btnMath)
        val startHangman: Button = view.findViewById(R.id.btnHangMan)

        startWordSearch.setOnClickListener {
            val intent = Intent(activity, GameDifficultyActivity::class.java)
            intent.putExtra("game_type", "WordSearch")
            selectedGameLauncher.launch(intent)
        }

        startMath.setOnClickListener {
            val intent = Intent(activity, GameDifficultyActivity::class.java)
            intent.putExtra("game_type", "Math")
            selectedGameLauncher.launch(intent)
        }

        startHangman.setOnClickListener {
            val intent = Intent(activity, GameDifficultyActivity::class.java)
            intent.putExtra("game_type", "Hangman")
            selectedGameLauncher.launch(intent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GamesFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
