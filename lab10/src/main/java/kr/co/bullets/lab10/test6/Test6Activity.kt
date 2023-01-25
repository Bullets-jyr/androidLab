package kr.co.bullets.lab10.test6

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import kr.co.bullets.lab10.R
import kr.co.bullets.lab10.databinding.ActivityTest6Binding

class Test6Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { permission -> permission.value == true}) {
                noti()
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                } else {
                    permissionLauncher.launch(arrayOf("android.permission.POST_NOTIFICATIONS"))
                }
            } else {
                noti()
            }
        }
    }

    fun noti() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("one", "one channel", NotificationManager.IMPORTANCE_LOW)
            channel.description = "one description"
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, "one")
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("Title")
        builder.setContentText("TEXT")

        val person1 = Person.Builder()
            .setName("kkang")
            .setIcon(IconCompat.createWithResource(this, R.drawable.person1))
            .build()

        val person2 = Person.Builder()
            .setName("ryu")
            .setIcon(IconCompat.createWithResource(this, R.drawable.person2))
            .build()

        val message1 = NotificationCompat.MessagingStyle.Message("hello", System.currentTimeMillis(), person1)
        val message2 = NotificationCompat.MessagingStyle.Message("world", System.currentTimeMillis(), person2)

        val messageStyle = NotificationCompat.MessagingStyle(person1)
            .addMessage(message1)
            .addMessage(message2)

        builder.setStyle(messageStyle)

        manager.notify(11, builder.build())
    }
}