import controllers.ExperienceAPI
import models.Experience
import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
private val experienceAPI = ExperienceAPI()


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
    //logger.info { "addExperience() function invoked" }
    var experienceTitle = ScannerInput.readNextLine("Enter a Title for the experience: ")
    var experienceDescription = ScannerInput.readNextLine("Enter a Description for the experience: ")
    var experienceCategory = ScannerInput.readNextLine("Enter the Category of the experience: ")
    var dateToAchieve = ScannerInput.readNextLine("Enter the Date to achieve the experience: ")
    var experiencePriority = ScannerInput.readNextInt("Enter priority (1-low, 2, 3, 4, 5-high): ")

    val isAdded = experienceAPI.add(Experience(experienceTitle, experienceDescription, experienceCategory, dateToAchieve, experiencePriority, false))

    if (isAdded) {
        println("Added Successfully")
    }
    else {
        println("Add Failed")
    }
}

fun listExperiences(){
    //logger.info { "listExperiences() function invoked" }
    println(experienceAPI.listAllExperiences())
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