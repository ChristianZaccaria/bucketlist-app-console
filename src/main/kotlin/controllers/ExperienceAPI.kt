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



