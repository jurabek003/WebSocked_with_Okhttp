package uz.turgunboyevjurabek.websockedwithscarlet


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetMessage(
    @SerializedName("message")
    var message: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("user")
    var user: String?
)