package com.example.mobile.service

import android.content.Context
import com.example.mobile.R
import com.example.mobile.SignInRequest
import com.example.mobile.SignUpRequest
import com.example.mobile.User
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.lang.Exception

class LoginService(
    private val okHttpClient: OkHttpClient,
    context: Context
) {
    private val serverHost =
        "http://" + context.getString(R.string.server_host) + ':' + context.getString(R.string.server_port)

    fun signUp(user: User): Int {
        val signUpRequest = SignUpRequest(user)
        val mediaType = MediaType.get("application/json")
        val requestBody: RequestBody = RequestBody.create(mediaType, Gson().toJson(signUpRequest))

        val request = Request.Builder()
            .url("${serverHost}/user/signUp")
            .post(requestBody)
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            return response.code()
        } catch (e: Exception) {

        }
        return -1
    }

    fun signIn(userId: String, userPassword: String): Int {
        val signInRequest =
            SignInRequest(userId, userPassword)
        val mediaType = MediaType.get("application/json")
        val requestBody: RequestBody = RequestBody.create(mediaType, Gson().toJson(signInRequest))

        val request = Request.Builder()
            .url("${serverHost}/user/signIn")
            .post(requestBody)
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            return response.code()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }
}