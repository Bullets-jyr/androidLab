package kr.co.bullets.lab19

import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kr.co.bullets.lab19.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var manager: LocationManager

    fun getLocation() {
        // Call requires permission which may be rejected by user: code should explicitly check to see if permission is available (with checkPermission) or explicitly handle a potential SecurityException
        val location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        location?.let {
            // 위도
            val latitude = location.latitude
            // 경도
            val longitude = location.longitude
            // 정확도
            val accuracy = location.accuracy

            Log.d("kkang", "$latitude, $longitude, $accuracy")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
            if (it) {
                getLocation()
            } else {
                Toast.makeText(this, "denied..", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else {
            permissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
    }
}