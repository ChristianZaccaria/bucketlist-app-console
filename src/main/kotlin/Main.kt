import controllers.ExperienceAPI
import models.Experience
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
private val experienceAPI = ExperienceAPI(XMLSerializer(File("experiences.xml")))


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
    //logger.info { "updateExperience() function invoked" }
    listExperiences()
    if (experienceAPI.numberOfExperiences() > 0) {
        //only ask the user to choose the experience if there are any in the Bucket List
        val indexToUpdate = readNextInt("Enter the index of the experience to update: ")
        if (experienceAPI.isValidIndex(indexToUpdate)) {
            var experienceTitle = readNextLine("Enter a title for the experience: ")
            var experienceDescription = readNextLine("Enter a description for the experience: ")
            var experienceCategory = readNextLine("Enter one of the categories: [Hobby, Concert, Travel, Career, Entertainment, Other] ")
            var dateToAchieve = readNextLine("Enter the date you plan to achieve the experience: ")
            var experiencePriority = readNextInt("Enter a priority [1-low, 2, 3, 4, 5-high]: ")

            //pass the index of the experience and the new experience details to ExperienceAPI for updating and check for success.
            if (experienceAPI.updateExperience(indexToUpdate, Experience(experienceTitle, experienceDescription, experienceCategory, dateToAchieve, experiencePriority, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no experiences by the index number entered")
        }
    }
}

fun deleteExperience(){
    //logger.info { "deleteExperience() function invoked" }
    listExperiences()
    if (experienceAPI.numberOfExperiences() > 0) {
        //only ask the user to choose the experience to delete if there are any in Bucket List
        val indexToDelete = readNextInt("Enter the index of the experience to delete: ")
        //pass the index of the experience to ExperienceAPI for deleting and check for success.
        val experienceToDelete = experienceAPI.deleteExperience(indexToDelete)
        if (experienceToDelete != null) {
            println("Delete Successful! Deleted experience: ${experienceToDelete.experienceTitle}")
        }
        else {
            println("Delete NOT Successful")
        }
    }
}

fun crossOffExperience(){
    logger.info { "crossOffExperience() function invoked" }
}

fun searchExperiences(){
    logger.info { "searchExperiences() function invoked" }
}


fun save() {
    try {
        experienceAPI.store()
        println("Successfully Saved to File")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        experienceAPI.load()
        println("Successfully Loaded from File")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
        logger.info { "exitApp() function invoked" }
        exit(0)
    }
