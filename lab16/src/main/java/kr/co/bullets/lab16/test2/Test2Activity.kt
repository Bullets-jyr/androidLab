package kr.co.bullets.lab16.test2

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kr.co.bullets.lab16.R
import kr.co.bullets.lab16.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {
    lateinit var binding: ActivityTest2Binding
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    lateinit var intentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            for (entry in it.entries) {
                if (entry.key == "android.permission.READ_CONTACTS" && entry.value) {
                    // 주소록 목록...
                    val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                    intentLauncher.launch(intent)
                } else {
                    Toast.makeText(this, "required permission...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val cursor = contentResolver.query(it!!.data!!.data!!, arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER), null, null, null)

                if (cursor!!.moveToFirst()) {
                    binding.textView.text = "name : ${cursor?.getString(0)}, phone : ${cursor?.getString(1)}"
                }
            }
        }

        binding.button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.READ_CONTACTS"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(arrayOf("android.permission.READ_CONTACTS"))
            } else {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                intentLauncher.launch(intent)
            }
        }
    }
}