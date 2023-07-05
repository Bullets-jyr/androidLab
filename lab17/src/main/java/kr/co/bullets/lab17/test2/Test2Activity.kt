package kr.co.bullets.lab17.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.bullets.lab17.R
import kr.co.bullets.lab17.databinding.ActivityTest2Binding
import java.io.File

class Test2Activity : AppCompatActivity() {
    lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            file = File(filesDir, "test.txt")
            val writeStream = file.writer()
            writeStream.write("Bullets")
            writeStream.flush()
        }

        binding.button2.setOnClickListener {
            val readStream = file.reader().buffered()
            readStream.forEachLine {
                Log.d("kkang", "$it")
            }
        }
    }
}