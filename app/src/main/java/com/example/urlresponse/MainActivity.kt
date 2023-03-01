package com.example.urlresponse

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    val url: String = "https://www.baidu.com"

    var regex_url: String = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"

    var et_1: EditText? = null;
    var et_2: EditText? = null;
    var et_3: EditText? = null;
    var et_4: EditText? = null;
    var et_5: EditText? = null;
    var et_6: EditText? = null;
    var et_7: EditText? = null;
    var et_8: EditText? = null;
    var et_9: EditText? = null;
    var et_10: EditText? = null;
    var tv: TextView? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        et_5 = findViewById(R.id.et_5);
        et_6 = findViewById(R.id.et_6);
        et_7 = findViewById(R.id.et_7);
        et_8 = findViewById(R.id.et_8);
        et_9 = findViewById(R.id.et_9);
        et_10 = findViewById(R.id.et_10);
        tv = findViewById(R.id.tv_1);

        tv?.setOnClickListener {
            getResponseTime()
        }

    }


    fun getResponseTime() {

        var urls: HashMap<EditText, String> = HashMap()
        urls[et_1!!] = getUrl(et_1!!)
        urls[et_2!!] = getUrl(et_2!!)
        urls[et_3!!] = getUrl(et_3!!)
        urls[et_4!!] = getUrl(et_4!!)
        urls[et_5!!] = getUrl(et_5!!)
        urls[et_6!!] = getUrl(et_6!!)
        urls[et_7!!] = getUrl(et_7!!)
        urls[et_8!!] = getUrl(et_8!!)
        urls[et_9!!] = getUrl(et_9!!)
        urls[et_10!!] = getUrl(et_10!!)


        GlobalScope.launch(Dispatchers.Main) {
            urls.forEach {
                Log.i("getResponseTime", "111")
                var time: Long = 0
                withContext(Dispatchers.IO) {
                    time = sendRequestGetResponse(it.value, it.key)

                }
                if (time > 0) {
                    it.key.setText("${it.value}的响应时间是:${time}")
                } else {
                    it.key.setText("${it.key.text}:该url地址有误")
                }
            }
        }
    }


    fun sendRequestGetResponse(urlstr: String, et: EditText): Long {
        if (urlstr == "") {
            return 0
        }
        var url: URL = URL(urlstr)
        var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        var start: Long = System.currentTimeMillis()
        connection.connect()
        var end: Long = System.currentTimeMillis()
        return end - start

    }


    fun getUrl(et: EditText): String {

        if (et.text == null || et.text.isEmpty()) {
            return url
        } else {
            if (et.text.matches(Regex(regex_url))) {
                return et.text.toString()
            } else {
                Toast.makeText(this, "url地址有误", Toast.LENGTH_LONG)
                return ""
            }
        }

    }

}