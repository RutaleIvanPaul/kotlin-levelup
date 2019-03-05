package com.example.ivanpaulrutale.kotlinone

class DataManager {
    val courses = HashMap<String,CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initialiseCourses()
    }

    private fun initialiseCourses(){
        var course = CourseInfo("1","android intents")
        courses.set(course.courseId,course)

        course = CourseInfo("2","android async")
        courses.set(course.courseId,course)

        course = CourseInfo(title = "android fundamentals", courseId = "3")
        courses.set(course.courseId,course)
    }
}