package com.example.upload

import android.os.Handler
import android.os.Looper
import java.util.Timer
import java.util.TimerTask

internal object Utils {

    private val handler = Handler(Looper.getMainLooper())
    private const val interval = 100L // 100 ms
    private var elapsedTime = 0L
    private var timer = Timer()

    fun stopTimerTask(callback: () -> Unit) {
        elapsedTime = 0
        timer.cancel()
        callback()
    }

    fun startTimerTask(timeInMillis: Int, callback: (Int) -> Unit) {
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                elapsedTime += interval

                // Update status on the UI thread
                handler.post {
                    callback(elapsedTime.toInt() / 100)
                }

                if (elapsedTime >= timeInMillis) {
                    timer.cancel()
                }
            }
        }
        timer.schedule(timerTask, 0, interval)
    }
}