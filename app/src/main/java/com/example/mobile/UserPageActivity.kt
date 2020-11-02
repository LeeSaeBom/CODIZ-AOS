package com.example.mobile

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.service.UserService
import kotlinx.android.synthetic.main.userpage_main.*
import okhttp3.OkHttpClient

class UserPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userpage_main)

        val userId = intent.getStringExtra("userId")
        val user =
            UserReviseActivity.UserAsyncTask(userId, UserService(OkHttpClient(), this)).execute()
                .get()
        if (user != null) {
            user_level.text = (user.userScore / 15 + 1).toString()
        }

        userpage_revise.setOnClickListener {
            val intent = Intent(this, UserReviseActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }


}