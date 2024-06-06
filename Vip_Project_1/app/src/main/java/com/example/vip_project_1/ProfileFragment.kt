/**
 * @author Carlo Barnardo
 * @edtior Sebastian Klopper
 */

package com.example.vip_project_1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), PinDailogFragment.PinDialogListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //button about
    private lateinit var btnAbout: Button
    private lateinit var switch: Switch
    private lateinit var dataswitch: Switch

    private var ignoreDataSwitchChange = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * Inflates the layout for this fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    /**
     * Sets up the button click listeners and shared preferences
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAbout = view.findViewById(R.id.btnAbout)
        switch = view.findViewById(R.id.swDM)
        dataswitch = view.findViewById(R.id.swData)


        val sharedPreferences = activity?.getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val nightMode = sharedPreferences?.getBoolean("DarkMode", false) ?: false

        /**
         * Sets the Color scheme to the correct state based on the shared preferences
         */
        switch.isChecked = nightMode
        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor?.putBoolean("DarkMode", true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor?.putBoolean("DarkMode", false)
            }
            editor?.apply()
            activity?.recreate()
        }

        val sharedPreferencesData = activity?.getSharedPreferences("ShowData", Context.MODE_PRIVATE)
        val editorData = sharedPreferencesData?.edit()
        val data = sharedPreferencesData?.getBoolean("ShowData", false) ?: false
        dataswitch.isChecked = data

        dataswitch.setOnCheckedChangeListener { _, isChecked ->
            if (ignoreDataSwitchChange) {
                ignoreDataSwitchChange = false
                return@setOnCheckedChangeListener
            }

            if (isChecked) {
                val pinDialog = PinDailogFragment()
                pinDialog.show(childFragmentManager, "PinDialogFragment")
            } else {
                editorData?.putBoolean("ShowData", false)
                editorData?.apply()
                val intent = Intent("com.example.vip_project_1.SHOW_DATA_CHANGED")
                activity?.sendBroadcast(intent)
            }
        }

        btnAbout.setOnClickListener{
            startActivity(Intent(activity, AboutMeActivity::class.java))
        }

    }

    /**
     * @author Liam Craven
     *
     * Handles the pin authentication for admin access
     */
    override fun onPinEntered(pin: String) {
        //make sure to add the correct pin here
        val correctPin = "000000" // Replace with the actual PIN check logic
        if (pin == correctPin) {
            val sharedPreferencesData =
                activity?.getSharedPreferences("ShowData", Context.MODE_PRIVATE)
            val editorData = sharedPreferencesData?.edit()

            editorData?.putBoolean("ShowData", true)
            editorData?.apply()

            val intent = Intent("com.example.vip_project_1.SHOW_DATA_CHANGED")
            activity?.sendBroadcast(intent)
        } else {
            Toast.makeText(activity, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            ignoreDataSwitchChange = true
            dataswitch.isChecked = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}