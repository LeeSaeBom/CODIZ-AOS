package com.example.mobile.service

import android.content.Context
import com.example.mobile.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.*


class GrammerService(
    private val okHttpClient: OkHttpClient,
    context: Context
) {
    private val serverHost =
        "http://" + context.getString(R.string.server_host) + ':' + context.getString(R.string.server_port)

    fun getGrammerList(): List<Grammer> {
        val request = Request.Builder()
            .header("content-type", "application/json")
            .url("${serverHost}/grammer/list")
            .get()
            .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            val grammerResponse= Gson().fromJson(
                response.body()?.string(),
                GrammerResponse::class.java
            )
            return if (response.isSuccessful) {
                grammerResponse.grammers
            } else {
                Collections.emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Collections.emptyList()
    }
}