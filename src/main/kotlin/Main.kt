import controllers.ExperienceAPI
import models.Experience
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
//private val experienceAPI = ExperienceAPI(XMLSerializer(File("experiences.xml")))
private val experienceAPI = ExperienceAPI(JSONSerializer(File("experiences.json")))


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
    var experienceTitle = readNextLine("Enter a title for the experience: ")
    var experienceDescription = readNextLine("Enter a description for the experience: ")
    var experienceCategory = readNextLine("Enter one of the categories: [Hobby, Concert, Travel, Career, Entertainment, Other] ")
    var dateToAchieve = readNextLine("Enter the date you plan to achieve the experience: ")
    var experiencePriority = readNextInt("Enter a priority [1-low, 2, 3, 4, 5-high]: ")

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
    if (experienceAPI.numberOfExperiences() > 0) {
        val option = readNextInt("""
                  > -----------------------------------------------
                  > |  1) View ALL experiences                    |
                  > |  2) View Not Yet Achieved Experiences       |
                  > |  3) View ACHIEVED experiences               |
                  > |  4) View Most Important Experiences (4-5)   |
                  > |  5) View Least Important Experiences (1-3)  |
                  > |  6) View Experiences by Category            |
                  > -----------------------------------------------
          > ==>>""".trimMargin(">"))


        when (option) {
            1 -> listAllExperiences()
            2 -> listNotYetAchievedExperiences()
            3 -> listAchievedExperiences()
            4 -> listPriority4and5()
            5 -> listPriority1and2and3()
            6 -> listCategoryExperiences()
            else -> println("Invalid option entered $option")
        }


    }
    else {
        println("Option Invalid - No Experiences Stored in your Bucket List")
    }
}

fun listAllExperiences() {
    println(experienceAPI.listAllExperiences())
}

fun listNotYetAchievedExperiences() {
    println(experienceAPI.listNotYetAchievedExperiences())
}

fun listAchievedExperiences() {
    println(experienceAPI.listAchievedExperiences())
}

fun listPriority4and5() {
    println(experienceAPI.listExperiencesBySelectedPriority(5))
    println(experienceAPI.listExperiencesBySelectedPriority(4))
}

fun listPriority1and2and3() {
    println(experienceAPI.listExperiencesBySelectedPriority(3))
    println(experienceAPI.listExperiencesBySelectedPriority(2))
    println(experienceAPI.listExperiencesBySelectedPriority(1))
}

fun listCategoryExperiences() {
    println(experienceAPI.listExperiencesByCategory("Hobby"))
    println(experienceAPI.listExperiencesByCategory("Concert"))
    println(experienceAPI.listExperiencesByCategory("Travel"))
    println(experienceAPI.listExperiencesByCategory("Career"))
    println(experienceAPI.listExperiencesByCategory("Entertainment"))
    println(experienceAPI.listExperiencesByCategory("Other"))


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
    //logger.info { "crossOffExperience() function invoked" }
    println(experienceAPI.listNotYetAchievedExperiences())
    if (experienceAPI.numberOfNotYetAchievedExperiences() > 0) {
        //only ask the user to choose the experience to set to 'Achieved' if not yet achieved ones exist
        val indexToAchieve = readNextInt("Enter the index of the experience you have achieved: ")
        //pass the index of the experience to ExperienceAPI and check for success.
        if (experienceAPI.achieveExperience(indexToAchieve)) {
            println("Well Done on your Achievement!")
        }
        else{
            println("Changing the experience to 'Achieved' was NOT successful.")
        }
    }
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
