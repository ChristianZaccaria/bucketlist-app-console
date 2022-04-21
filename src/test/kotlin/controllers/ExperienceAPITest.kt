package controllers

import models.Experience
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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
        rockClimb = Experience("Going Rock Climbing", "I have never gone rock climbing", "Hobby", "2022-06-27",4,  false)
        graduate = Experience("Graduate from WIT", "It is my goal to graduate at WIT", "Career", "2022-04-10",4,true)
        concert = Experience("Ed Sheeran concert", "Go to Ed Sheeran's concert in Dublin", "Concert", "2022-04-29", 3,false)

        //adding 5 Experiences to the experience api
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

    @Nested
    inner class NotYetAchievedExperiences {
        @Test
        fun `listNotYetAchievedExperiences returns 'All experiences stored in your Bucket List have been achieved' when ArrayList is empty`(){
            Assertions.assertEquals(0, emptyExperiences!!.numberOfNotYetAchievedExperiences())
            assertTrue(emptyExperiences!!.listNotYetAchievedExperiences().lowercase().contains("all experiences stored in your bucket list have been achieved"))
        }

        @Test
        fun `listNotYetAchievedExperiences returns the pending experiences to achieve stored in ArrayList`(){
            Assertions.assertEquals(4, populatedExperiences!!.numberOfNotYetAchievedExperiences())
            val NotYetExperiencesString = populatedExperiences!!.listNotYetAchievedExperiences().lowercase()
            assertTrue(NotYetExperiencesString.contains("learning to play piano"))
            assertTrue(NotYetExperiencesString.contains("rock climbing"))
            assertTrue(NotYetExperiencesString.contains("summer holiday"))
            assertFalse(NotYetExperiencesString.contains("graduate"))
            assertTrue(NotYetExperiencesString.contains("ed sheeran"))
        }
    }

    @Nested
    inner class AchievedExperiences {
        @Test
        fun `listAchievedExperiences returns 'No experiences in your Bucket List have been achieved yet' when ArrayList is empty`() {
            Assertions.assertEquals(0, emptyExperiences!!.numberOfAchievedExperiences())
            assertTrue(emptyExperiences!!.listAchievedExperiences().lowercase().contains("no experiences in your bucket list have been achieved yet."))
        }


        @Test
        fun `listAchievedExperiences returns Achieved Experiences stored in ArrayList`() {
            Assertions.assertEquals(1, populatedExperiences!!.numberOfAchievedExperiences())
            val achievedExperiencesString = populatedExperiences!!.listAchievedExperiences().lowercase()
            assertFalse(achievedExperiencesString.contains("learning to play piano"))
            assertFalse(achievedExperiencesString.contains("rock climbing"))
            assertFalse(achievedExperiencesString.contains("summer holiday"))
            assertTrue(achievedExperiencesString.contains("graduate"))
            assertFalse(achievedExperiencesString.contains("ed sheeran"))
        }
    }

    @Nested
    inner class PriorityExperiences {
        @Test
        fun `listExperiencesBySelectedPriority returns No Experiences when ArrayList is empty`() {
            Assertions.assertEquals(0, emptyExperiences!!.numberOfExperiences())
            assertTrue(emptyExperiences!!.listExperiencesBySelectedPriority(3).lowercase().contains("no experiences")
            )
        }

        @Test
        fun `listExperiencesBySelectedPriority returns no experiences when no experiences of that priority exist`() {
            //Priority 1 (1 item), 2 (none), 3 (1 item). 4 (2 items), 5 (1 item)
            Assertions.assertEquals(5, populatedExperiences!!.numberOfExperiences())
            val priority2String = populatedExperiences!!.listExperiencesBySelectedPriority(2).lowercase()
            assertTrue(priority2String.contains("no experiences"))
            assertTrue(priority2String.contains("2"))
        }

        @Test
        fun `listExperiencesBySelectedPriority returns all experiences that match that priority when experiences of that priority exist`() {
            //Priority 1 (1 item), 2 (none), 3 (1 item). 4 (2 items), 5 (1 item)
            Assertions.assertEquals(5, populatedExperiences!!.numberOfExperiences())
            val priority1String = populatedExperiences!!.listExperiencesBySelectedPriority(1).lowercase()
            assertTrue(priority1String.contains("1 experience"))
            assertTrue(priority1String.contains("priority 1"))
            assertTrue(priority1String.contains("summer holiday"))
            assertFalse(priority1String.contains("rock climbing"))
            assertFalse(priority1String.contains("learning to play piano"))
            assertFalse(priority1String.contains("graduate"))
            assertFalse(priority1String.contains("ed sheeran"))


            val priority4String = populatedExperiences!!.listExperiencesBySelectedPriority(4).lowercase()
            assertTrue(priority4String.contains("2 experience"))
            assertTrue(priority4String.contains("priority 4"))
            assertFalse(priority4String.contains("ed sheeran"))
            assertTrue(priority4String.contains("graduate"))
            assertTrue(priority4String.contains("rock climbing"))
            assertFalse(priority4String.contains("learning to play piano"))
            assertFalse(priority4String.contains("summer holiday"))


            val priority3String = populatedExperiences!!.listExperiencesBySelectedPriority(3).lowercase()
            assertTrue(priority3String.contains("1 experience"))
            assertTrue(priority3String.contains("priority 3"))
            assertTrue(priority3String.contains("ed sheeran"))
            assertTrue(priority3String.contains("concert"))
            assertFalse(priority3String.contains("test app"))
            assertFalse(priority3String.contains("learning to play piano"))
            assertFalse(priority3String.contains("summer holiday"))
        }
    }


    @Nested
    inner class CategoryExperiences {
        @Test
        fun `listExperiencesByCategory returns No Experiences when ArrayList is empty`() {
            Assertions.assertEquals(0, emptyExperiences!!.numberOfExperiences())
            assertTrue(emptyExperiences!!.listExperiencesByCategory("").lowercase().contains("no experiences stored in your bucket list")
            )
        }


        @Test
        fun `listExperiencesByCategory returns no experiences when no experiences of that category exist`() {
            Assertions.assertEquals(5, populatedExperiences!!.numberOfExperiences())
            val categoryDummyString = populatedExperiences!!.listExperiencesByCategory("fooling around").lowercase()
            assertTrue(categoryDummyString.contains("no experiences"))
            assertTrue(categoryDummyString.contains("fooling around"))
        }

        @Test
        fun `listExperiencesByCategory returns all experiences that match that category when experiences of that category exist`() {
            Assertions.assertEquals(5, populatedExperiences!!.numberOfExperiences())
            val categoryHobbyString = populatedExperiences!!.listExperiencesByCategory("Hobby").lowercase()
            assertTrue(categoryHobbyString.contains("2 experience"))
            assertTrue(categoryHobbyString.contains("category hobby"))
            assertFalse(categoryHobbyString.contains("travel"))
            assertFalse(categoryHobbyString.contains("entertainment"))
            assertFalse(categoryHobbyString.contains("career"))
            assertFalse(categoryHobbyString.contains("Work"))
            assertTrue(categoryHobbyString.contains("play piano"))


            val categoryTravelString = populatedExperiences!!.listExperiencesByCategory("travel").lowercase()
            assertTrue(categoryTravelString.contains("1 experience"))
            assertTrue(categoryTravelString.contains("category travel"))
            assertFalse(categoryTravelString.contains("concert"))
            assertFalse(categoryTravelString.contains("career"))
            assertTrue(categoryTravelString.contains("travel"))
            assertFalse(categoryTravelString.contains("Hobby"))
            assertTrue(categoryTravelString.contains("spain"))
        }
    }

    @Nested
    inner class DeleteExperiences {
        @Test
        fun `deleting an Experience that does not exist, returns null`() {
            Assertions.assertNull(emptyExperiences!!.deleteExperience(0))
            Assertions.assertNull(populatedExperiences!!.deleteExperience(-1))
            Assertions.assertNull(populatedExperiences!!.deleteExperience(5))
        }

        @Test
        fun `deleting an Experience that exists delete and returns deleted object`() {
            Assertions.assertEquals(5, populatedExperiences!!.numberOfExperiences())
            assertEquals(concert, populatedExperiences!!.deleteExperience(4))
            Assertions.assertEquals(4, populatedExperiences!!.numberOfExperiences())
            assertEquals(learnPiano, populatedExperiences!!.deleteExperience(0))
            Assertions.assertEquals(3, populatedExperiences!!.numberOfExperiences())
        }
    }

}