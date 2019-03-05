package com.example.ivanpaulrutale.kotlinone

class CourseInfo(val courseId:String, val title:String)

class NoteInfo(var course: CourseInfo, var title: String, var text:String)
