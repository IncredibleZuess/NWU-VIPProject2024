/**
 * @author Liam Craven
 * @editor Sebastian Klopper
 *
 * A pop up input to enter a PIN to access admin features
 */

package com.example.vip_project_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context

import android.widget.EditText
import androidx.fragment.app.DialogFragment

class PinDailogFragment : DialogFragment() {
    private var listener: PinDialogListener? = null

    interface PinDialogListener {
        fun onPinEntered(pin: String)
    }

    /**
     * Override the onAttach method to instantiate the listener
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as? PinDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement PinDialogListener")
        }
    }

    /**
     * Override the onCreateDialog method to create the dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_pin_dailog, null)
            val pinEditText = view.findViewById<EditText>(R.id.pinEditText)

            builder.setView(view)
                .setTitle("Enter PIN")
                .setPositiveButton("OK") { _, _ ->
                    val pin = pinEditText.text.toString()
                    listener?.onPinEntered(pin)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}