package utils

object Utilities {

    //utility method to determine if an index is valid in a list.
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun isValidText(input: String): Boolean {
        return (input != "")
    }

}