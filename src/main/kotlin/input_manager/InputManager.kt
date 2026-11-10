package input_manager

class InputManager {
    fun promptStrInput(message: String): String {
        println(message)
        val strInput = readln()
        return strInput
    }
    fun promptIntInput(message: String): Int {
        println(message)
        val intInput = readln()
        if (intInput.isEmpty()) return 0
        return intInput.toInt()
    }
}