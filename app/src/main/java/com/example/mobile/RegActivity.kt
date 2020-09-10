package com.example.mobile

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile.service.LoginService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_reg.*
import okhttp3.OkHttpClient
import javax.net.ssl.HttpsURLConnection

class RegActivity : AppCompatActivity() {

    private lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        loginService = LoginService(OkHttpClient(), this)

        reg_cancle_btn.setOnClickListener {
            finish()
        }

        login_reg_btn.setOnClickListener {
            if (reg_pw.text.toString() != reg_pwcheck.text.toString()) {
                Snackbar.make(reg_layout, "비밀번호가 다릅니다. 다시 입력해주세요.", Snackbar.LENGTH_SHORT).show()
            } else {
                signUp(
                    User(
                        reg_id.text.toString(),
                        reg_pw.text.toString(),
                        reg_email.text.toString(),
                        reg_name.text.toString()
                    )
                )
            }
        }
    }

    private fun signUp(user: User) {
        val signUpAsyncTask = SignUpAsyncTask(user, loginService)
        val response = signUpAsyncTask.execute().get()
        if (response == HttpsURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else if (response == HttpsURLConnection.HTTP_CONFLICT) {
            Snackbar.make(reg_layout, "해당 유저는 이미 존재합니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    class SignUpAsyncTask(
        private val user: User,
        private val loginService: LoginService
    ) : AsyncTask<Unit, Unit, Int>() {

        override fun doInBackground(vararg params: Unit?): Int {
            return loginService.signUp(user)
        }
    }
}
