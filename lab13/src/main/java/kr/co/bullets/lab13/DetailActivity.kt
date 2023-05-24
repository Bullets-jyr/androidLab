package kr.co.bullets.lab13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.bullets.lab13.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data1 = intent.getStringExtra("data1")
        val data2 = intent.getIntExtra("data2", 0)

        binding.detailTextView.text = "$data1, $data2"

        binding.detailButton.setOnClickListener {
            intent.putExtra("result", "world")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}