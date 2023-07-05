package kr.co.bullets.lab18.test3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.bullets.lab18.R
import kr.co.bullets.lab18.databinding.ActivityTest3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkService = (applicationContext as MyApplication).networkService

        val userListCall = networkService.doGetUserList("1")
        userListCall.enqueue(object : Callback<UserListModel> {
            override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {
                val userList = response.body()

                binding.recyclerView.adapter = MyAdapter(this@Test3Activity, userList?.data)
                binding.recyclerView.addItemDecoration(
                    DividerItemDecoration(this@Test3Activity, LinearLayoutManager.VERTICAL)
                )
            }

            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
                call.cancel()
            }
        })
    }
}