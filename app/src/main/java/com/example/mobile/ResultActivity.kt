package com.example.mobile

import android.content.Intent
import android.content.LocusId
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile.service.ProblemHistoryService
import com.example.mobile.service.ProblemService
import com.example.mobile.service.UserService
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.main_btn
import kotlinx.android.synthetic.main.activity_result.retry_btn
import kotlinx.android.synthetic.main.activity_result3.*
import okhttp3.OkHttpClient

class ResultActivity : AppCompatActivity() {

    private var problemId: Long = 0
    private var userId: String = ""
    private lateinit var problemName: String
    private lateinit var problemType: String
    private var correct: Int = 0
    private var total: Int = 0

    lateinit var problemHistoryService: ProblemHistoryService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result3)

        problemHistoryService = ProblemHistoryService(OkHttpClient(), this)
        correct = intent.getIntExtra("correct", 0)
        total = intent.getIntExtra("total", 0)

        problemId = intent.getLongExtra("problemId", 0)
        userId = intent.getStringExtra("userId")
        problemName = intent.getStringExtra("problemName")
        problemType = intent.getStringExtra("problemType")

        if (correct == 1) {
            txtScore.text = "정답입니다."
            ProblemHistoryAsyncTask(
                ProblemHistory(
                    userId,
                    problemId,
                    problemName,
                    ProblemType.valueOf(problemType),
                    true
                ),
                problemHistoryService
            ).execute()
            UserUpdateScoreAsyncTask(userId, 1, UserService(OkHttpClient(), this)).execute().get()
        } else {
            txtScore.text = "틀렸습니다."
            ProblemHistoryAsyncTask(
                ProblemHistory(
                    userId,
                    problemId,
                    problemName,
                    ProblemType.valueOf(problemType),
                    false
                ),
                problemHistoryService
            ).execute()
        }

        retry_btn.setOnClickListener {
            val intent = Intent(this, ProblemActivity::class.java)
            intent.putExtra("problemId", problemId)
            intent.putExtra("problemName", problemName)
            intent.putExtra("problemType", problemType)
            intent.putExtra("userId", userId)
            startActivity(intent)
            finish()
        }
        main_btn.setOnClickListener {
            finish()
        }
    }

    class ProblemHistoryAsyncTask(
        private val problemHistory: ProblemHistory,
        private val problemHistoryService: ProblemHistoryService
    ) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            problemHistoryService.putProblemHistory(problemHistory)
        }
    }

    class UserUpdateScoreAsyncTask(
        private val userId: String,
        private val userScore: Long,
        private val userService: UserService
    ) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            userService.updateScore(userId, userScore)
        }
    }
}
