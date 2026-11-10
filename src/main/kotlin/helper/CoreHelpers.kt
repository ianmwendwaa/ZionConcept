package helper

import java.util.Locale.getDefault
import kotlin.random.Random

object CoreHelpers {
    fun generateMemberNumber(memberName: String, phoneNumber: String): String{
        // Supposing I have name Ian Mwendwa, phone number 0791930433
        // Generate a unique member number like: ELDR-IANM-0433
        if (memberName.isEmpty()) return "Please provide the new member's name!"

        // Split the name into first name and second name. Now, this will break if no space is provided
        // which is why it HAS to be provided with that space
        val (firstName, secondName) = memberName.split(" ")
        val firstNameSubstr = firstName.substring(0, 3).uppercase(getDefault())
        val secondNameSubstr = secondName.substring(0, 1).uppercase(getDefault())

        // Cover the case where someone has no phone.
        if (phoneNumber.isEmpty()){
            // Generate a sequence of 4 random integers
            val trailingNumbers = mutableListOf<Int>()

            while (trailingNumbers.size < 5){
                trailingNumbers.add(Random.nextInt(0, 9))

                if (trailingNumbers.size == 4) break
            }
            return "ELDR-$firstNameSubstr$secondNameSubstr-${trailingNumbers.joinToString("")}"
        }
        if (phoneNumber.length != 10) return "Phone number is too short or too long. Please ascertain that it is 10 chars long!"
        // Obtain the trailing last 4 phone number digits.
        val trailingPhoneNumberDigits = phoneNumber.takeLast(4)
        return "ELDR-$firstNameSubstr$secondNameSubstr-$trailingPhoneNumberDigits"
    }
    fun validateMemberNumber(redNumber: String): Boolean{
        return false
    }
}