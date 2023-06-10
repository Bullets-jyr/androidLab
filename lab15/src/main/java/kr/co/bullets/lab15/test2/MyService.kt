package kr.co.bullets.lab15.test2

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    class MyBinder : Binder() {
        fun funA(arg: Int) {
            Log.d("kkang", "funA... $arg")
        }
        fun funB(arg: Int): Int {
            Log.d("kkang", "funB... $arg")
            return arg * arg
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }
}