package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding :ActivityMainBinding
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this@MainActivity, this)

        binding.btnSpeak.setOnClickListener{view ->
            if(binding?.etEnteredText?.text!!.isEmpty()){
                Toast.makeText(this@MainActivity, "Enter a text to speak.",
                    Toast.LENGTH_LONG).show()
            }else{
                speakOut(binding.etEnteredText?.text!!.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.KOREAN)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this@MainActivity, "Woops... Sorry! ERROR!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}