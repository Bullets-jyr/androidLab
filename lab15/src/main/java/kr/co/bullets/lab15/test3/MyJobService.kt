package kr.co.bullets.lab15.test3

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log

class MyJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("kkang", "JobService...onCreate....")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("kkang", "JobService...onDestroy....")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        val param = params?.extras?.getString("extra_data")
        Log.d("kkang", "JobService...onStartJob....$param...")

        Thread(Runnable {
            var sum = 0
            for (i in 1..3) {
                sum += i
                SystemClock.sleep(1000)
            }

            Log.d("kkang", "JobService...onStartJob....thread :: $sum...")
            jobFinished(params, false)
        }).start()

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("kkang", "JobService...onStopJob....")
        return false
    }
}