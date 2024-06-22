package com.example.tugasm4_6958

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasm4_6958.databinding.ActivityQuoteBinding

class QuoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuoteBinding

    val listQuotes: MutableMap<String,String> = mutableMapOf(
        "Stephen Hawking" to "Quiet people have the loudest minds.",
        "Napoleon Hill" to "A goal is a dream with a deadline.",
        "Isaac Newton" to "Truth is the offspring of silence and meditation.",
        "Robert Kiyosaki" to "People who avoid failure also avoid success.",
        "Lao Tzu" to "Confidence is the greatest friend.",
        "Colin Powell" to "Excellence is not an exception, it is a prevailing attitude.",
        "Euripides" to "Talk sense to a fool and he calls you foolish.",
        "Marilyn Monroe" to "Fear is stupid. So are regrets.",
        "Jiddu Krishnamurti" to "The constant assertion of belief is an indication of fear.",
        "Charles Dickens" to "A loving heart is the truest wisdom."
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quote = listQuotes.toList().shuffled().first()

        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvQuoteText.text = "\"${quote.second}\""

        binding.tvQuoteAuthor.text = quote.first

        binding.btnQuoteBack.setOnClickListener {
            finish()
        }
    }
}