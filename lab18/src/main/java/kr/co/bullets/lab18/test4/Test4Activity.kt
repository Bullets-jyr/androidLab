package kr.co.bullets.lab18.test4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kr.co.bullets.lab18.R
import kr.co.bullets.lab18.databinding.ActivityTest4Binding

class Test4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Android_logo_2019_%28stacked%29.svg/1200px-Android_logo_2019_%28stacked%29.svg.png1"
        Glide.with(this)
            .load(url)
            .override(200, 200)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(binding.imageView)
    }
}