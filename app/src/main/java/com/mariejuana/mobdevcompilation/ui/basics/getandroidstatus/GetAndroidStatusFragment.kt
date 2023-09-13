package com.mariejuana.mobdevcompilation.ui.basics.getandroidstatus

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

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentAndroidStatusBinding

class GetAndroidStatusFragment : Fragment() {

    private var _binding: FragmentAndroidStatusBinding? = null
    private val binding get() = _binding!!

    private lateinit var androidStatusViewModel: GetAndroidStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAndroidStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPrintStatus

        // Initialize the ViewModel
        androidStatusViewModel = ViewModelProvider(this).get(GetAndroidStatusViewModel::class.java)

        // Observe the LiveData from the ViewModel to get Android device information
        androidStatusViewModel.text.observe(viewLifecycleOwner) { androidStatusText ->
            textView.text = androidStatusText
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}