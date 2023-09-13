package com.mariejuana.mobdevcompilation.ui.basics.printname

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentPrintnameBinding

class PrintNameFragment : Fragment() {

    private var _binding: FragmentPrintnameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val printNameViewModel =
            ViewModelProvider(this).get(PrintNameViewModel::class.java)

        _binding = FragmentPrintnameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPrintName
        printNameViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val textViewPrintName = view.findViewById<TextView>(R.id.text_print_name)

        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the TextView with the text from EditText as the user types
                textViewPrintName.text = s
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}