package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class GrammerActivity : AppCompatActivity() {

    val groupList = arrayOf("조건문", "반복문", "함수")
    private val grammerList = arrayOf(
        arrayOf("if", "switch"),
        arrayOf("for", "while"),
        arrayOf("함수 선언")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammer)

        expanded_menu.setAdapter(GrammerExpandAdapter(groupList, grammerList, this))
    }

    class GrammerExpandAdapter(
        private val groupList: Array<String>,
        private val grammerList: Array<Array<String>>,
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
            textView.text = groupList[groupPosition]
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
            stepButton.text = grammerList[groupPosition][childPosition]

            return view
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
    }
}