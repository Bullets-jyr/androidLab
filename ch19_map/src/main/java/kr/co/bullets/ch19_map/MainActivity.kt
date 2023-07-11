package kr.co.bullets.ch19_map

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    lateinit var providerClient: FusedLocationProviderClient
    lateinit var apiClient: GoogleApiClient
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value == true }){
                apiClient.connect()
            }else {
                Toast.makeText(this, "권한 거부..", Toast.LENGTH_SHORT).show()
            }
        }
        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(this)

        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }else {
            // 위치 제공자 준비하기
            apiClient.connect()
        }

    }

    private fun moveMap(latitude: Double, longitude: Double){
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        // 지도 중심 이동하기
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
        // 마커 옵션
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOptions.position(latLng)
        markerOptions.title("MyLocation")
        // 마커 표시하기
        googleMap?.addMarker(markerOptions)
    }
    // 위치 제공자를 사용할 수 있는 상황일 때
    override fun onConnected(p0: Bundle?) {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                this@MainActivity,
                object: OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let {
                            val latitude = p0.latitude
                            val longitude = p0.longitude
                            // 지도 중심 이동하기
                            moveMap(latitude, longitude)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }


    }

    override fun onConnectionSuspended(p0: Int) {
        // 위치 제공자를 사용할 수 없을 때
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        // 사용할 수 있는 위치 제공자가 없을 때
    }
    // 지도 객체를 이용할 수 있는 상황이 될 때
    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
    }
}