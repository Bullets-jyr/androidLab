package kr.co.bullets.lab15.test2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kr.co.bullets.lab15.R
import kr.co.bullets.lab15.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {
    lateinit var serviceBinder: MyService.MyBinder

    val connection = object : ServiceConnection {
        // service: IBinder? - MyService가 전달한 객체
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBinder = service as MyService.MyBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MyService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        binding.button1.setOnClickListener {
            serviceBinder.funA(10)
        }
        binding.button2.setOnClickListener {
            Log.d("kkang", "${serviceBinder.funB(10)}")
        }
    }
}