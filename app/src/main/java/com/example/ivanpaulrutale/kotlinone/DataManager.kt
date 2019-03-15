package com.example.ivanpaulrutale.kotlinone

object DataManager {
    val courses = HashMap<String,CourseInfo>()
    val notes = ArrayList<NoteInfo>()
    var course = CourseInfo("1","android intents")

    init {
        initialiseCourses()
        initialiseNotes()
    }

    private fun initialiseCourses(){
        var course = CourseInfo("1","android intents")
        courses.set(course.courseId,course)

        course = CourseInfo("2","android async")
        courses.set(course.courseId,course)

        course = CourseInfo(title = "android fundamentals", courseId = "3")
        courses.set(course.courseId,course)
    }

    private fun initialiseNotes(){
        notes.add(NoteInfo(course,"Test note 1","Test note content 1"))
        notes.add(NoteInfo(course,"Test note 2","Test note content 2"))
        notes.add(NoteInfo(course,"Test note 3","Test note content 3"))
        notes.add(NoteInfo(course,"Test note 4","Test note content 4"))
    }
}