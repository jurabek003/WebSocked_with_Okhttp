
package uz.turgunboyevjurabek.websockedwithscarlet

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

@Composable
fun WebSocketDemo() {
    val context= LocalContext.current

    val messages = remember { mutableStateListOf<ChatMessage>() }

    var edtText by remember {
        mutableStateOf("")
    }
    var getNumber by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val webSocket=WebsocketConnected(messages = messages)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.95f)
        ) {
            items(messages.size){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .align(
                                if (messages[it].userID == 1)
                                    Alignment.End
                                else
                                    Alignment.Start
                            )
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = messages[it].message,
                                fontFamily = FontFamily.Serif,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(7.dp),
                                textAlign = if (messages[it].userID==1) TextAlign.End
                                else TextAlign.Start

                            )
                        }
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = edtText,
                onValueChange = {
                    edtText = it
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
                },
                modifier = Modifier
                    .weight(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
//            SendSmsButton(phoneNumber = "905602976", message = "12345 test")

            IconButton(
                onClick = {
                webSocket?.send(edtText)
                    val chatMessage=ChatMessage(1,edtText)
                messages.add(chatMessage)
                edtText=""
                Log.d("LIST77",messages.lastIndex.toString())
            },
                modifier = Modifier
                    .weight(0.2f)) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "send IconButton",
                    tint = Color.Blue
                )
            }
        }

    }
}

@Composable
fun SendSmsButton(phoneNumber: String, message: String) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            val smsUri = Uri.parse("sms:$phoneNumber")
            val intent = Intent(Intent.ACTION_VIEW, smsUri)
            intent.putExtra("sms_body", message)
            context.startActivity(intent)
        }) {
            Text("SMS yuborish")
        }
    }
}
@Preview(showSystemUi = true)
@Composable
private fun ChatUi() {
    WebSocketDemo()

}
