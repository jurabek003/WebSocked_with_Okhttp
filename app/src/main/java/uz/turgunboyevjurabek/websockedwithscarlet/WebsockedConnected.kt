@file:OptIn(DelicateCoroutinesApi::class)

package uz.turgunboyevjurabek.websockedwithscarlet

import android.util.Log
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
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@Composable
fun WebsocketConnected(messages:SnapshotStateList<GetMessage>): WebSocket? {
    val context = LocalContext.current
    val client = OkHttpClient()
    // Websocket ulanishini saqlash uchun state
    var webSocket: WebSocket? by remember { mutableStateOf(null) }
    var phone = remember { mutableStateOf("") }
    var message = remember { mutableStateOf("") }

    // Pie Host api
    //wss://free.blr2.piesocket.com/v3/1?api_key=iHWcLIKRFox5pcEto7xRs6LZztFdHjaSukLUiqEB
    // uzimiziki
//    wss://chat.ziyo.me/chat/room
    val request = Request.Builder().url("wss://chat.ziyo.me/sms-chat/test")
        .build()

    LaunchedEffect(Unit) {
        val listener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val getMessage=parseWebSocketMessage(text)

                messages.add(getMessage)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "onMassage : $text", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "$getMessage", Toast.LENGTH_SHORT).show()
                     phone.value= getMessage.phone.toString()
                    message.value=getMessage.message.toString()
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
                    Log.d("usha",t.message.toString())
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

    if(isPhoneNumber(phone.value)){
        SmsSend(phone =phone.value , message = message.value)
    }

    return webSocket
}
fun parseWebSocketMessage(jsonString: String): GetMessage {

    // JSON parsingni amalga oshirish uchun JSON decode funksiyasidan foydalaning
    val json = Json { ignoreUnknownKeys = true }  // Tasodifiy kalitlarni e'tiborsiz qoldirish uchun

    return json.decodeFromString<GetMessage>(jsonString)
}

fun isPhoneNumber(input: String): Boolean {
    val phoneRegex = "^\\+?\\d{10,15}$"
    return Pattern.matches(phoneRegex, input)
}
