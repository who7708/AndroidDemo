package com.clutch.student.entity

/**
 * Created by clutchyu on 2018/3/21.
 * 展示学生选课及对应课程分数的类
 */
class CourseSec {
    var course_id = 0
    var course_name: String? = null
    var credit = 0
    var grade = 0
    var imageId = 0

    constructor() {}
    constructor(course_id: Int, course_name: String?, credit: Int, grade: Int, imageId: Int) {
        this.course_id = course_id
        this.course_name = course_name
        this.credit = credit
        this.grade = grade
        this.imageId = imageId
    }
}