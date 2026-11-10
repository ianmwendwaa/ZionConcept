package backend

import helper.CoreHelpers
import input_manager.InputManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.MemberData
import models.NewMemberData
import java.util.Locale.getDefault

class MemberRecordsManager(private val inputManager: InputManager) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }
    fun addMember(){
        val newMemberName = inputManager.promptStrInput("Enter new member's name: ")
        var newMembersPhoneNumber = inputManager.promptStrInput("Phone number: ")

        if (newMembersPhoneNumber.isEmpty()){
            val proceed = inputManager.promptStrInput("You did not provide a phone number.\n" +
                    "If you proceed, the system will generate a pseudo number for $newMemberName's member number.\n " +
                    "Proceed? (Y/N): ").lowercase(getDefault())

            if (proceed != "y"){
                newMembersPhoneNumber = inputManager.promptStrInput("Phone number: ")
            }
        }
        // Generate the member number only after the user ascertains that the phone number was intentionally left
        // blank
        val newMembersUniqueNumber = CoreHelpers.generateMemberNumber(
            memberName = newMemberName,
            phoneNumber = newMembersPhoneNumber
        )

        // Serialize and commit the data to the server and consequently the database
        val newMemberData = NewMemberData(
            name = newMemberName, phoneNumber = newMembersPhoneNumber, genNumber = newMembersUniqueNumber
        )
        val serializedData = json.encodeToString(newMemberData)
        println("Data going to the server: ")
        println(serializedData)
    }
    fun viewMembers(): List<MemberData>{
        return emptyList()
    }
    fun deleteMember(){
        // Delete using the member number
        val redNumber = inputManager.promptStrInput("Enter member number of the member you wish to delete: ")
        if (redNumber.isEmpty()) println("Please provide a member number.")



    }
    fun updateMember(){

    }
}