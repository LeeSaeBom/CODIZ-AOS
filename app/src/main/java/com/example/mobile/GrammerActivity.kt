package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.service.GrammerService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class GrammerActivity : AppCompatActivity() {

    val groupList = GrammerType.values()
    private val grammerList = ArrayList<Grammer>()

    lateinit var grammerService: GrammerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammer)
        grammerService = GrammerService(OkHttpClient(), this)
        init()
    }

    private fun init() {
        val grammers = GrammerAsyncTask(grammerService).execute().get()
        if (grammers == null) {
            Toast.makeText(this, "유효하지 않은 문법입니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val grammerArray = Array<ArrayList<Grammer>>(groupList.size) { ArrayList() }
            for (grammer in grammers) {
                val index = groupList.indexOf(grammer.grammerType)
                grammerArray[index].add(grammer)
            }
            expanded_menu.setAdapter(GrammerExpandAdapter(groupList, grammerArray, this))
        }
    }

    class GrammerExpandAdapter(
        private val groupList: Array<GrammerType>,
        private val grammerList: Array<ArrayList<Grammer>>,
        private val context: Context
    ) : BaseExpandableListAdapter() {

        override fun getGroupCount(): Int = groupList.size

        override fun getChildrenCount(groupPosition: Int): Int {
            return grammerList[groupPosition].size
        }

        override fun getGroup(groupPosition: Int): Any = groupList[groupPosition]

        override fun getChild(groupPosition: Int, childPosition: Int): Any =
            grammerList[groupPosition][childPosition]

        override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

        override fun getChildId(groupPosition: Int, childPosition: Int): Long =
            childPosition.toLong()

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
            textView.text = groupList[groupPosition].grammerName
            return view
        }

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

            val stepButton = view.findViewById<Button>(R.id.stepButton)
            stepButton.text = grammerList[groupPosition][childPosition].grammerName
            stepButton.setOnClickListener {
                val intent = Intent(context, GrammerDetailActivity::class.java)
                intent.putExtra(
                    "grammerName",
                    grammerList[groupPosition][childPosition].grammerName
                )
                intent.putExtra(
                    "description",
                    grammerList[groupPosition][childPosition].description
                )
                context.startActivity(intent)
            }

            return view
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
    }

    class GrammerAsyncTask(
        private val grammerService: GrammerService
    ) : AsyncTask<Unit, Unit, List<Grammer>>() {

        override fun doInBackground(vararg params: Unit?): List<Grammer> {
            return grammerService.getGrammerList()
        }
    }
}