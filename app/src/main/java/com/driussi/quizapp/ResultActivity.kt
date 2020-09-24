package com.driussi.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        name.text = userName
        val correct = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        val total = intent.getStringExtra(Constants.TOTAL_QUESTIONS)

        score.text = "Score is $correct out of $total"


        btn_finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}