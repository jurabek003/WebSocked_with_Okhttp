
package uz.turgunboyevjurabek.websockedwithscarlet

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@Composable
fun WebSocketDemo() {
    val context= LocalContext.current
    // Websocket ulanishini saqlash uchun state
    var webSocket: WebSocket? by remember { mutableStateOf(null) }

    val client = OkHttpClient()

    val messages = remember { mutableStateListOf<String>() }

    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LaunchedEffect(Unit) {
            val request = Request.Builder().url("wss://free.blr2.piesocket.com/v3/1?api_key=iHWcLIKRFox5pcEto7xRs6LZztFdHjaSukLUiqEB")
                .build()
            val listener = object : WebSocketListener() {
                override fun onMessage(webSocket: WebSocket, text: String) {
                    GlobalScope.launch(Dispatchers.Main) {
                        messages.add(text)
                        Toast.makeText(context, "onMassage : $text", Toast.LENGTH_SHORT).show()
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
        ) {
            items(messages.size){
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = messages[it],
                        fontFamily = FontFamily.Serif,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(7.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Cyan,
                    unfocusedContainerColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(15.dp),
                 placeholder = {
                    Text(text = "Massage")
                }
            )

            IconButton(onClick = {
                webSocket?.send(text)
                messages.add(text)
                text=""
                Log.d("LIST77",messages.lastIndex.toString())

            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "send IconButton",
                    tint = Color.Blue
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatUi() {
    WebSocketDemo()

}
