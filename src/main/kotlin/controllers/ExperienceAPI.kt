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








    //Helper Method
    fun formatListString(experiencesToFormat : List<Experience>) : String =
        experiencesToFormat
            .joinToString (separator = "\n") { experience ->
                experiences.indexOf(experience).toString() + ": " + experience.toString() }




}



