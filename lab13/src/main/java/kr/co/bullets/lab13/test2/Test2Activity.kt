package kr.co.bullets.lab13.test2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.bullets.lab13.R
import kr.co.bullets.lab13.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            val intent = Intent()
            intent.action = "ACTION_EDIT"
            intent.data = Uri.parse("http://www.google.com")
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.77,127.41"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }
}