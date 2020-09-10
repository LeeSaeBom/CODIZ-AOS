package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.TextView
import com.example.mobile.service.ProblemService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var problemService: ProblemService
    private lateinit var baseProblemFrames: List<ProblemFrame>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        problemService = ProblemService(OkHttpClient(), this)

        initList()

    }

    private fun initList() {
        baseProblemFrames = ProblemListAsyncTask(problemService).execute().get()
        val problemTypes = ProblemType.values()
        val problemFrames = Array<ArrayList<ProblemFrame>>(problemTypes.size) { ArrayList() }

        for (problemFrame in baseProblemFrames) {
            val index = problemTypes.indexOf(problemFrame.problemType)
            problemFrames[index].add(problemFrame)
        }

        expanded_menu.setAdapter(ProblemExpandAdapter(problemFrames, this))
    }

    class ProblemExpandAdapter(
        private val problemFrames: Array<ArrayList<ProblemFrame>>,
        private val context: Context
    ) : BaseExpandableListAdapter() {

        override fun getGroup(groupPosition: Int): Any = problemFrames[groupPosition]

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

        override fun hasStableIds(): Boolean = true

        override fun getGroupView(
            groupPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val view =
                convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.problem_group, parent, false)

            val textView = view.findViewById<TextView>(R.id.groupName)
            textView.text = ProblemType.values()[groupPosition].problemName
            return view
        }

        override fun getChildrenCount(groupPosition: Int): Int = problemFrames[groupPosition].size

        override fun getChild(groupPosition: Int, childPosition: Int): Any =
            problemFrames[groupPosition][childPosition]

        override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

        override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val view =
                convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.problem_button, parent, false)

            val button = view.findViewById<Button>(R.id.stepButton)
            button.text = problemFrames[groupPosition][childPosition].problemName
            button.setOnClickListener {
                val intent = Intent(context, ProblemStepActivity::class.java)
                intent.putExtra("problemId", problemFrames[groupPosition][childPosition].id)
                context.startActivity(intent)
            }
            return view
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long =
            childPosition.toLong()

        override fun getGroupCount(): Int = problemFrames.size
    }

    class ProblemListAsyncTask(
        private val problemService: ProblemService
    ) : AsyncTask<Unit, Unit, List<ProblemFrame>>() {

        override fun doInBackground(vararg params: Unit?): List<ProblemFrame> {
            return problemService.getProblemFrameList()
        }
    }
}
