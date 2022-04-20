import utils.ScannerInput
import java.lang.System.exit

fun main(args: Array<String>) {

    runMenu()

}


fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
         > ------------------------------------------
         > |            BUCKET LIST APP             |
         > ------------------------------------------
         > | BUCKET LIST MENU                       |
         > |   1) Add an Experience                 |
         > |   2) List Experiences                  |
         > |   3) Update an Experience              |
         > |   4) Delete an Experience              |
         > |   5) Cross Item Off your Bucket List   |
         > |   6) Search for an added Experience    |
         > |  20) Save Experiences                  |
         > |  21) Load Experiences                  |
         > ------------------------------------------
         > |   0) Exit                              |
         > ------------------------------------------
         > ==>>""".trimMargin(">"))
}


fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1 -> addExperience()
            2 -> listExperiences()
            3 -> updateExperience()
            4 -> deleteExperience()
            5 -> crossOffExperience()
            6 -> searchExperiences()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}


fun addExperience(){
    println("You chose Add Experience")
}

fun listExperiences(){
    println("You chose List Experiences")
}

fun updateExperience(){
    println("You chose Update Experience")
}

fun deleteExperience(){
    println("You chose Delete Experience")
}

fun crossOffExperience(){
    println("You chose Cross Off Experience")
}

fun searchExperiences(){
    println("You chose Search for Added Experiences")
}


fun save() {
    println("Saved to File")
}

fun load() {
    println("Loaded from File")
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}