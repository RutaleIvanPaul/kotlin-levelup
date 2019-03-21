package com.example.ivanpaulrutale.kotlinone

import android.support.test.espresso.Espresso
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    @Rule @JvmField
    val noteListActivity = ActivityTestRule(NoteListActivity::class.java)

    @Test
    fun createNewNote(){
        val course = DataManager.courses["2"]
        val noteTitle = "Test Note Title"
        val noteText = "Test Note Text"

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())

        onView(withId(R.id.editNoteTitle)).perform(typeText(noteTitle))
        onView(withId(R.id.editNoteText)).perform(typeText(noteText), closeSoftKeyboard())

        Espresso.pressBack()

        val newNote = DataManager.notes.last()
        assertEquals(course,newNote.course)
        assertEquals(noteText,newNote.text)
        assertEquals(noteTitle,newNote.title)


    }

}