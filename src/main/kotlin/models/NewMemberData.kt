package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewMemberData(
    @SerialName("new_member_name") val name: String,
    @SerialName("new_member_phone_num") val phoneNumber: String,
    @SerialName("new_member_num") val genNumber: String,
)
