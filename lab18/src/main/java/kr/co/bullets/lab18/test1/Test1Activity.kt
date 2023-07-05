package kr.co.bullets.lab18.test1

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kr.co.bullets.lab18.R
import kr.co.bullets.lab18.databinding.ActivityTest1Binding

class Test1Activity : AppCompatActivity() {
    lateinit var telephonyManager: TelephonyManager

    private fun callChanged() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyManager.registerTelephonyCallback(
                mainExecutor,
                object : TelephonyCallback(), TelephonyCallback.CallStateListener {

                    override fun onCallStateChanged(state: Int) {
                        when (state){
                            TelephonyManager.CALL_STATE_IDLE -> {
                                Log.d("kkang", "idle...")
                            }
                            TelephonyManager.CALL_STATE_OFFHOOK -> {
                                Log.d("kkang", "offhook...")
                            }
                            TelephonyManager.CALL_STATE_RINGING -> {
                                Log.d("kkang", "ringing...")
                            }
                        }
                    }
                }
            )
        } else {
            val listener = object : PhoneStateListener() {
                override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                    when (state) {
                        TelephonyManager.CALL_STATE_IDLE -> {
                            Log.d("kkang", "idle...")
                        }
                        TelephonyManager.CALL_STATE_OFFHOOK -> {
                            Log.d("kkang", "offhook...")
                        }
                        TelephonyManager.CALL_STATE_RINGING -> {
                            Log.d("kkang", "ringing...")
                        }
                    }
                }
            }
            telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun phoneInfo(){
        // 국가정보
        val countryIso = telephonyManager.networkCountryIso
        // 이통사정보
        val operatorName = telephonyManager.networkOperatorName


        var phoneNumber = "unKnown"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val subscriptionManager = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            for(subscriptionInfo: SubscriptionInfo in subscriptionManager.activeSubscriptionInfoList) {
                val activeSubscriptionId: Int = subscriptionInfo.subscriptionId
                phoneNumber = subscriptionManager.getPhoneNumber(activeSubscriptionId)
                Log.d("kkang", "$phoneNumber")
            }
        } else {
            phoneNumber = telephonyManager.line1Number
        }
        Log.d("kkang", "$countryIso, $operatorName, $phoneNumber")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if(it.all { permission -> permission.value == true }){
                callChanged()
                phoneInfo()
            } else {
                Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") ==
            PackageManager.PERMISSION_GRANTED) {
            callChanged()
            phoneInfo()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    "android.permission.READ_PHONE_NUMBERS",
                    "android.permission.READ_PHONE_STATE"
                )
            )
        }
    }
}