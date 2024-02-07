package kr.co.bullets.lab10.test5

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kr.co.bullets.lab10.databinding.ActivityTest5Binding

class Test5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // API Level 33
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            // 모든 permission이 허락이 되었을 경우
            if (it.all { permission -> permission.value == true}) {
                noti()
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            // API Level 33 이상
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                } else {
                    permissionLauncher.launch(arrayOf("android.permission.POST_NOTIFICATIONS"))
                }
            // API Level 33 미만
            } else {
                noti()
            }
        }
    }

    fun noti() {
        // 1) 시스템
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 2) 빌더
        val builder: NotificationCompat.Builder

        // 3) 채널 개념
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("one", "one channel", NotificationManager.IMPORTANCE_LOW)
            channel.description = "one description"
            // 채널을 시스템에 등록합니다.
            manager.createNotificationChannel(channel)
            // 채널을 적용한 빌더
            builder = NotificationCompat.Builder(this, "one")
        } else {
            // 빌더
            builder = NotificationCompat.Builder(this)
        }

        // 4) 빌더 세팅
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("Title")
        builder.setContentText("TEXT")

        manager.notify(11, builder.build())
    }
}