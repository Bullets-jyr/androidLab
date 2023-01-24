package kr.co.bullets.lab9.test3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowMetrics
import kr.co.bullets.lab9.R
import kr.co.bullets.lab9.databinding.ActivityTest3Binding

class Test3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
            binding.textView.text = "width : ${windowMetrics.bounds.width()}, height: ${windowMetrics.bounds.height()}"
        } else {
            val display = windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display?.getRealMetrics(displayMetrics)
            binding.textView.text = "width : ${displayMetrics.widthPixels}, height: ${displayMetrics.heightPixels}"
        }
    }
}