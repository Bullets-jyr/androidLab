package kr.co.bullets.lab18.test3

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class UserModel(
    var id: String,
    @SerializedName("first_name")
    var firstName: String,
    var lastName: String,
    var avatar: String,
    var avatarBitmap: Bitmap
)