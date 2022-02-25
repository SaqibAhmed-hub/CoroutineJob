package com.example.coroutinejob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_job.setOnClickListener {
            callbackAPI()
        }
    }

    private fun callbackAPI() {

        val started = System.currentTimeMillis()

        val parentJob = CoroutineScope(Dispatchers.IO).launch {
            val job1 = launch {
                val time1 = measureTimeMillis {
                    val result1 = callAPI1()
                    setTextOnMainThread(result1)
                }
                println("The job1 time taken : $time1")
            }
            val job2 = launch {
                val time2 = measureTimeMillis {
                    val result2 = callAPI2()
                    setTextOnMainThread(result2)
                }
                println("The job2 time taken : $time2")
            }
        }
        parentJob.invokeOnCompletion {
            println("The total time taken : ${System.currentTimeMillis() - started}ms")
        }
    }

    private fun setNewText(input: String) {
        val newText = text_view.text.toString() + "\n$input"
        text_view.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Dispatchers.Main) {
            setNewText(input)
        }

    }

    private suspend fun callAPI2(): String {
        delay(1000)
        val data2 = ApiService.getService().getData1()
        data2.let {
            return if (it.isSuccessful) {
                val lastname = callName(it.body())
                lastname
            } else {
                "Unsuccessful#2"
            }
        }

    }

    private suspend fun callAPI1(): String {
        delay(2000)
        val data1 = ApiService.getService().getData()
        data1.let {
            return if (it.isSuccessful) {
                val firstname = callName(it.body())
                firstname
            } else {
                "Unsuccessful#1"
            }
        }
    }

    private fun callName(body: employee?): String {
        val name = body?.data?.first_name
        return name!!
    }
}