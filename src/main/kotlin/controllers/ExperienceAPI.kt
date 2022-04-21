package controllers

import models.Experience

class ExperienceAPI {

    private var experiences = ArrayList<Experience>()

    fun add(experience: Experience): Boolean {
        return experiences.add(experience)
    }

    fun listAllExperiences(): String =
        if (experiences.isEmpty()) "No experiences stored"
        else formatListString(experiences)


    fun listNotYetAchievedExperiences(): String =
        if (numberOfNotYetAchievedExperiences() == 0) "All experiences stored in your Bucket List have been achieved"
        else formatListString( experiences.filter { experience -> !experience.isExperienceAchieved } )

    //helper method to determine how many Not Yet Achieved experiences there are in the Bucket List
    fun numberOfNotYetAchievedExperiences(): Int = experiences.count{experience: Experience -> !experience.isExperienceAchieved}


    fun listAchievedExperiences(): String =
        if (numberOfAchievedExperiences() == 0) "No experiences in your Bucket List have been achieved yet."
        else formatListString( experiences.filter { experience -> experience.isExperienceAchieved } )

    //helper method to determine how many experiences have been achieved
    fun numberOfAchievedExperiences(): Int =  experiences.count {experience: Experience -> experience.isExperienceAchieved}


    fun listExperiencesBySelectedPriority(priority: Int): String =
        if (experiences.isEmpty()) "no experiences stored in your Bucket List"

        else if (numberOfExperiencesByPriority(priority) == 0) "\nNo experiences with priority: $priority"

        else "\n${numberOfExperiencesByPriority(priority)} experiences with priority $priority:\n" +
                experiences.filter { experience -> experience.experiencePriority == priority }
                    .joinToString(separator = "\n") { experience -> experiences.indexOf(experience).toString() + ": " + experience.toString()}



    //helper method to determine how many experiences there are of a specific priority
    fun numberOfExperiencesByPriority(priorityToCheck :Int): Int = experiences.count {experience: Experience -> experience.experiencePriority == priorityToCheck}









    fun numberOfExperiences(): Int {
        return experiences.size
    }

    fun findExperience(index: Int): Experience? {
        return if (isValidListIndex(index, experiences)) {
            experiences[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    //Helper Method
    fun formatListString(experiencesToFormat : List<Experience>) : String =
        experiencesToFormat
            .joinToString (separator = "\n") { experience ->
                experiences.indexOf(experience).toString() + ": " + experience.toString() }




}



