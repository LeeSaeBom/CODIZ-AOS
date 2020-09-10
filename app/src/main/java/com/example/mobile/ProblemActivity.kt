package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mobile.service.ProblemService
import kotlinx.android.synthetic.main.problem_layout.*
import okhttp3.OkHttpClient


class ProblemActivity : AppCompatActivity() {

    private var problemId: Long = 0
    private lateinit var problemService: ProblemService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)
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
            viewPager.adapter =
                ProblemViewPageAdapter(this, problemFrameResponse.problems, problemId)
        }
    }

    class ProblemViewPageAdapter(
        private val context: Context,
        private val problems: List<Problem>,
        private val problemId: Long
    ) : PagerAdapter() {

        private val saveAnswer: Array<Int> = Array(problems.size) { 0 }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (position == problems.size) {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.choose_last, container, false)
                val btn = view.findViewById<Button>(R.id.submit_btn)
                btn.setOnClickListener {
                    val intent = Intent(context, ResultActivity::class.java)
                    var correct = 0
                    for (i in saveAnswer.indices) {
                        if (saveAnswer[i] == problems[i].problemAnswer.toInt()) {
                            correct++
                        }
                    }
                    intent.putExtra("correct", correct)
                    intent.putExtra("total", saveAnswer.size)
                    intent.putExtra("problemId", problemId)
                    context.startActivity(intent)
                    if (context is AppCompatActivity) {
                        context.finish()
                    }
                }
                container.addView(view)
                return view
            } else {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.choose_item, container, false)
                container.addView(view)

                val problemTextView = view.findViewById<TextView>(R.id.problemDescription)
                val problemOneButton = view.findViewById<Button>(R.id.problem_description_one)
                val problemTwoButton = view.findViewById<Button>(R.id.problem_description_two)
                val problemThreeButton = view.findViewById<Button>(R.id.problem_description_three)
                val problemFourButton = view.findViewById<Button>(R.id.problem_description_four)

                problemTextView.text = problems[position].problemDescription
                problemOneButton.text = problems[position].problemAnswerDescriptionOne
                problemTwoButton.text = problems[position].problemAnswerDescriptionTwo
                problemThreeButton.text = problems[position].problemAnswerDescriptionThree
                problemFourButton.text = problems[position].problemAnswerDescriptionFour

                val btnArray = arrayOf(
                    problemOneButton,
                    problemTwoButton,
                    problemThreeButton,
                    problemFourButton
                )

                for (index in 0 until 4) {
                    btnArray[index].setOnClickListener {
                        btnArray[index].setBackgroundColor(context.getColor(R.color.buttonSelect))
                        saveAnswer[position] = index
                        for (other in 0 until 4) {
                            if (index == other) {
                                continue
                            } else {
                                btnArray[other].setBackgroundColor(context.getColor(R.color.buttonBackground))
                            }
                        }
                    }
                }

                return view
            }
        }

        override fun isViewFromObject(converView: View, `object`: Any): Boolean =
            converView == `object`

        override fun getCount(): Int = problems.size + 1

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (container as ViewPager).removeView(`object` as View?)
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
