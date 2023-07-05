package kr.co.bullets.lab17.test3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.bullets.lab17.R
import kr.co.bullets.lab17.databinding.ActivityTest3Binding

class Test3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        binding.button1.setOnClickListener {
            sharedPref.edit().run {
                putString("data1", "hello")
                putInt("data2", 10)
                commit()
            }
        }

        binding.button2.setOnClickListener {
            val data1 = sharedPref.getString("data1", "world")
            val data2 = sharedPref.getInt("data2", 0)

            Log.d("kkang", "$data1, $data2")
        }
    }
}