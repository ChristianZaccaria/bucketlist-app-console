package controllers

import models.Experience
import persistence.Serializer
import utils.Utilities.isValidListIndex

class ExperienceAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var experiences = ArrayList<Experience>()

    fun add(experience: Experience): Boolean {
        return experiences.add(experience)
    }

    fun listAllExperiences(): String =
        if (experiences.isEmpty()) "No experiences stored"
        else formatListString(experiences)

    fun listNotYetAchievedExperiences(): String =
        if (numberOfNotYetAchievedExperiences() == 0) "All experiences stored in your Bucket List have been achieved"
        else formatListString(experiences.filter { experience -> !experience.isExperienceAchieved })

    // helper method to determine how many Not Yet Achieved experiences there are in the Bucket List
    fun numberOfNotYetAchievedExperiences(): Int = experiences.count { experience: Experience -> !experience.isExperienceAchieved }

    fun listAchievedExperiences(): String =
        if (numberOfAchievedExperiences() == 0) "No experiences in your Bucket List have been achieved yet."
        else formatListString(experiences.filter { experience -> experience.isExperienceAchieved })

    // helper method to determine how many experiences have been achieved
    fun numberOfAchievedExperiences(): Int = experiences.count { experience: Experience -> experience.isExperienceAchieved }

    fun listExperiencesBySelectedPriority(priority: Int): String =
        if (experiences.isEmpty()) "no experiences stored in your Bucket List"

        else if (numberOfExperiencesByPriority(priority) == 0) "\nNo experiences with priority $priority:"

        else "\n${numberOfExperiencesByPriority(priority)} experiences with priority $priority:\n" +
            formatListString(experiences.filter { experience -> experience.experiencePriority == priority })


    // helper method to determine how many experiences there are of a specific priority
    private fun numberOfExperiencesByPriority(priorityToCheck: Int): Int = experiences.count { experience: Experience -> experience.experiencePriority == priorityToCheck }

    fun listExperiencesByCategory(category: String): String =
        if (experiences.isEmpty()) "no experiences stored in your Bucket List"

        else if (numberOfExperiencesByCategory(category) == 0) "\nNo experiences with category: ${category.lowercase()}"

        else "\n${numberOfExperiencesByCategory(category.lowercase())} experiences with category ${category.lowercase()}\n" +
            formatListString(experiences.filter { experience -> experience.experienceCategory.lowercase() == category.lowercase() })


    private fun numberOfExperiencesByCategory(categoryCheck: String): Int = experiences.count { experience: Experience -> experience.experienceCategory.lowercase() == categoryCheck.lowercase() }

    fun deleteExperience(indexToDelete: Int): Experience? {
        return if (isValidListIndex(indexToDelete, experiences)) {
            experiences.removeAt(indexToDelete)
        } else null
    }

    fun updateExperience(indexToUpdate: Int, experience: Experience?): Boolean {
        // find the experience object by the index number
        val foundExperience = findExperience(indexToUpdate)

        // if the experience exists, use the experience details passed as parameters to update the found experience in the ArrayList.
        if ((foundExperience != null) && (experience != null)) {
            foundExperience.experienceTitle = experience.experienceTitle
            foundExperience.experienceDescription = experience.experienceDescription
            foundExperience.experienceCategory = experience.experienceCategory
            foundExperience.dateToAchieve = experience.dateToAchieve
            foundExperience.experiencePriority = experience.experiencePriority

            return true
        }
        // if the experience was not found, return false, indicating that the update was not successful
        return false
    }

    fun numberOfExperiences(): Int {
        return experiences.size
    }

    fun findExperience(index: Int): Experience? {
        return if (isValidListIndex(index, experiences)) {
            experiences[index]
        } else null
    }

    fun achieveExperience(indexToAchieve: Int): Boolean {
        if (isValidIndex(indexToAchieve)) {
            val experienceToAchieve = experiences[indexToAchieve]
            if (!experienceToAchieve.isExperienceAchieved) {
                experienceToAchieve.isExperienceAchieved = true
                return true
            }
        }
        return false
    }

    fun searchByTitle(title: String) =
        formatListString(
            experiences.filter { experience -> experience.experienceTitle.lowercase().contains(title.lowercase()) }
        )

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, experiences)
    }

    // Helper Method
    private fun formatListString(experiencesToFormat: List<Experience>): String =
        experiencesToFormat
            .joinToString(separator = "\n") { experience ->
                experiences.indexOf(experience).toString() + ": " + experience.toString()
            }

    @Throws(Exception::class)
    @Suppress("UNCHECKED_CAST")
    fun load() {
        experiences = serializer.read() as ArrayList<Experience>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(experiences)
    }
}
