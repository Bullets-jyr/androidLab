package kr.co.bullets.lab18.test3

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    var networkService: INetworkService

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    init {
        networkService = retrofit.create(INetworkService::class.java)
    }
}