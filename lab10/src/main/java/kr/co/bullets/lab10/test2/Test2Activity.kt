package kr.co.bullets.lab10.test2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import kr.co.bullets.lab10.R
import kr.co.bullets.lab10.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {

    // 내가 알아서 할게
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            // API Level 1
            val toast = Toast.makeText(this, "종료하려면 한번 더 누르세요...", Toast.LENGTH_SHORT)
            // API Level 30
            toast.addCallback(object : Toast.Callback() {
                override fun onToastHidden() {
                    super.onToastHidden()
                    Log.d("kkang", "toast hidden...")
                }

                override fun onToastShown() {
                    super.onToastShown()
                    Log.d("kkang", "toast show...")
                }
            })
            toast.show()
        }

        binding.button2.setOnClickListener {
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Log.d("kkang", "data: $year, $month, $dayOfMonth")
                }
            }, 2022, 10, 3).show()
        }

        binding.button3.setOnClickListener {
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Log.d("kkang", "time: $hourOfDay, $minute")
                }
            }, 15, 0, true).show()
        }
    }
}