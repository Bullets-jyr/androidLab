package kr.co.bullets.lab16.test1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.bullets.lab16.R
import kr.co.bullets.lab16.databinding.ActivityTest1Binding

class Test1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val cursor = contentResolver.query(Uri.parse("content://com.my.provider"), null, null, null, null)
            while (cursor?.moveToNext() ?: false) {
                Log.d("kkang", cursor?.getString(0) ?: "")
            }
        }
    }
}