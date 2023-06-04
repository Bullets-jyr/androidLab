package kr.co.bullets.lab13.test5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kr.co.bullets.lab13.R
import kr.co.bullets.lab13.databinding.ActivityTest5Binding
import kotlin.system.measureNanoTime

class Test5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val channel = Channel<Int>()

            val scope = CoroutineScope(Dispatchers.Default + Job())
            scope.launch {
                var sum = 0L
                var time = measureNanoTime {
                    for(i in 1..2_000_000_000){
                        sum += i
                    }
                }
                channel.send(sum.toInt())
            }

            val mainScope = GlobalScope.launch(Dispatchers.Main) {
                channel.consumeEach {
                    binding.textView.text = "sum : $it"
                }
            }
        }
    }
}