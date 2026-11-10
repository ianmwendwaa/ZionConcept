import backend.MemberRecordsManager
import helper.CoreHelpers
import input_manager.InputManager
import menu_manager.MenuManager

fun main() {
    val inputManager = InputManager()
    val memberRecordsManager = MemberRecordsManager(inputManager)
    MenuManager(inputManager, memberRecordsManager).printMenu()
}