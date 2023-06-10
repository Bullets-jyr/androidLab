package kr.co.bullets.ch14_receiver

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kr.co.bullets.ch14_receiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add......................
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value == true }) {
                val intent = Intent(this, MyReceiver::class.java)
                sendBroadcast(intent)
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!.apply {
            when(getIntExtra(BatteryManager.EXTRA_STATUS, -1)){
                BatteryManager.BATTERY_STATUS_CHARGING -> {
                    when(getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)){
                        BatteryManager.BATTERY_PLUGGED_USB -> {
                            binding.chargingResultView.text = "USB Plugged"
                            binding.chargingImageView.setImageBitmap(
                                BitmapFactory.decodeResource(
                                resources, R.drawable.usb
                            ))
                        }
                        BatteryManager.BATTERY_PLUGGED_AC -> {
                            binding.chargingResultView.text = "AC Plugged"
                            binding.chargingImageView.setImageBitmap(
                                BitmapFactory.decodeResource(
                                resources, R.drawable.ac
                            ))
                        }
                    }

                }
                else -> {
                    binding.chargingResultView.text = "Not Plugged"
                }
            }
            val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = level / scale.toFloat() * 100
            binding.percentResultView.text = "$batteryPct %"
        }

        binding.button.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.POST_NOTIFICATIONS"
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(this, MyReceiver::class.java)
                    sendBroadcast(intent)
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            "android.permission.POST_NOTIFICATIONS"
                        )
                    )
                }
            }else {
                val intent = Intent(this, MyReceiver::class.java)
                sendBroadcast(intent)
            }
        }
    }
}