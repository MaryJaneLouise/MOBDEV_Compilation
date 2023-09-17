package com.mariejuana.mobdevcompilation.ui.lectures.midterms.lecture1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.core.content.ContextCompat
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentMidtermsLecture1Binding

class LM1Fragment : Fragment() {

    private var _binding: FragmentMidtermsLecture1Binding? = null
    private var selection: Int = -1
    private var hasSelected: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMidtermsLecture1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbTen.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selection = 1
                hasSelected = true
                binding.btnCalculateTip.isEnabled = true
            }
        }

        binding.rbTwenty.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selection = 2
                hasSelected = true
                binding.btnCalculateTip.isEnabled = true
            }
        }

        binding.rbThirty.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selection = 3
                hasSelected = true
                binding.btnCalculateTip.isEnabled = true
            }
        }

        binding.btnCalculateTip.setOnClickListener {

            if(binding.edtTotalCharge.text.isNullOrEmpty()){
                binding.edtTotalCharge.error = "Total Charge required"
                return@setOnClickListener
            }

            if(hasSelected){
                binding.txtTotalTip.text = "${calculateTip(binding.edtTotalCharge.text.toString().toDouble())}"
            }

        }
    }
    private fun calculateTip(amount: Double): Double{
        return when(selection){
            (1) -> (amount * .10)
            (2) -> (amount * .20)
            (3) -> (amount * .30)
            else -> 0.0
        }
    }

    fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            (R.id.rb_ten) -> selection = 1
            (R.id.rb_twenty) -> selection = 2
            (R.id.rb_thirty) -> selection = 3
        }
        hasSelected = true
        binding.btnCalculateTip.isEnabled = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}