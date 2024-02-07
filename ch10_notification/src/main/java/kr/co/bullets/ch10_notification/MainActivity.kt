package kr.co.bullets.ch10_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import kr.co.bullets.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용자에게 퍼미션을 허용을 요청할 때는 ActivityResultLauncher를 이용합니다.
        // 이 클래스는 액티비티에서 결과를 돌려받아야 할 때 사용하며 대표적으로 퍼미션 허용 요청과
        // 다른 액티비티를 실행하고 결과를 돌려받을 때 입니다.
        // ActivityResultLauncher 객체는 registerForActivityResult() 함수를 호출해서 만듭니다.
        // registerForActivityResult 함수는 매개변수가 2개입니다.
        // 첫 번째는 어떤 요청인지를 나타내는 ActivityResultContracts 타입 객체이며 다양한 요청에 대응하는 서브 클래스들이 있습니다.
        // 대표적으로 다른 액티비티를 실행하고 결과를 돌려받을 때는 StartActivityForResult,
        // 퍼미션 허용을 요청할 때는 RequestPermission을 사용합니다.
        // 그리고 registerForActivityResult() 함수의 두 번째 매개변수는 결과를 받았을 때 호출되는 콜백입니다.
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            // 요청 결과는 registerForActivityResult() 함수의 두 번째 매개변수로 등록한 콜백으로 전달됩니다.
            if (it.all { permission -> permission.value == true }) {
                noti()
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.notificationButton.setOnClickListener {
            // Build.VERSION.SDK_INT: 앱이 실행되는 기기의 API 레벨입니다.
            // if 문에서 이 값을 이용해 특정 버전에서만 실행하도록 작성할 수 있습니다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // checkSelfPermission: 사용자가 퍼미션을 허용했는지 확인하려면 checkSelfPermission() 함수를 이용합니다.
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
        // NotificationManager
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Builder
        val builder: NotificationCompat.Builder
        // Notification을 만들려면 NotificationCompat.Builder가 필요한데 빌더를 만드는 방법이 API레벨 26(Android 8) 버전부터 변경되었습니다.
        // Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 정리하자면 NotificationChannel로 알림 채널을 만들고 이 채널 정보를 대입해 NotificationCompat.Builder를 만든 다음,
            // 이 빌더로 Notification 객체를 만듭니다.
            // 이 Notification 객체를 NotificationManager의 notify() 함수에 대입하는 구조입니다.
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel Description"
                setShowBadge(true)

                // 알림음
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)

                // 진동
                enableVibration(true)
            }

            // NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // Builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.drawable.small)
            setWhen(System.currentTimeMillis())
            setContentTitle("류지영")
            setContentText("안녕하세요")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big))
        }

        // RemoteInput
        val KEY_TEXT_REPLY = "key_text_reply"
        val replyLabel = "답장"
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        // BroadcastReceiver
        val replyIntent = Intent(this, ReplyReceiver::class.java)
        // BroadcastReceiver를 의뢰하기 위한 PendingIntent
        val replyPendingIntent = PendingIntent.getBroadcast(this, 30, replyIntent, PendingIntent.FLAG_MUTABLE)

        // RemoteInput 연결
        builder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.send,
                "답장",
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()
        )

        // 알림은 NotificationManager의 notify() 함수로 발생합니다.
        // notify() 함수에는 NotificationCompat.Builder가 만들어 주는 Notification 객체를 대입하며 이 객체에는 알림 정보가 저장됩니다.
        // 그런데 NotificationCompat.Builder를 만들 때 NotificationChannel정보를 대입해 줘야 합니다.
        manager.notify(11, builder.build())
    }
}