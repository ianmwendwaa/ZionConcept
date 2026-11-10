package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberData(
    @SerialName("member_name") val name: String,
    @SerialName("member_phone_number") val phoneNumber: String,
    @SerialName("member_number") val memberNumber: String,
)
