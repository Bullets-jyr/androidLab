package kr.co.bullets.lab17.test1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.bullets.lab17.R
import kr.co.bullets.lab17.databinding.ActivityTest1Binding

class Test1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = openOrCreateDatabase("testdb", Context.MODE_PRIVATE, null)

        try {
            db.execSQL("create table USER_TB (" +
                    "_id integer primary key autoincrement," +
                    "name not null," +
                    "phone)")

            db.execSQL("insert into USER_TB (name, phone) values (?,?)", arrayOf("kkang", "01000011"))
            db.execSQL("insert into USER_TB (name, phone) values (?,?)", arrayOf("kim", "01000022"))
        } catch (e: Exception) {

        }

        binding.button.setOnClickListener {
            val cursor = db.rawQuery("select * from USER_TB", null)
            while (cursor.moveToNext()) {
                val name = cursor.getString(0)
                val phone = cursor.getString(1)
                Log.d("kkang", "$name, $phone")
            }
        }
    }
}