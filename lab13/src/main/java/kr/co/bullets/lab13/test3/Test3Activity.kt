package kr.co.bullets.lab13.test3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.bullets.lab13.R
import kr.co.bullets.lab13.databinding.ActivityTest3Binding

class Test3Activity : AppCompatActivity() {
    var count = 0
    lateinit var binding : ActivityTest3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countButton.setOnClickListener {
            count++
            binding.resultView.text = "$count"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val data = savedInstanceState.getInt("count")
        binding.resultView.text = "$data"
    }
}