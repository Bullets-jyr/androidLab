package kr.co.bullets.lab18.test3

import com.google.gson.annotations.SerializedName
import kr.co.bullets.lab18.test3.UserModel

data class UserListModel(
    var page: String,
    @SerializedName("per_page")
    var perPage: String,
    var total: String,
    @SerializedName("total_pages")
    var totalPages: String,
    var data: List<UserModel>?
)