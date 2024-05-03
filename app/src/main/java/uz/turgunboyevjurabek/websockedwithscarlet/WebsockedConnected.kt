@file:OptIn(DelicateCoroutinesApi::class)

package uz.turgunboyevjurabek.websockedwithscarlet

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import java.util.regex.Pattern
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@Composable
fun WebsocketConnected(messages:SnapshotStateList<ChatMessage>): WebSocket? {
    val context = LocalContext.current
    val client = OkHttpClient()
    // Websocket ulanishini saqlash uchun state
    var webSocket: WebSocket? by remember { mutableStateOf(null) }
    var phone = remember { mutableStateOf("") }


    // Pie Host api
    //wss://free.blr2.piesocket.com/v3/1?api_key=iHWcLIKRFox5pcEto7xRs6LZztFdHjaSukLUiqEB
    // uzimiziki
//    wss://chat.ziyo.me/chat/room
    val request = Request.Builder().url("wss://chat.ziyo.me/chat/room")
        .build()

    LaunchedEffect(Unit) {
        val listener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val chatMessage= ChatMessage(0,text.toString())
                messages.add(chatMessage)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "onMassage : $text", Toast.LENGTH_SHORT).show()
                     phone.value=text.toString()
                }
            }
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "okey : $response", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "onFailure : ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "onClosing ->  code: $code , reason: $reason" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "onClosed ->  code: $code , reason: $reason" , Toast.LENGTH_SHORT).show()
                }
            }
        }
        webSocket=client.newWebSocket(request, listener)
    }

    if(phone.value!=""){
        SmsSend(phone =phone.value , message ="salom nima gap" )
    }else{
        Toast.makeText(context, "sms ketmadi " +
                "Telefon raqamni +998909999999 formatida kiriting", Toast.LENGTH_SHORT).show()
    }



    return webSocket
}

fun isPhoneNumber(input: String): Boolean {
    val phoneRegex = "^\\+?\\d{9,15}$"
    return Pattern.matches(phoneRegex, input)
}
