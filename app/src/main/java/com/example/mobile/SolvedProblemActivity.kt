package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mobile.service.ProblemHistoryService
import kotlinx.android.synthetic.main.solved_problem.*
import okhttp3.OkHttpClient

class SolvedProblemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solved_problem)
        val problemHistoryService = ProblemHistoryService(OkHttpClient(), this)
        val problemHistories =
            ProblemHistoryAsyncTask(
                intent.getStringExtra("userId"),
                problemHistoryService
            ).execute().get()
        listView.adapter = ProblemHistoryAdapter(problemHistories, this)
    }

    class ProblemHistoryAdapter(
        private val problemHistories: List<ProblemHistory>,
        private val context: Context
    ) : BaseAdapter() {

        override fun getCount(): Int {
            return problemHistories.size
        }

        override fun getItem(position: Int): Any {
            return problemHistories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =
                convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, null, false)
            val problemNameText = view.findViewById<TextView>(R.id.problemName)
            val problemResult = view.findViewById<TextView>(R.id.result)
            val history = problemHistories[position]
            view.setOnClickListener {
                val intent = Intent(context, ProblemActivity::class.java)
                intent.putExtra("problemId", history.problemId)
                intent.putExtra("problemName", history.problemName)
                intent.putExtra("userId", history.userId)
                intent.putExtra("problemType", history.problemType.toString())
                context.startActivity(intent)
            }

            problemNameText.text = problemHistories[position].problemName

            if (problemHistories[position].result) {
                problemResult.text = "맞춤"
            } else {
                problemResult.text = "틀림"
            }
            return view
        }

    }


    class ProblemHistoryAsyncTask(
        private val userId: String,
        private val problemHistoryService: ProblemHistoryService
    ) : AsyncTask<Unit, Unit, List<ProblemHistory>>() {

        override fun doInBackground(vararg params: Unit?): List<ProblemHistory> {
            return problemHistoryService.getProblemHistoryList(userId)
        }
    }
}