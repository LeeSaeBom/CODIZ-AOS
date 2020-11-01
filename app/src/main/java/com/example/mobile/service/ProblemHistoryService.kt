package com.example.mobile.service

import android.content.Context
import com.example.mobile.*
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.lang.Exception
import java.util.*


class ProblemHistoryService(
    private val okHttpClient: OkHttpClient,
    context: Context
) {
    private val serverHost =
        "http://" + context.getString(R.string.server_host) + ':' + context.getString(R.string.server_port)

    fun getProblemHistoryList(userId : String): List<ProblemHistory> {
        val request = Request.Builder()
            .header("content-type", "application/json")
            .url("${serverHost}/history/${userId}")
            .get()
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val problemHistoryResponse = Gson().fromJson(
                response.body()?.string(),
                ProblemHistoryResponse::class.java
            )
            return if (response.isSuccessful) {
                problemHistoryResponse.problemHistories
            } else {
                Collections.emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Collections.emptyList()
    }

    fun putProblemHistory(problemHistory: ProblemHistory) {
        val mediaType = MediaType.get("application/json")
        val request = Request.Builder()
            .header("content-type", "application/json")
            .url("${serverHost}/history")
            .post(RequestBody.create(mediaType, Gson().toJson(problemHistory)))
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}