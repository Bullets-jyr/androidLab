package kr.co.bullets.lab18.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kr.co.bullets.lab18.R
import kr.co.bullets.lab18.databinding.ActivityTest2Binding
import org.json.JSONObject

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)
        binding.button.setOnClickListener {
            val request = JsonObjectRequest(
                Request.Method.GET,
                "https://reqres.in/api/users/2",
                null,
                Response.Listener<JSONObject> { response ->
                    val dataJson = response.getJSONObject("data")
                    val id = dataJson.getString("id")
                    val email = dataJson.getString("email")
                    Log.d("kkang", "$id, $email")
                },
                Response.ErrorListener { error ->
                    Log.d("kkang","error...$error")
                }
            )

            queue.add(request)
        }
    }
}