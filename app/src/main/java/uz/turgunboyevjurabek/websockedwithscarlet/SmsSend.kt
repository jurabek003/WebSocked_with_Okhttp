package uz.turgunboyevjurabek.websockedwithscarlet

import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun SmsSend(phone:String,message:String) {
    val smsManager=SmsManager.getDefault()
    val context = LocalContext.current
    try {
        smsManager.sendTextMessage(phone,null,message,null,null)
        Toast.makeText(context, "sms send", Toast.LENGTH_SHORT).show()
    }catch (e:Exception){
        Toast.makeText(context, "sms ketmadi :${e.message}", Toast.LENGTH_SHORT).show()
    }
}