package com.example.mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        userId = intent.getStringExtra("userId")

        problemButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        grammerButton.setOnClickListener {
            val intent = Intent(this, GrammerActivity::class.java)
            startActivity(intent)
        }

        solved_problems.setOnClickListener {
            val intent = Intent(this, SolvedProblemActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}