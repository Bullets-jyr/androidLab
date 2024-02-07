package kr.co.bullets.lab10.test1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kr.co.bullets.lab10.R

class Test1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        // 준비
        val requestLauncher = registerForActivityResult(
            // 처리자
            ActivityResultContracts.RequestPermission()
            // 콜백 등록
        ) { isGranted ->
            if (isGranted) {
                Log.d("kkang", "callback... granted...")
            } else {
                Log.d("kkang", "callback... denied...")
            }
        }

        // 확인
        val status = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION")
        if (status == PackageManager.PERMISSION_GRANTED) {
            Log.d("kkang", "granted...")
        } else {
            requestLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
    }
}