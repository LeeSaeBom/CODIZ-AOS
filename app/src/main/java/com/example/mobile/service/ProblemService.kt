package com.example.mobile.service

import android.content.Context
import com.example.mobile.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.*


class ProblemService(
    private val okHttpClient: OkHttpClient,
    context: Context
) {
    private val serverHost =
        "http://" + context.getString(R.string.server_host) + ':' + context.getString(R.string.server_port)

    fun getProblemFrameList(): List<ProblemFrame> {
        val request = Request.Builder()
            .header("content-type", "application/json")
            .url("${serverHost}/problem/list")
            .get()
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val problemFrameListResponse = Gson().fromJson(
                response.body()?.string(),
                ProblemFrameListResponse::class.java
            )
            return if (response.isSuccessful) {
                problemFrameListResponse.problemFrames
            } else {
                Collections.emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Collections.emptyList()
    }

    fun getProblemFrame(problemId: Long): ProblemFrameResponse? {
        val request = Request.Builder()
            .header("content-type", "application/json")
            .url("${serverHost}/problem/${problemId}")
            .get()
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val problemFrameResponse = Gson().fromJson(
                response.body()?.string(),
                ProblemFrameResponse::class.java
            )
            return if (response.isSuccessful) {
                problemFrameResponse
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}