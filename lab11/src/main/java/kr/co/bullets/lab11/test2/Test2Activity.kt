package kr.co.bullets.lab11.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.bullets.lab11.OneFragment
import kr.co.bullets.lab11.R
import kr.co.bullets.lab11.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = OneFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.fragment_content, fragment)
        transaction.commit()
    }
}