package com.example.mobile

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.service.UserService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import java.net.HttpURLConnection


class LoginActivity : AppCompatActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        userService = UserService(OkHttpClient(), this)

        login_reg_btn.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInAsyncTask = SignInAsyncTask(
            login_id.text.toString(),
            login_pw.text.toString(),
            userService
        )

        val responseCode = signInAsyncTask.execute().get()

        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            val intent = Intent(this, SelectActivity::class.java)
            intent.putExtra("userId", login_id.text.toString())
            startActivity(intent)
        } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            Snackbar.make(login_layout_id, "아이디를 찾을 수 없습니다.", Snackbar.LENGTH_SHORT).show()
        } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            Snackbar.make(login_layout_id, "비밀번호가 틀렸습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(login_layout_id, "알 수 없는 오류로 인해 로그인에 실패하였습니다.", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    class SignInAsyncTask(
        private val userId: String,
        private val userPassword: String,
        private val userService: UserService
    ) : AsyncTask<Unit, Unit, Int>() {

        override fun doInBackground(vararg params: Unit?): Int {
            return userService.signIn(userId, userPassword)
        }
    }
}
