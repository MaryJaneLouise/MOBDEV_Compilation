package com.mariejuana.mobdevcompilation.ui.activities.randomquotes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.mariejuana.mobdevcompilation.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FaveFragment : Fragment() {
    private fun clearFavoriteQuote() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("favoriteQuote")
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if there are fragments in the back stack before popping
                if (fragmentManager?.backStackEntryCount!! > 0) {
                    fragmentManager?.popBackStack()
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fave, container, false)
        val buttonGoBack : Button = view.findViewById(R.id.buttonBackToHome)
        val buttonFaveQuote : Button = view.findViewById(R.id.buttonFaveQuote)

        val textViewQuote : TextView = view.findViewById(R.id.text_quote)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favoriteQuote = sharedPreferences.getString("favoriteQuote", "")
        val timestamp = sharedPreferences.getLong("favoriteQuoteTime", 0)

        if (favoriteQuote.isNullOrEmpty()) {
            textViewQuote.text = "There's no current favorite quote."
            buttonFaveQuote.isEnabled = false
        } else {
            val formattedTimestamp = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(
                Date(timestamp)
            )

            textViewQuote.text = "Favorite Quote:\n$favoriteQuote\n\nTimestamp: $formattedTimestamp"
            buttonFaveQuote.isEnabled = true
        }

        buttonGoBack.setOnClickListener{
//            val fragment = MainFragment()
//            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit()

            findNavController().popBackStack()
        }
        buttonFaveQuote.setOnClickListener{
            clearFavoriteQuote()
            Toast.makeText(requireContext(), "The quote has been deleted.", Toast.LENGTH_SHORT).show()            // Update the TextView to show there's no current favorite quote
            textViewQuote.text = "There's no current favorite quote."
        }
        return view
    }
}