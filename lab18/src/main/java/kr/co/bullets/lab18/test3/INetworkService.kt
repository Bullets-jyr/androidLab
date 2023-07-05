package kr.co.bullets.lab18.test3

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface INetworkService {
    @GET("api/users")
    fun doGetUserList(@Query("page") page: String): Call<UserListModel>

    @GET
    fun getAvatarImage(@Url url: String): Call<ResponseBody>
}