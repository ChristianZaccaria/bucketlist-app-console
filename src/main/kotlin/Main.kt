import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit

private val logger = KotlinLogging.logger {}


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
    logger.info { "addExperience() function invoked" }
}

fun listExperiences(){
    logger.info { "listExperiences() function invoked" }
}

fun updateExperience(){
    logger.info { "updateExperience() function invoked" }
}

fun deleteExperience(){
    logger.info { "deleteExperience() function invoked" }
}

fun crossOffExperience(){
    logger.info { "crossOffExperience() function invoked" }
}

fun searchExperiences(){
    logger.info { "searchExperiences() function invoked" }
}


fun save() {
    logger.info { "save() function invoked" }
}

fun load() {
    logger.info { "load() function invoked" }
}

fun exitApp(){
    logger.info { "exitApp() function invoked" }
    exit(0)
}