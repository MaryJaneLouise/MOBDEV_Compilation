package com.mariejuana.mobdevcompilation.ui.activities.randomquotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.ui.activities.randomquotes.FaveFragment
import java.util.Calendar

class MainFragment : Fragment() {
    private lateinit var textViewQuoteDay: TextView
    private lateinit var quoteOfTheDay: String

    fun getQuoteOfTheDay(seed : Long): String {
        val quotes = arrayOf(
            "Love is not finding someone to live with; it's finding someone you can't imagine living without.\n - Unknown",
            "The best thing to hold onto in life is each other.\n - Audrey Hepburn",
            "Love is composed of a single soul inhabiting two bodies.\n - Aristotle",
            "You know you're in love when you can't fall asleep because reality is finally better than your dreams.\n - Dr. Seuss",
            "Love doesn't make the world go 'round. Love is what makes the ride worthwhile.\n - Franklin P. Jones",
            "Love is when the other person's happiness is more important than your own.\n - H. Jackson Brown Jr.",
            "Love is an endless act of forgiveness. Forgiveness is the key to action and freedom.\n - Maya Angelou",
            "To love and be loved is to feel the sun from both sides.\n - David Viscott",
            "Love is like the wind, you can't see it, but you can feel it.\n - Nicholas Sparks",
            "Love is an irresistible desire to be irresistibly desired.\n - Robert Frost",
            "Success is not final, failure is not fatal: It is the courage to continue that counts.\n - Winston Churchill",
            "The only way to do great work is to love what you do.\n - Steve Jobs",
            "The future belongs to those who believe in the beauty of their dreams.\n - Eleanor Roosevelt",
            "In the middle of every difficulty lies opportunity.\n - Albert Einstein",
            "Believe you can and you're halfway there.\n - Theodore Roosevelt",
            "The best preparation for tomorrow is doing your best today.\n - H. Jackson Brown Jr.",
            "Success is walking from failure to failure with no loss of enthusiasm.\n - Winston Churchill",
            "Don't watch the clock; do what it does. Keep going.\n - Sam Levenson",
            "Time is gold when watching bold.\n - Chupaghetti Salsalani",
            "Ignorance is bliss when you are living butt to tip.\n - Jerjerking Salsalero",
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

        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val random = java.util.Random(dayOfYear + seed)

        val randomIndex = random.nextInt(quotes.size)
        return quotes[randomIndex]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val seed = dayOfYear.toLong()

        quoteOfTheDay = getQuoteOfTheDay(seed)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val buttonLove : Button = view.findViewById(R.id.buttonLove)
        val buttonInspiration : Button = view.findViewById(R.id.buttonInspiration)
        val buttonFunny : Button = view.findViewById(R.id.buttonFunny)
        val buttonFave : Button = view.findViewById(R.id.buttonFave)

        textViewQuoteDay = view.findViewById(R.id.text_quote_of_the_day)

        textViewQuoteDay.text = quoteOfTheDay

        buttonLove.setOnClickListener{
            val fragment = SharedFragment(1)
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_main, fragment)?.commit()
        }
        buttonInspiration.setOnClickListener{
            val fragment = SharedFragment(2)
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_main, fragment)?.commit()
        }
        buttonFunny.setOnClickListener{
            val fragment = SharedFragment(3)
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_main, fragment)?.commit()
        }
        buttonFave.setOnClickListener{
            val fragment = FaveFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_main, fragment)?.commit()
        }
        return view
    }
}