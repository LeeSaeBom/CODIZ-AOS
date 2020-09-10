package com.example.mobile

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile.service.ProblemService
import kotlinx.android.synthetic.main.activity_problem_step.*
import okhttp3.OkHttpClient

class ProblemStepActivity : AppCompatActivity() {

    private lateinit var problemService: ProblemService
    private var problemId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_step)

        problemId = intent.getLongExtra("problemId", 0)
        problemService = ProblemService(OkHttpClient(), this)

        init()
    }

    private fun init() {
        val problemFrameResponse = ProblemFrameAsyncTask(problemId, problemService).execute().get()
        if (problemFrameResponse == null) {
            Toast.makeText(this, "유효하지 않은 문제입니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val problemFrame = problemFrameResponse.problemFrame
            problemName.text = problemFrame.problemName
            problemDescription.text = problemFrame.problemFrameDescription
            problem_start_btn.setOnClickListener {
                val intent = Intent(applicationContext, ProblemActivity::class.java)
                intent.putExtra("problemId", problemId)
                startActivity(intent)
                finish()
            }
        }
    }

    class ProblemFrameAsyncTask(
        private val problemId: Long,
        private val problemService: ProblemService
    ) : AsyncTask<Unit, Unit, ProblemFrameResponse?>() {

        override fun doInBackground(vararg params: Unit?): ProblemFrameResponse? {
            return problemService.getProblemFrame(problemId)
        }
    }
}
