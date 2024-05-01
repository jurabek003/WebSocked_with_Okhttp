package uz.turgunboyevjurabek.websockedwithscarlet

import android.util.Log
import okhttp3.*
import okio.ByteString

// Websocket Callback (yangiliklarni olish uchun)
class MyWebSocketListener : WebSocketListener() {
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        Log.d("onOpen",response.message)
//        // Ulash muvaffaqiyatli bo'lganda
//    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)

    }
    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("onMessage",text)
        // Matnli xabarni olish
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Baytlarni olish
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        // Websocket yopilishi
        Log.d("onClosing","yopildi")
//        webSocket.close(1000, null) // To'g'ri yopish
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("onFailure",t.message.toString())
        // Xatolik yuz berganda
    }


}

// Websocket yaratish va ulash
fun connectWebSocket() {
    val client = OkHttpClient()

    val request = Request.Builder().url("wss://chat.ziyo.me/chat").build()
    val listener = MyWebSocketListener()

    val webSocket = client.newWebSocket(request, listener)

    // OkHttp resurslarini tozalash
    client.dispatcher.executorService.shutdown()
}
