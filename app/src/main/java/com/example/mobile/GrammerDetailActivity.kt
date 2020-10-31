package com.example.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grammer_detail.*

class GrammerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammer_detail)

        grammerDescription.text = intent.getStringExtra("description")
        grammerName.text = intent.getStringExtra("grammerName")
    }
}