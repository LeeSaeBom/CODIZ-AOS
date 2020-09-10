package com.example.mobile

import android.content.Intent
import android.content.LocusId
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private var problemId: Long = 0
    private var correct: Int = 0
    private var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        problemId = intent.getLongExtra("problemId", 0L)
        correct = intent.getIntExtra("correct", 0)
        total = intent.getIntExtra("total", 0)

        scoreText.text = ("${correct}점 입니다.")

        retry_btn.setOnClickListener {
            val intent = Intent(this, ProblemActivity::class.java)
            intent.putExtra("problemId", problemId)
            startActivity(intent)
            finish()
        }
        main_btn.setOnClickListener {
            finish()
        }
    }

}
