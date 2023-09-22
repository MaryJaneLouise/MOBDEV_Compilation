package com.mariejuana.mobdevcompilation.ui.lectures.midterms.lecture2

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentMidtermsLecture2Binding

class LM2Fragment : Fragment() , OnClickListener {
    private var _binding: FragmentMidtermsLecture2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMidtermsLecture2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAlarm.setOnClickListener(this)
        binding.buttonSendEmail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            (R.id.buttonAlarm) -> {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, "Test Message")
                    putExtra(AlarmClock.EXTRA_MINUTES, 1)
                }

                if (intent.resolveActivity(requireActivity().packageManager) != null)
                    startActivity(intent)
            }
            (R.id.buttonSendEmail) -> {
                val data = Uri.parse("mailto:")
                var email = arrayOf("jong@gmail.com", "ong@gmail.com")
                var intent = Intent(Intent.ACTION_SENDTO).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_EMAIL, email)
                    putExtra(Intent.EXTRA_SUBJECT, "This is a test.")
                }
                intent.setData(data)

                if (intent.resolveActivity(requireActivity().packageManager) != null)
                    startActivity(intent)

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}