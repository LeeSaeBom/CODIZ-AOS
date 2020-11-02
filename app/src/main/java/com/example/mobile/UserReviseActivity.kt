package com.example.mobile

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.service.UserService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_userrevise.*
import okhttp3.OkHttpClient

class UserReviseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userrevise)

        val userId = intent.getStringExtra("userId")
        val userService = UserService(OkHttpClient(), this)
        val user = UserAsyncTask(userId, userService).execute().get()
        if (user != null) {
            update_pw.setText(user.userPassword)
            update_pwcheck.setText(user.userPassword)
            update_email.setText(user.userEmail)
            update_name.setText(user.userName)
        }

        update_bnt.setOnClickListener {
            if (user != null) {
                if (update_pw.text.toString() != update_pwcheck.text.toString()) {
                    Snackbar.make(revise_layout, "같은 비밀번호를 입력해주세요.", 1000).show()
                    return@setOnClickListener
                }
                UserUpdateAsyncTask(
                    userId,
                    User(
                        userId,
                        update_pw.text.toString(),
                        update_name.text.toString(),
                        update_email.text.toString(),
                        user.userScore
                    ),
                    userService
                ).execute().get()
                finish()
            }
        }

        update_cancle_bnt.setOnClickListener {
            finish()
        }
    }

    class UserAsyncTask(
        private val userId: String,
        private val userService: UserService
    ) : AsyncTask<Unit, Unit, User?>() {

        override fun doInBackground(vararg params: Unit?): User? {
            return userService.findByUserId(userId)
        }
    }

    class UserUpdateAsyncTask(
        private val userId: String,
        private val user: User,
        private val userService: UserService
    ) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            userService.updateUser(userId, user)
        }
    }
}