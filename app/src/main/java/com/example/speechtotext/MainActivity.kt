package com.example.speechtotext

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var resultext : TextView
    lateinit var imageButton: ImageButton

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultext = findViewById(R.id.textView)
        imageButton = findViewById(R.id.imageButton)

        // must to register Activity Result Launcher in oncreate mthd to work
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                result ->
                val resultCode = result.resultCode
                val data = result.data

                if(resultCode == RESULT_OK && data != null){

                    val speakResult : ArrayList<String> = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                    resultext.text= speakResult[0]

                }

            })

        imageButton.setOnClickListener{
            convertSpeech()
        }

    }

    fun convertSpeech(){

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

       // startActivityForResult(intent,1) -> this method is now deprecated but it v=can be also use to display text
        activityResultLauncher.launch(intent)

    }
// when we use first method then only this code will be used to display text
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
//
//            val speakResult : ArrayList<String> = data
//                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
//
//            resultext.text= speakResult[0]
//
//        }
//
//    }

}