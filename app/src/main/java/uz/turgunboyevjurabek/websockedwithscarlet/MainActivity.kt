package uz.turgunboyevjurabek.websockedwithscarlet

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import uz.turgunboyevjurabek.websockedwithscarlet.ui.theme.WebSockedWithScarletTheme

const val SMS_PERMISSION_REQUEST_CODE = 100

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebSockedWithScarletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context= LocalContext.current
                    val smsPermissionState = remember { mutableStateOf(ContextCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS)) }

                    if (smsPermissionState.value== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(context, "ruhsat berilgan", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "ruhsat suraladi", Toast.LENGTH_SHORT).show()
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.SEND_SMS),
                            SMS_PERMISSION_REQUEST_CODE
                        )
                    }



                    WebSocketDemo()

                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (permissions.isNotEmpty() && permissions[0] == android.Manifest.permission.SEND_SMS && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("jj2","ruhsat berildi")
                // Bu yerda kerakli harakatlarni bajaring
            } else {
                // Ruxsat rad etildi
                Log.d("jj2","ruhsat berilmadi")
                // Bu yerda kerakli harakatlarni bajaring
            }
        }
    }
}
