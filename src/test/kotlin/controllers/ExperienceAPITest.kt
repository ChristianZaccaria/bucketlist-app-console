package controllers

import models.Experience
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExperienceAPITest {

    private var learnPiano: Experience? = null
    private var summerHoliday: Experience? = null
    private var rockClimb: Experience? = null
    private var graduate: Experience? = null
    private var concert: Experience? = null
    private var populatedExperiences: ExperienceAPI? = ExperienceAPI()
    private var emptyExperiences: ExperienceAPI? = ExperienceAPI()

    @BeforeEach
    fun setup(){
        learnPiano = Experience("Learning to play Piano", "Music is Beautiful","Hobby", "2024-02-03", 5,false)
        summerHoliday = Experience("Summer Holiday to Spain", "I wish to visit Barcelona", "Travel", "2023-06-23", 1, false)
        rockClimb = Experience("Going Rock Climbing", "I have never gone rock climbing", "Entertainment", "2022-06-27",4,  false)
        graduate = Experience("Graduate from WIT", "It is my goal to graduate at WIT", "Career", "2022-04-10",4,true)
        concert = Experience("Ed Sheeran concert", "Go to Ed Sheeran's concert in Dublin", "Concert", "2022-04-29", 3,false)

        //adding 5 Note to the notes api
        populatedExperiences!!.add(learnPiano!!)
        populatedExperiences!!.add(summerHoliday!!)
        populatedExperiences!!.add(rockClimb!!)
        populatedExperiences!!.add(graduate!!)
        populatedExperiences!!.add(concert!!)
    }

    @AfterEach
    fun tearDown(){
        learnPiano = null
        summerHoliday = null
        rockClimb = null
        graduate = null
        concert = null
        populatedExperiences = null
        emptyExperiences = null
    }


    @Nested
    inner class AddExperiences {
        @Test
        fun `adding an Experience to a populated list adds to ArrayList`() {
            val newExperience = Experience("Learning Musical Notes", "Play Beethoven", "Hobby", "2023-02-21", 3, false)
            assertEquals(5, populatedExperiences!!.numberOfExperiences())
            assertTrue(populatedExperiences!!.add(newExperience))
            assertEquals(6, populatedExperiences!!.numberOfExperiences())
            assertEquals(newExperience, populatedExperiences!!.findExperience(populatedExperiences!!.numberOfExperiences() - 1))
        }

        @Test
        fun `adding an Experience to an empty list adds to ArrayList`() {
            val newExperience = Experience("Learning Musical Notes", "Play Beethoven", "Hobby", "2023-02-21", 3, false)
            assertEquals(0, emptyExperiences!!.numberOfExperiences())
            assertTrue(emptyExperiences!!.add(newExperience))
            assertEquals(1, emptyExperiences!!.numberOfExperiences())
            assertEquals(newExperience, emptyExperiences!!.findExperience(emptyExperiences!!.numberOfExperiences() - 1))
        }
    }

    @Nested
    inner class ListExperiences {
        @Test
        fun `listAllExperiences returns No Experiences Stored message when ArrayList is empty`() {
            assertEquals(0, emptyExperiences!!.numberOfExperiences())
            assertTrue(emptyExperiences!!.listAllExperiences().lowercase().contains("no experiences"))
        }

        @Test
        fun `listAllExperiences returns Experiences when ArrayList has experiences stored`() {
            assertEquals(5, populatedExperiences!!.numberOfExperiences())
            val experiencesString = populatedExperiences!!.listAllExperiences().lowercase()
            assertTrue(experiencesString.contains("learning"))
            assertTrue(experiencesString.contains("summer holiday"))
            assertTrue(experiencesString.contains("rock climbing"))
            assertTrue(experiencesString.contains("graduate"))
            assertTrue(experiencesString.contains("ed sheeran"))
        }
    }
}