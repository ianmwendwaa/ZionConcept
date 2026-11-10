package menu_manager

import backend.MemberRecordsManager
import input_manager.InputManager

class MenuManager(private val inputManager: InputManager,
                  private val memberRecordsManager: MemberRecordsManager) {
    private val menuOptions = listOf(
        "1. Add members",
        "2. View members",
        "3. Delete member",
        "4. Update member",
        "5. Record meeting data",
        "6. View meeting history",
        "7. Exit"
    )
    fun printMenu(){
        println(menuOptions.joinToString("\n"))
        val userChoice = inputManager.promptStrInput("What do you want to do?: ")

        when (userChoice) {
            "1" -> memberRecordsManager.addMember()
            "2" -> memberRecordsManager.viewMembers()
            "3" -> memberRecordsManager.deleteMember()
            "4" -> memberRecordsManager.updateMember()
//            "7" ->
            else -> println("Unknown command")
        }
    }
}