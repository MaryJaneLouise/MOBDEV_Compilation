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
import com.mariejuana.mobdevcompilation.R

class SharedFragment(var typeQuote : Int) : Fragment() {
    private lateinit var textViewQuote: TextView
    private var randomQuote: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun saveFavoriteQuote(quote: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val timestamp = System.currentTimeMillis()
        val quoteKey = "favoriteQuote"
        val timestampClock = "favoriteQuoteTime"

        editor.putString(quoteKey, quote)
        editor.putLong(timestampClock, timestamp)

        editor.apply()
    }

    private val quotes_love = arrayOf(
        "Love is not finding someone to live with; it's finding someone you can't imagine living without.\n - Unknown",
        "The best thing to hold onto in life is each other.\n - Audrey Hepburn",
        "Love is composed of a single soul inhabiting two bodies.\n - Aristotle",
        "You know you're in love when you can't fall asleep because reality is finally better than your dreams.\n - Dr. Seuss",
        "Love doesn't make the world go 'round. Love is what makes the ride worthwhile.\n - Franklin P. Jones",
        "Love is when the other person's happiness is more important than your own.\n - H. Jackson Brown Jr.",
        "Love is an endless act of forgiveness. Forgiveness is the key to action and freedom.\n - Maya Angelou",
        "To love and be loved is to feel the sun from both sides.\n - David Viscott",
        "Love is like the wind, you can't see it, but you can feel it.\n - Nicholas Sparks",
        "Love is an irresistible desire to be irresistibly desired.\n - Robert Frost"
    )

    private val quotes_inspiration = arrayOf(
        "Success is not final, failure is not fatal: It is the courage to continue that counts.\n - Winston Churchill",
        "The only way to do great work is to love what you do.\n - Steve Jobs",
        "The future belongs to those who believe in the beauty of their dreams.\n - Eleanor Roosevelt",
        "In the middle of every difficulty lies opportunity.\n - Albert Einstein",
        "Believe you can and you're halfway there.\n - Theodore Roosevelt",
        "The best preparation for tomorrow is doing your best today.\n - H. Jackson Brown Jr.",
        "Success is walking from failure to failure with no loss of enthusiasm.\n - Winston Churchill",
        "Don't watch the clock; do what it does. Keep going.\n - Sam Levenson",
        "Time is gold when watching bold.\n - Chupaghetti Salsalani",
        "Ignorance is bliss when you are living butt to tip.\n - Jerjerking Salsalero"
    )

    private val quotes_funny = arrayOf(
        "I'm writing a book. I've got the page numbers done.\n - Steven Wright",
        "Behind every great man, there is a woman rolling her eyes.\n - Jim Carrey",
        "I used to play piano by ear, but now I use my hands.\n - Steven Wright",
        "I don't suffer from insanity. I enjoy every minute of it.\n - Unknown",
        "I'm on the whiskey diet. I've lost three days already.\n - Tommy Cooper",
        "I'm not arguing, I'm just explaining why I'm right.\n - Unknown",
        "I'm not lazy, I'm just on my energy-saving mode.\n - Unknown",
        "I'm not saying I'm Wonder Woman, I'm just saying no one has ever seen me and Wonder Woman in the same room together.\n - Unknown",
        "Life is not a bed of roses neither it is full of thorns. Prosperity in every field of life never comes to you on its own. YOU HAVE TO STRUGGLE HARD TO GET TO IT.\n - My Daddae Herbs"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shared, container, false)
        val buttonRandomQuote : Button = view.findViewById(R.id.buttonRoll)
        val buttonGoBack : Button = view.findViewById(R.id.buttonBackToHome)
        val buttonFaveQuote : Button = view.findViewById(R.id.buttonFaveQuote)

        val textViewQuote : TextView = view.findViewById(R.id.text_quote)
        val textViewTypeQuote : TextView = view.findViewById(R.id.text_typeQuote)

        when (typeQuote) {
            1 -> {
                val randomIndex = (quotes_love.indices).random()
                val randomQuote = quotes_love[randomIndex]

                textViewTypeQuote.text = "Love Quotes"
                textViewQuote.text = randomQuote

                buttonRandomQuote.setOnClickListener{
                    val randomIndex = (quotes_love.indices).random()
                    val randomQuote = quotes_love[randomIndex]

                    textViewQuote.text = randomQuote
                }

                buttonFaveQuote.setOnClickListener{
                    saveFavoriteQuote(randomQuote)
                    Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                val randomIndex = (quotes_inspiration.indices).random()
                val randomQuote = quotes_inspiration[randomIndex]

                textViewTypeQuote.text = "Inspirational Quotes"
                textViewQuote.text = randomQuote

                buttonRandomQuote.setOnClickListener{
                    val randomIndex = (quotes_inspiration.indices).random()
                    val randomQuote = quotes_inspiration[randomIndex]

                    textViewQuote.text = randomQuote
                }

                buttonFaveQuote.setOnClickListener{
                    saveFavoriteQuote(randomQuote)
                    Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                }
            }
            3 -> {
                val randomIndex = (quotes_funny.indices).random()
                val randomQuote = quotes_funny[randomIndex]

                textViewTypeQuote.text = "Funny Quotes"
                textViewQuote.text = randomQuote

                buttonRandomQuote.setOnClickListener{
                    val randomIndex = (quotes_funny.indices).random()
                    val randomQuote = quotes_funny[randomIndex]

                    textViewQuote.text = randomQuote
                }

                buttonFaveQuote.setOnClickListener{
                    saveFavoriteQuote(randomQuote)
                    Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }

        buttonGoBack.setOnClickListener{
            val fragment = MainFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_main, fragment)?.commit()
        }
        return view
    }
}